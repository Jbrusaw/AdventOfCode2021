import java.io.File

var explosions = 0
fun main() {
    val oct = File("src/input11.txt").readLines().map { it.toCharArray().map { it.toString().toInt() }.toMutableList() }
    var round = 0
    do {
        execStep(oct)
        round++
        if (round == 100) {
            println(explosions)
        }
    } while (!oct.flatten().all { it == 0 })
    println(round)
}

fun execStep(oct: List<MutableList<Int>>) {
    for (x in oct.indices) {
        for (y in oct[0].indices) {
            oct[x][y]++
            if (oct[x][y] == 10) {
                explode(oct, x, y)
            }
        }
    }
    for (x in oct.indices) {
        for (y in oct[0].indices) {
            if (oct[x][y] >= 10) {
                oct[x][y] = 0
            }
        }
    }
}

fun explode(oct: List<MutableList<Int>>, x: Int, y: Int) {
    explosions++
    for (x1 in x - 1..x + 1) {
        for (y1 in y - 1..y + 1) {
            try {
                oct[x1][y1]++
                if (oct[x1][y1] == 10) {
                    explode(oct, x1, y1)
                }
            } catch (t: Throwable) {
            }
        }
    }
}
