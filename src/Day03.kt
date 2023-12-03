fun main() {
    fun <T> genMap(input: List<String>, value: T): MutableMap<Pair<Int, Int>, T> {
        val symbolMap = mutableMapOf<Pair<Int, Int>, T>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                if (symbol != '.' && !symbol.isDigit()) {
                    symbolMap[Pair(y, x)] = value
                }
            }
        }
        return symbolMap
    }
    fun loopThroughMap(input: List<String>, action: (Int, Int, Int) -> Unit) {
        val symbolMap = genMap(input, true)
        val width = input[0].length
        val height = input.size
        val directions = listOf(
            Pair(-1, -1),
            Pair(-1, 0),
            Pair(-1, 1),
            Pair(0, 1),
            Pair(0, -1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1),
        )
        var currentNum = ""
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                if (symbol.isDigit()) {
                    currentNum += symbol
                } else {
                    if (currentNum.isNotEmpty())
                    {
                        // end of num, check if touch with symbol
                        val touchPoint = mutableSetOf<Pair<Int, Int>>()
                        for (vx in x - currentNum.length..x - 1) {
                            for (direction in directions) {
                                val ty = y + direction.first
                                val tx = vx + direction.second
                                if (tx >= 0 && ty >= 0 && tx < width && ty < height &&
                                    symbolMap[Pair(ty, tx)] == true) {
                                    if (!touchPoint.contains(Pair(ty, tx)))
                                        action(ty, tx, currentNum.toInt())
                                    touchPoint.add(Pair(ty, tx))
                                }
                            }
                        }
                        currentNum = ""
                    }
                }
            }
            if (currentNum.isNotEmpty())
            {
                // end of num, check if touch with symbol
                val touchPoint = mutableSetOf<Pair<Int, Int>>()
                for (vx in (width - 1) - currentNum.length..<width) {
                    for (direction in directions) {
                        val ty = y + direction.first
                        val tx = vx + direction.second
                        if (tx >= 0 && ty >= 0 && tx < width && ty < height &&
                            symbolMap[Pair(ty, tx)] == true) {
                            if (!touchPoint.contains(Pair(ty, tx)))
                                action(ty, tx, currentNum.toInt())
                            touchPoint.add(Pair(ty, tx))
                        }
                    }
                }
                currentNum = ""
            }
        }
    }
    fun part1(input: List<String>): Int {
        // (y, x) to true
        var sum = 0
        loopThroughMap(input) { _, _, num ->
            sum += num
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val symbolTouchedBy = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()
        loopThroughMap(input) { y, x, num ->
            symbolTouchedBy[Pair(y, x)]?.add(num) ?: run {
                symbolTouchedBy[Pair(y, x)] = mutableListOf(num)
            }
        }
        return symbolTouchedBy.entries.sumOf {
            if (it.value.size == 2) {
                it.value[0] *it.value[1]
            } else {
                0
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
