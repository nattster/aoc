fun main() {

    fun cardValue(line: String): Int {
        val card = line.split(" ")[1]
        val winningHave = card.split("|")
        val winning = winningHave[0].trim().split(" ").toSet()
        val score = winningHave[1].trim().split(" ").toList().map {
            if (winning.contains(it)) {
                1
            } else {
                0
            }
        }.sum()
        return 1 shl score

    }

    fun part1(input: List<String>): Int {
        return input.sumOf { cardValue(it) }
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
//    check(part2(testInput) == 467835)
//
//    val input = readInput("Day03")
//    part1(input).println()
//    part2(input).println()
}
