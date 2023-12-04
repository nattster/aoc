import java.math.BigInteger

fun main() {

    fun cardValue(line: String): BigInteger {
        val card = line.split(":")[1]
        val winningHave = card.split("|")
        val winning = winningHave[0].trim().split("\\s+".toRegex()).toSet()
        val l = winningHave[1].trim().split("\\s+".toRegex()).toList()
            val score = l.map {
            if (winning.contains(it)) {
                1
            } else {
                0
            }
        }.sum()
        if (score == 0) {
            return BigInteger.ZERO
        }
        return BigInteger.valueOf(2L).pow(score - 1)

    }

    fun part1(input: List<String>): BigInteger {
        return input.sumOf { cardValue(it) }
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day04_test")
    check(part1(testInput) == BigInteger.valueOf(13L))
//    check(part2(testInput) == 467835)
//
    val input = readInput("Day04")
    part1(input).println()
//    part2(input).println()
}
