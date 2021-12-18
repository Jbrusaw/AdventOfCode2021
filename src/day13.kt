import java.io.File

fun main() {
    val input = File("src/input13.txt").readLines()
    val points = mutableSetOf<Pair<Int,Int>>()
    val instructions = mutableListOf<Pair<Char, Int>>()
    input.forEach {
        if (it.contains(',')) {
            val (x, y) = it.trim().split(',').map{it.toInt()}
            points.add(Pair(x,y))
        } else if (it.contains("fold")) {
            val axis = it.split('=')[0].last()
            val line = it.split('=')[1].toInt()
            instructions.add(Pair(axis,line))
        }
    }
    instructions.forEach { (axis, line) ->
        val toRemove = mutableListOf<Pair<Int, Int>>()
        val toAdd = mutableListOf<Pair<Int,Int>>()
        points.forEach { point ->
            if (axis == 'x' && point.first > line) {
                toRemove.add(point)
                toAdd.add(Pair(line - (point.first - line), point.second))
            } else if (axis == 'y' && point.second > line) {
                toRemove.add(point)
                toAdd.add(Pair(point.first, line - (point.second - line)))
            }
        }
        points.removeAll(toRemove)
        points.addAll(toAdd)
        println(points.size)
    }
    val width = points.maxOf { it.first } + 1
    val height = points.maxOf { it.second } + 1
    val grid = List(height) { MutableList(width) {' '} }
    points.forEach { grid[it.second][it.first] = '█' }
    grid.forEach { line ->
        line.forEach { print("$it$it$it") }
        println()
    }
}
