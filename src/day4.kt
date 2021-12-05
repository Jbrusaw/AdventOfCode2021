import java.io.File

fun main() {
    val strings = File("src/input4.txt").readText().split("\n\n")
    val numbers = strings[0].split(',').map {it.toInt()}
    val boards = emptyList<Board>().toMutableList()
    for (i in 1..strings.lastIndex) {
        boards.add(Board(strings[i].trim()))
    }
    println(checkBoards(numbers, boards, true))
    println(checkBoards(numbers, boards, false))
}

class Board(board: String) {
    val numbers: MutableList<Int> = board.split("\\s+".toRegex()).map{it.toInt()}.toMutableList()
    private val wins: List<MutableList<Int>>
    init {
        val lines = mutableListOf<MutableList<Int>>()
        for (i in numbers.indices step 5) {
            lines.add(numbers.subList(i, i + 5).toMutableList())
        }
        for (i in 0..4) {
            lines.add(listOf(numbers[i], numbers[i + 5], numbers[i + 10], numbers[i + 15], numbers[i + 20]).toMutableList())
        }
        lines.add(listOf(numbers[0], numbers[6], numbers[12], numbers[18], numbers[24]).toMutableList())
        lines.add(listOf(numbers[4], numbers[8], numbers[12], numbers[16], numbers[20]).toMutableList())
        wins = lines
    }


    fun checkNumber(number: Int): Boolean {
        if (numbers.remove(number)) {
            wins.forEach { win ->
                if (win.remove(number)) {
                    return win.isEmpty()
                }
            }
        }
        return false
    }
}

fun checkBoards(numbers:List <Int>, boards: MutableList<Board>, firstBoard: Boolean): Int {
    val toRemove = emptyList<Board>().toMutableList()
    numbers.forEach { number ->
        boards.forEach { board ->
            if (board.checkNumber(number)) {
                if (firstBoard) {
                    return number * board.numbers.sum()
                }
                else if (boards.size == 1) {
                    return number * board.numbers.sum()
                }
                toRemove.add(board)
            }
        }
        boards.removeAll(toRemove)
    }
    throw Error("what")
}