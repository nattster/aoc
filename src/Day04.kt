import java.math.BigInteger

fun main() {

    fun score(line: String): Int {
        val card = line.split(":")[1]
        val winningHave = card.split("|")
        val winning = winningHave[0].trim().split("\\s+".toRegex()).toSet()
        return winningHave[1].trim().split("\\s+".toRegex()).map {
            if (winning.contains(it)) {
                1
            } else {
                0
            }
        }.sum()
    }

    fun cardValue(line: String): BigInteger {
        val score = score(line)
        if (score == 0) {
            return BigInteger.ZERO
        }
        return BigInteger.valueOf(2L).pow(score - 1)
    }

    fun part1(input: List<String>): BigInteger {
        return input.sumOf { cardValue(it) }
    }

    fun part2(input: List<String>): Long {
        val numCards = mutableMapOf<Int, Int>()
        var finalScore = 0L
        for (i in input.indices) {
            numCards[i] = 1
        }
        for (i in input.indices) {
            finalScore += numCards[i]!!

            val score = score(input[i])
            for (j in 0..<score) {
                numCards[i + 1 + j] = (numCards[i + 1 + j] ?: 0) + numCards[i]!!
            }
        }
        return finalScore
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day04_test")
    check(part1(testInput) == BigInteger.valueOf(13L))
    check(part2(testInput) == 30L)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
