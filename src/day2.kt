import java.io.File

fun main() {
    val instructions = File("src/input2.txt").readLines().map{it.split(' ')[0] to Integer.parseInt(it.split(' ')[1])}

    // part 1
    var horizontal = 0
    var depth = 0
    instructions.forEach { (instruction, num) ->
        when (instruction) {
            "forward" -> horizontal += num
            "down" -> depth += num
            "up" -> depth -= num
        }
    }
    println(depth * horizontal)

    // part 2
    horizontal = 0
    depth = 0
    var aim = 0
    instructions.forEach { (instruction, num) ->
        when (instruction) {
            "forward" -> {
                horizontal += num
                depth += aim * num
            }
            "down" -> aim += num
            "up" -> aim -= num
        }
    }
    println(depth * horizontal)
}