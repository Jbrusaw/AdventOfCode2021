import java.io.File

fun main() {
    println(day14(10))
    println(day14(40))
}

fun day14(iterations: Int): Long {
    val input = File("src/input14.txt").readLines()
    val rules = mutableMapOf<String, Pair<String, String>>()
    val polymer = input[0]
    var pairCount = mutableMapOf<String, Long>()
    val charCount = mutableMapOf<Char, Long>()

    for (i in 2..input.lastIndex) {
        val (pair, output) = input[i].split(" -> ").map { it.trim() }
        val charOutput = output.toCharArray()[0]
        rules[pair] = Pair(pair[0].toString() + charOutput, charOutput + pair[1].toString())
        pairCount[pair] = 0L
    }

    for (i in 0 until polymer.lastIndex) {
        val pair = polymer[i].toString() + polymer[i + 1]
        pairCount[pair] = pairCount[pair]!!.plus(1)
        charCount[polymer[i]] = charCount[polymer[i]]?.plus(1) ?: 1L
    }
    charCount[polymer.last()] = charCount[polymer.last()]?.plus(1) ?: 1L

    for (i in 1..iterations) {
        val newPairCounts = mutableMapOf<String, Long>()
        pairCount.forEach { (pair, count) ->
            newPairCounts[rules[pair]!!.first] = (newPairCounts[rules[pair]!!.first] ?: 0) + count
            newPairCounts[rules[pair]!!.second] = (newPairCounts[rules[pair]!!.second] ?: 0) + count
            charCount[rules[pair]!!.first[1]] = (charCount[rules[pair]!!.first[1]] ?: 0) + count
        }
        pairCount = newPairCounts
    }

    return charCount.values.maxOrNull()!! - charCount.values.minOrNull()!!
}
