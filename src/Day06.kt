import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun parseNumbers(line: String): List<Long> {
        return line.trim().split("\\s+".toRegex()).map { it.toLong() }
    }

    fun findWays(time: Long, recordDistance: Long): Long {
        var count = 0L
        for (hold in 0..time) {
            val distance = (time - hold) * hold
            if (distance > recordDistance) {
                count += 1L
            }
        }
        return count
    }

    fun solve(times: List<Long>, distances: List<Long>): Long =
        times.zip(distances).map { findWays(it.first, it.second) }.reduce { acc, i -> acc * i }

    fun part1(input: List<String>): Long {
        val times = parseNumbers(input[0].split(":")[1])
        val distances = parseNumbers(input[1].split(":")[1])
        return solve(times, distances)
    }

    fun part2(input: List<String>): Long {
        val times = parseNumbers(input[0].split(":")[1].replace(" ", ""))
        val distances = parseNumbers(input[1].split(":")[1].replace(" ", ""))
        return solve(times, distances)
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
