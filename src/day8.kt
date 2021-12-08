import java.io.File

fun main() {
    val codes = File("src/input8.txt").readLines().map{it.split('|').map{it.trim().split(' ').map{it.toCharArray().sorted().joinToString("")}}}
    println(codes.sumBy{code -> code[1].count {it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7}})
    println(codes.sumBy{decipher(it[0].toMutableList(), it[1])})
}

fun decipher(input: MutableList<String>, output: List<String>): Int {
    val cipher: MutableMap<String, Int> = HashMap()

    val one = input.find { it.length == 2 }!!
    cipher[one] = 1
    input.remove(one)

    val seven = input.find { it.length == 3 }!!
    cipher[seven] = 7
    input.remove(seven)

    val four = input.find { it.length == 4 }!!
    cipher[four] = 4
    input.remove(four)

    val eight = input.find { it.length == 7 }!!
    cipher[eight] = 8
    input.remove(eight)

    val nine = input.find { it.length == 6 && contains(it, four) }!!
    cipher[nine] = 9
    input.remove(nine)

    val zero = input.find { it.length == 6 && contains(it, one) }!!
    cipher[zero] = 0
    input.remove(zero)

    val six = input.find { it.length == 6 }!!
    cipher[six] = 6
    input.remove(six)

    val three = input.find { it.length == 5 && contains(it, one) }!!
    cipher[three] = 3
    input.remove(three)

    val five = input.find { it.length == 5 && contains(six, it) }!!
    cipher[five] = 5
    input.remove(five)

    val two = input[0]
    cipher[two] = 2
    input.remove(two)

    return output.map { cipher[it] }.joinToString("").toInt()
}

fun contains(parent: String, child: String): Boolean {
    return child.all { parent.contains(it) }
}