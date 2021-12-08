import java.io.File
import kotlin.math.abs

fun main() {
    val crabs = File("src/input7.txt").readText().trim().split(',').map { it.toInt() }.sorted()
    val median = crabs[crabs.size / 2]
    val mean = crabs.average().toInt()
    println(crabs.map { abs(it - median) }.sum())
    println(crabs.map { (1..abs(it - mean)).sum() }.sum())
}