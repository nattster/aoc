fun main() {
    fun findNextValue(nums: List<Int>): Int {
        if (nums.all { it == 0 }) {
            return 0 // diff to add to last value
        }
        val newListDiff = nums.windowed(2, 1).map { (a, b) ->
            b - a
        }
        val diff = findNextValue(newListDiff)
        return nums.last() + diff
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val nums = line.split(" ").map { it.toInt() }
            findNextValue(nums)
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val nums = line.split(" ").map { it.toInt() }.reversed()
            findNextValue(nums)
        }
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
