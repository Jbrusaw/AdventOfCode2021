import java.io.File

fun main() {
    val fish = File("src/input6.txt").readText().trim().split(',').map { it.toInt() }
    println(fish.map { fishSpawned(it, 80) }.sum())
    println(fish.map { fishSpawned(it, 256) }.sum())
}

val fishMap = HashMap<Pair<Int, Int>, Long>()

fun fishSpawned(num: Int, days: Int): Long {
    return when {
        num == -1 -> fishSpawned(6, days) + fishSpawned(8, days)
        days == 0 -> 1L
        else -> fishMap[Pair(num, days)] ?: fishSpawned(num - 1, days - 1).also { fishMap[Pair(num, days)] = it }
    }
}