fun main() {
    fun calibrateValue(input: List<String>): Int {
        return input.map {
            val first = it.find(Char::isDigit)
            val last = it.findLast(Char::isDigit)
            "$first$last".toInt()
        }.sum()
    }

    fun part1(input: List<String>): Int {
        return calibrateValue(input)
    }

    val numberMap = mapOf("eight" to "8", "two" to "2", "one" to "1", "three" to "3", "four" to "4", "five" to "5", "six" to "6",
        "seven" to "7", "nine" to "9")

    fun convertSpelledOut(input: String): String {
        val regex = "(${numberMap.keys.joinToString("|")}|[0-9])".toRegex()
        val first = regex.find(input)?.let {
            numberMap.getOrDefault(it.value, it.value)
        }

        var last = input.findLast(Char::isDigit).toString()
        var lastIndex = input.lastIndexOf(last)
        numberMap.keys.forEach { key ->
            input.lastIndexOf(key).let { index ->
                if (index > lastIndex) {
                    lastIndex = index
                    last = numberMap[key]!!
                }
            }
        }
        return "$first$last"
    }

    fun part2(input: List<String>): Int {
        return calibrateValue(input.map(::convertSpelledOut))
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()

    val testInput2 = readInput("Day01_part2_test")
    check(part2(testInput2) == 281)
    part2(input).println()
}
