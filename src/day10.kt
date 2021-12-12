import java.io.File

fun main() {
    File("src/input10.txt").readLines().forEach { calcScore(it) }
    println(corruptedScore)
    println(completedScores.sorted()[completedScores.lastIndex / 2])
}

var corruptedScore = 0
val completedScores = mutableListOf<Long>()

fun calcScore(s: String) {
    val opens = mutableListOf<Char>()
    var scoreTable = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val closes = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    s.forEach {
        when (it) {
            in listOf('(', '{', '[', '<') -> opens.add(it)
            else -> {
                if (opens.removeAt(opens.lastIndex) != closes[it]) {
                    corruptedScore += scoreTable[it] ?: error("what")
                    return
                }
            }
        }
    }

    scoreTable = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
    val completeScore = opens.reversed().fold(0L) { i, acc -> 5L * i + (scoreTable[acc] ?: error("what")) }
    completedScores.add(completeScore)
}
