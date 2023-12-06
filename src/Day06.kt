import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun parseNumbers(line: String): List<Int> {
        return line.trim().split("\\s+".toRegex()).map { it.toInt() }
    }

    fun findWays(time: Int, recordDistance: Int): Int {
        var count = 0
        for (hold in 0..time) {
            val distance = (time - hold) * hold
            if (distance > recordDistance) {
                count += 1
            }
        }
        return count
    }

    fun part1(input: List<String>): Long {
        val times = parseNumbers(input[0].split(":")[1])
        val distances = parseNumbers(input[1].split(":")[1])
        var result = 1L
        for (it in times.zip(distances)) {
            result *= findWays(it.first, it.second)
        }
        return result
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
//    check(part2(testInput) == 46L)
//
    val input = readInput("Day06")
    part1(input).println()
//    part2(input).println()
}
