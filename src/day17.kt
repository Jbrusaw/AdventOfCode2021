import kotlin.math.max

fun main() {
    val x = 265..287
    val y = -103..-58
    //val x = 20..30
    //val y = -10..-5
    val validX = mutableListOf<Int>()
    for (i in 1..100) {
        if (i * (i + 1) / 2  in x) {
            validX.add(i)
        } else if ((i * (i + 1) / 2  > x.last())) {
            break
        }
    }
    var max = 0
    println(calcHeight(6, 0, x, y))

    (validX.first()..x.last()).forEach {
        for (i in y.first()..1000) {
            val height = calcHeight(it, i, x, y)
            if (height >= 0) {
                max++
            }
        }
    }
    println(max)
}

fun calcHeight(startX: Int, startY: Int, xRange: IntRange, yRange: IntRange): Int {
    var x = 0
    var y = 0
    var step = 0
    do {
        x += max((startX - step), 0)
        y += (startY-step)
        if (x in xRange && y in yRange) {
            return startY * (startY + 1) / 2
        }
        step++
    } while (x <= xRange.last() && y >= yRange.first())
    return -1
}
