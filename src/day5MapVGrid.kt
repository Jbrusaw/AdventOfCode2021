import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.time.Duration
import kotlin.time.measureTime

fun main() {
    assert(day5Grid(false) == day5Map(false))
    assert(day5Grid(true) == day5Map(true))
    val grid: List<Duration> = (1..100).map{measureTime{day5Grid(false)}}
    val gridWithDiagonals: List<Duration> = (1..100).map{measureTime{day5Grid(true)}}
    val map: List<Duration> = (1..100).map{measureTime{day5Map(false)}}
    val mapWithDiagonals: List<Duration> = (1..100).map{measureTime{day5Map(true)}}
    println("Average time (in ms) of 100 runs using grid and not checking diagonals: \t" + grid.map{it.toDouble(TimeUnit.MILLISECONDS)}.average())
    println("Average time (in ms) of 100 runs using grid and checking diagonals: \t\t" + gridWithDiagonals.map{it.toDouble(TimeUnit.MILLISECONDS)}.average())
    println("Average time (in ms) of 100 runs using hashmap and not checking diagonals: \t" + map.map{it.toDouble(TimeUnit.MILLISECONDS)}.average())
    println("Average time (in ms) of 100 runs using hashmap and checking diagonals: \t\t" + mapWithDiagonals.map{it.toDouble(TimeUnit.MILLISECONDS)}.average())
}

fun day5Grid(checkDiagonals: Boolean): Int {
    var maxSize = 0
    val points = File("src/input5.txt").readLines().map { line -> line.split(" -> ").map { point -> point.split(',').map { coordinate -> coordinate.toInt().also {maxSize = max(maxSize, it)} } } }
    val grid = List(maxSize + 1) { MutableList(maxSize + 1) { 0 } }
    var answer = 0
    points.forEach { (start, end) ->
        val (startX, startY) = start
        val (endX, endY) = end
        if (checkDiagonals || startX == endX || startY == endY) {
            val xIterator = when {
                startX < endX -> (startX..endX).iterator()
                startX > endX -> (startX downTo endX).iterator()
                else -> generateSequence { startX }.iterator()
            }
            val yIterator = when {
                startY < endY -> (startY..endY).iterator()
                startY > endY -> (startY downTo endY).iterator()
                else -> generateSequence { startY }.iterator()
            }
            while (xIterator.hasNext() && yIterator.hasNext()) {
                val x = xIterator.next()
                val y = yIterator.next()
                grid[x][y]++
                if (grid[x][y] == 2) {
                    answer++
                }
            }
        }
    }
    return answer
}

fun day5Map(checkDiagonals: Boolean): Int {
    val points = File("src/input5.txt").readLines().map { line -> line.split(" -> ").map { point -> point.split(',').map { coordinate -> coordinate.toInt() } } }
    val map = HashMap<Pair<Int,Int>, Int>()
    var answer = 0
    points.forEach { (start, end) ->
        val (startX, startY) = start
        val (endX, endY) = end
        if (checkDiagonals || startX == endX || startY == endY) {
            val xIterator = when {
                startX < endX -> (startX..endX).iterator()
                startX > endX -> (startX downTo endX).iterator()
                else -> generateSequence { startX }.iterator()
            }
            val yIterator = when {
                startY < endY -> (startY..endY).iterator()
                startY > endY -> (startY downTo endY).iterator()
                else -> generateSequence { startY }.iterator()
            }
            while (xIterator.hasNext() && yIterator.hasNext()) {
                val x = xIterator.next()
                val y = yIterator.next()
                val point = Pair(x, y)
                when (map[point]) {
                    null -> map[point] = 1
                    1 -> map[point] = 2.also { answer++ }
                }
            }
        }
    }
    return answer
}

