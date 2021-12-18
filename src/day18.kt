import java.io.File
import kotlin.math.max

fun main() {
    val input = File("src/input18.txt").readLines()
    var head = FishBranch(input[0])
    input.subList(1, input.lastIndex + 1).forEach {
        head = FishBranch(head, FishBranch(it)).reduce()
    }
    println(head.magnitude())
    var magnitude = 0
    for (i in 0 until input.lastIndex) {
        for (j in (i+1)..input.lastIndex) {
            magnitude = max(magnitude, FishBranch(FishBranch(input[i]),FishBranch(input[j])).reduce().magnitude())
            magnitude = max(magnitude, FishBranch(FishBranch(input[j]),FishBranch(input[i])).reduce().magnitude())
        }
    }
    println(magnitude)
}


abstract class Fish {
    abstract fun split(): Pair<Fish, Boolean>
    abstract fun explode(depth: Int): ExplodeReturn
    abstract fun addRight(num: Int)
    abstract fun addLeft(num: Int)
    abstract fun magnitude(): Int
    abstract fun print()
}

class FishLeaf(var num: Int): Fish() {
    override fun split(): Pair<Fish, Boolean> {
        if (num >= 10) {
            val left = FishLeaf(num / 2)
            val right = FishLeaf(num / 2 + num % 2)
            val fish = FishBranch(left, right)
            return Pair(fish, true)
        }
        return Pair(this, false)
    }

    override fun explode(depth: Int): ExplodeReturn = ExplodeReturn(this, 0, 0, false)

    override fun addLeft(num: Int) {
        this.num += num
    }

    override fun addRight(num: Int) {
        this.num += num
    }

    override fun magnitude() = this.num

    override fun print() = print(num)
}

class FishBranch: Fish {
    private var left: Fish
    private var right: Fish

    constructor(fish1: Fish, fish2: Fish) {
        left = fish1
        right = fish2
    }

    constructor(fishString: String) {
        if (fishString.count{it == ','} == 1) {
            val nums = fishString.split(',')
            left = FishLeaf(nums[0][1].toString().toInt())
            right = FishLeaf(nums[1][0].toString().toInt())
            return
        } else {
            var opens = 0
            for (i in 1 until fishString.lastIndex) {
                if (fishString[i] == '[') {
                    opens++
                } else if (fishString[i] == ']') {
                    opens--
                }
                if (opens == 0) {
                    var s = fishString.substring(1, i + 1)
                    left = if (s.contains(',')) FishBranch(s) else FishLeaf(s.toInt())
                    s = fishString.substring(i + 2, fishString.lastIndex)
                    right = if (s.contains(',')) FishBranch(s) else FishLeaf(s.toInt())
                    return
                }
            }
        }
        error(fishString)
    }

    override fun explode(depth: Int): ExplodeReturn {
        if (depth == 4) {
            val fish = FishLeaf(0)
            return ExplodeReturn(fish, (left as FishLeaf).num, (right as FishLeaf).num, true)
        } else {
            var explodeReturn = left.explode(depth + 1)
            if (explodeReturn.exploded) {
                left = explodeReturn.fish
                right.addLeft(explodeReturn.right)
                explodeReturn.fish = this
                explodeReturn.right = 0
                return explodeReturn
            }
            explodeReturn = right.explode(depth + 1)
            if (explodeReturn.exploded) {
                right = explodeReturn.fish
                left.addRight(explodeReturn.left)
                explodeReturn.fish = this
                explodeReturn.left = 0
                return explodeReturn
            }
        }
        return ExplodeReturn(this, 0, 0, false)
    }

    override fun split(): Pair<Fish, Boolean> {
        var result = left.split()
        if (result.second) {
            left = result.first
        } else {
            result = right.split()
            if (result.second) {
                right = result.first
            }
        }
        return Pair(this, result.second)
    }

    override fun addLeft(num: Int) = left.addLeft(num)

    override fun addRight(num: Int) = right.addRight(num)

    override fun magnitude(): Int {
        return 3 * left.magnitude() + 2 * right.magnitude()
    }

    override fun print() {
        print('[')
        left.print()
        print(',')
        right.print()
        print(']')
    }

    fun reduce(): FishBranch {
        while (true) {
            val explodeReturn = explode(0)
            if (!explodeReturn.exploded) {
                val splitReturn = split()
                if (!splitReturn.second) {
                    return this
                }
            }
        }
    }
}

data class ExplodeReturn(
        var fish: Fish,
        var left: Int,
        var right: Int,
        val exploded: Boolean
)