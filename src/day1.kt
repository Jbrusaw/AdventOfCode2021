import java.io.File

fun main() {
    val depths = File("src/input1.txt").readLines().map { Integer.parseInt(it) }

    // part 1 + 2
    var count1 = 0
    var count2 = 0
    for (i in 0..depths.size - 2) {
        count1 += if (depths[i + 1] > depths[i]) 1 else 0
        count2 += if (i + 3 < depths.size && depths[i + 3] > depths[i]) 1 else 0
    }
    println(count1)
    println(count2)
}