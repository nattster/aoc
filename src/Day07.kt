fun main() {

    data class Hand(val cards: String, val type: Int, val value: Int, val bid: Int)

    val values = "23456789TJQKA".mapIndexed() { i, c -> c to i }.toMap()
    fun parseHand(line: String): Hand {
        val cardBid = line.split(" ")
        val cards = cardBid[0]
        val freq = cards.groupingBy { it }.eachCount()
        val type = when {
            freq.values.contains(5) -> 7    // five of a hind
            freq.values.contains(4) -> 6    // four of a kind
            freq.values.contains(3) && freq.values.contains(2) -> 5 // full house
            freq.values.contains(3) -> 4       // three of a kind
            freq.values.count { it == 2 } == 2 -> 3 // two pair
            freq.values.count { it == 2 } == 1 -> 2 // one pair
            else -> 1                               // high card
        }
        val value = cards.map { values[it]!! }.reduce { acc, i -> acc * 13 + i }
        return Hand(cards, type, value, cardBid[1].toInt())
    }

    fun part1(input: List<String>): Long {
        return input.map(::parseHand)
            .sortedWith(compareBy({ it.type }, { it.value }))
            .mapIndexed { i, hand -> hand.bid * (i + 1L) }
            .sum()
    }

    val values2 = "J23456789TQKA".mapIndexed() { i, c -> c to (i+1) }.toMap()
    fun parseHand2(line: String): Hand {
        val cardBid = line.split(" ")
        val cards = cardBid[0]
        val freq = cards.groupingBy { it }.eachCount()
        val freqNoJ = cards.filter { it != 'J' }.groupingBy { it }.eachCount().values.sortedDescending().filter { it > 0 }.toMutableList()
        var type = 0
        if (freq['J'] == 5) {
            type = 7
        } else {
            freqNoJ[0] += freq['J'] ?: 0
            type = when (freqNoJ) {
                listOf(5) -> 7
                listOf(4, 1) -> 6
                listOf(3, 2) -> 5
                listOf(3, 1, 1) -> 4
                listOf(2, 2, 1) -> 3
                listOf(2, 1, 1, 1) -> 2
                listOf(1, 1, 1, 1, 1) -> 1
                else -> 0
            }
        }
        val value = cards.map { values2[it]!! }.reduce { acc, i -> acc * 20 + i }
        return Hand(cards, type, value, cardBid[1].toInt())
    }

    fun part2(input: List<String>): Long {
        return input.map(::parseHand2)
            .sortedWith(compareBy({ it.type }, { it.value }))
            .mapIndexed { i, hand -> hand.bid * (i + 1L) }
            .sum()
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440L)
    check(part2(testInput) == 5905L)
//
    val input = readInput("Day07")
//    part1(input).println()
    part2(input).println()
}
