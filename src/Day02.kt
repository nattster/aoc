fun main() {
    fun isSubsetPossible(subset: String): Boolean {
        val count = mapOf("red" to 12, "green" to 13, "blue" to 14)
        for (cube in subset.split(",")) {
            val numColor = cube.trim().split(" ")
            val num = numColor[0].toInt()
            val color = numColor[1].trim()
            if (num > count[color]!!) {
                return false
            }
        }
        return true
    }

    fun isGamePossible(game: String): Boolean {
        val subset = game.split(";")
        return subset.all { isSubsetPossible(it) }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { game ->
            val idGame = game.split(":")
            val id = idGame[0].substring("Game ".length).toInt()
            val game = idGame[1].trim()
            if (isGamePossible(game)) {
                id
            } else {
                0
            }
        }
    }

    fun power(game: String): Int {
        val subsets = game.split(";")
        var red = 0
        var blue = 0
        var green = 0
        for (subset in subsets) {
            for (cube in subset.split(",")) {
                val numColor = cube.trim().split(" ")
                val num = numColor[0].trim().toInt()
                val color = numColor[1].trim()
                when (color) {
                    "red" -> red = Math.max(red, num)
                    "blue" -> blue = Math.max(blue, num)
                    "green" -> green = Math.max(green, num)
                }
            }
        }
        return red * blue * green
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { game ->
            power(game.split(": ")[1])
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()

}
