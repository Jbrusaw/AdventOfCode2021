import java.io.File
import java.lang.Error

fun main() {
    val strings = File("src/input3.txt").readLines()

    // part 1
    val numArrays = strings.map { line -> line.map { char -> Integer.parseInt(char.toString()) } }
    val sums = Array(numArrays[0].size) { 0 }
    numArrays.forEach { numArray ->
        numArray.forEachIndexed { index, num ->
            sums[index] += num
        }
    }
    val gamma = String(sums.map { if (it > numArrays.size / 2) '1' else '0' }.toCharArray())
    val epsilon = String(gamma.map { if (it == '0') '1' else '0' }.toCharArray())
    println(Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2))

    // part 2
    // build binary tree
    val head = Node()
    strings.forEach { string ->
        var curr = head
        string.forEach { curr = curr.addNode(it) }
    }
    // traverse binary tree
    var o2 = ""
    var co2 = ""
    var o2Node: Node? = head
    var co2Node: Node? = head
    while (o2Node != null && co2Node != null) {
        o2Node = if (o2Node.zero == null && o2Node.one == null) {
            null
        } else if (o2Node.one == null) {
            o2 += '0'
            o2Node.zero
        } else if (o2Node.zero == null) {
            o2 += '1'
            o2Node.one
        } else if (o2Node.one!!.num >= o2Node.zero!!.num) {
            o2 += '1'
            o2Node.one
        } else {
            o2 += '0'
            o2Node.zero
        }

        co2Node = if (co2Node.zero == null && co2Node.one == null) {
            null
        } else if (co2Node.one == null) {
            co2 += '0'
            co2Node.zero
        } else if (co2Node.zero == null) {
            co2 += '1'
            co2Node.one
        } else if (co2Node.one!!.num >= co2Node.zero!!.num) {
            co2 += '0'
            co2Node.zero
        } else {
            co2 += '1'
            co2Node.one
        }
    }
    println(Integer.parseInt(o2, 2) * Integer.parseInt(co2, 2))
}

class Node(var num: Int = 1, var zero: Node? = null, var one: Node? = null) {

    private fun increase(): Node {
        num++
        return this
    }

    fun addNode(c: Char): Node {
        return when (c) {
            '0' -> {
                zero = zero?.increase() ?: Node()
                zero!!
            }
            '1' -> {
                one = one?.increase() ?: Node()
                one!!
            }
            else -> throw Error("what")
        }
    }
}
