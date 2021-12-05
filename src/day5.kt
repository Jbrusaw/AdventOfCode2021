import java.io.File

fun main() {
    val points = File("src/input5.txt").readLines().map { line -> line.split(" -> ").map { point -> point.split(',').map { coordinate -> coordinate.toInt() } } }
    println(day4(points, false))
    println(day4(points, true))
}

fun day4(points: List<List<List<Int>>>, checkDiagonals: Boolean): Int {
    val grid = List(1000) { MutableList(1000) { 0 } }
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
                answer += if (++grid[xIterator.next()][yIterator.next()] == 2) 1 else 0
            }
        }
    }
    return answer
}