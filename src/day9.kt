import java.io.File

fun main() {
    val heights = File("src/input9.txt").readLines().map{ it.toCharArray().map{it.toInt() - 48}}
    var answer = 0
    for (x in heights.indices) {
        for (y in heights[x].indices) {
            answer += risk(heights, x, y)
        }
    }
    println(answer)
    val basins = mutableListOf<Int>()
    val checkedPoints: MutableSet<Pair<Int, Int>> = mutableSetOf()
    for (x in heights.indices) {
        for (y in heights[x].indices) {
            if (heights[x][y] != 9) {
                val point = Pair(x, y)
                if (!checkedPoints.contains(point)) {
                    basins.add(findBasin(heights, point, checkedPoints))
                }
            }
        }
    }
    basins.sortDescending()
    println(basins.subList(0,3).reduce { acc, it -> acc*it })
}

fun risk(heights: List<List<Int>>, x: Int, y: Int): Int {
    val height = heights[x][y]
    return when {
        x + 1 in heights.indices && heights[x + 1][y]    <= height -> 0
        x - 1 in heights.indices && heights[x - 1][y]    <= height -> 0
        y + 1 in heights[x].indices && heights[x][y + 1] <= height -> 0
        y - 1 in heights[x].indices && heights[x][y - 1] <= height -> 0
        else -> height + 1
    }
}

fun findBasin(heights: List<List<Int>>, startPoint: Pair<Int, Int>, checkedPoints: MutableSet<Pair<Int, Int>>): Int {
    val pointsToCheck: MutableSet<Pair<Int, Int>> = mutableSetOf()
    pointsToCheck.add(startPoint)
    var points = 0
    while (pointsToCheck.isNotEmpty()) {
        val point = pointsToCheck.elementAt(0)
        pointsToCheck.remove(point)
        points++
        checkedPoints.add(point)
        var checkPoint = Pair(point.first + 1, point.second)
        if (checkPoint.first in heights.indices && heights[checkPoint.first][checkPoint.second] != 9 && !checkedPoints.contains(checkPoint)) {
            pointsToCheck.add(checkPoint)
        }
        checkPoint = Pair(point.first - 1, point.second)
        if (checkPoint.first in heights.indices && heights[checkPoint.first][checkPoint.second] != 9 && !checkedPoints.contains(checkPoint)) {
            pointsToCheck.add(checkPoint)
        }
        checkPoint = Pair(point.first, point.second + 1)
        if (checkPoint.second in heights[checkPoint.first].indices && heights[checkPoint.first][checkPoint.second] != 9 && !checkedPoints.contains(checkPoint)) {
            pointsToCheck.add(checkPoint)
        }
        checkPoint = Pair(point.first, point.second - 1)
        if (checkPoint.second in heights[checkPoint.first].indices && heights[checkPoint.first][checkPoint.second] != 9 && !checkedPoints.contains(checkPoint)) {
            pointsToCheck.add(checkPoint)
        }
    }

    return points
}
