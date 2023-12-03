fun main() {
    fun part1(input: List<String>): Int {
        // (y, x) to true
        val width = input[0].length
        val height = input.size
        val symbolMap = mutableMapOf<Pair<Int, Int>, Boolean>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                if (symbol != '.' && !symbol.isDigit()) {
                    symbolMap[Pair(y, x)] = true
                }
            }
        }
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
        var sum = 0
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                if (symbol.isDigit()) {
                    currentNum += symbol
                } else {
                    if (currentNum.isNotEmpty())
                    {
                        // end of num, check if touch with symbol
                        var touched = false
                        for (vx in x - currentNum.length..x - 1) {
                            for (direction in directions) {
                                val ty = y + direction.first
                                val tx = vx + direction.second
                                if (tx >= 0 && ty >= 0 && tx < width && ty < height &&
                                    symbolMap[Pair(ty, tx)] == true) {
                                    touched = true
                                    break
                                }
                            }
                        }
                        if (touched) {
                            sum += currentNum.toInt()
                        }
                        currentNum = ""
                    }
                }
            }
            if (currentNum.isNotEmpty())
            {
                // end of num, check if touch with symbol
                var touched = false
                for (vx in (width - 1) - currentNum.length..<width) {
                    for (direction in directions) {
                        val ty = y + direction.first
                        val tx = vx + direction.second
                        if (tx >= 0 && ty >= 0 && tx < width && ty < height &&
                            symbolMap[Pair(ty, tx)] == true) {
                            touched = true
                            break
                        }
                    }
                }
                if (touched) {
                    sum += currentNum.toInt()
                }
                currentNum = ""
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
}
