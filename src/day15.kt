import java.io.File

fun main() {
    println(day15a())
    println(day15b())
}

val input = File("src/input15.txt").readLines().map {it.toCharArray().map{it.toString().toInt()}}

fun day15a() = bfs(input)

fun day15b(): Int {
    val nodes = MutableList(5 * input.size) { MutableList(5 * input.size) { 0 } }
    for (i in input.indices) {
        for (j in input.indices) {
            for (k in 0..4) {
                for (l in 0..4) {
                    if (input[i][j] + k + l > 9) {
                        nodes[i + k * input.size][j + l * input.size] = input[i][j] + k + l - 9
                    } else {
                        nodes[i + k * input.size][j + l * input.size] = input[i][j] + k + l
                    }
                }
            }
        }
    }
    return bfs(nodes)
}

fun bfs(nodes: List<List<Int>>): Int {
    val nodesToCheck = mutableListOf<Pair<Pair<Int,Int>, Int>>()
    nodesToCheck.add(Pair(Pair(0,0), 0))
    val checkedNodes = mutableSetOf<Pair<Int, Int>>()
    val directions = listOf(Pair(0,1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))
    while (true) {
        nodesToCheck.sortBy { it.second }
        val node = nodesToCheck.removeAt(0)
        if (node.first.first == nodes.lastIndex && node.first.second == nodes.last().lastIndex) {
            return node.second
        }
        directions.forEach {
            val next = Pair(node.first.first + it.first, node.first.second + it.second)
            if (next.first in nodes.indices && next.second in nodes[next.first].indices && checkedNodes.add(next)) {
                nodesToCheck.add(Pair(next, node.second + nodes[next.first][next.second]))
            }
        }
    }
}
