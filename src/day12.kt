import java.io.File

val caves = mutableMapOf<String, MutableList<String>>()
fun main() {
    val input = File("src/input12.txt").readLines().map{it.split('-')}
    input.forEach { (cave1, cave2) ->
        caves[cave1] = caves[cave1]?.plus(cave2)?.toMutableList() ?: mutableListOf(cave2)
        caves[cave2] = caves[cave2]?.plus(cave1)?.toMutableList() ?: mutableListOf(cave1)
    }
    println(dfs1("start", emptyList()))
    println(dfs2("start", emptyList()))
}

fun dfs1(cave: String, path: List<String>): Int {
    return when {
        cave == "end" -> 1
        cave.all { it.isLowerCase() } && path.contains(cave) -> 0
        else -> caves[cave]!!.sumBy { dfs1(it, path + cave) }
    }
}

fun dfs2(cave: String, path: List<String>): Int {
    return when {
        cave == "end" -> 1
        cave == "start" && path.isNotEmpty() -> 0
        cave.all { it.isLowerCase() } && path.contains(cave) -> caves[cave]!!.sumBy { dfs1(it, path + cave) }
        else -> caves[cave]!!.sumBy { dfs2(it, path + cave) }
    }
}
