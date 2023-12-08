fun main() {
    fun parseInput(input: List<String>): Pair<String, MutableMap<String, Pair<String, String>>> {
        val instruction = input[0]
        val map = mutableMapOf<String, Pair<String, String>>()
        for (line in input.drop(2)) {
            val match = Regex("""(\w+) = \((\w+), (\w+)\)""").find(line)!!
            val (source, left, right) = match.destructured
            map[source] = Pair(left, right)
        }
        return Pair(instruction, map)
    }

    fun findDistance(map: Map<String, Pair<String, String>>, startPos: String, targetReached: (pos: String) -> Boolean, instruction: String): Int {
        var pos = startPos
        var count = 0
        while (!targetReached(pos)) {
            val (left, right) = map[pos]!!
            if (instruction[count % instruction.length] == 'L') {
                pos = left
            } else {
                pos = right
            }
            count += 1
        }
        return count
    }

    fun part1(input: List<String>): Int {
        val (instruction, map) = parseInput(input)
        return findDistance(map, "AAA", { it == "ZZZ"}, instruction)
    }

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

    fun lcm(numbers: List<Long>): Long = numbers.reduce { a, b -> a * b / gcd(a, b) }

    fun part2(input: List<String>): Long {
        val (instruction, map) = parseInput(input)
        val distances = map.keys.filter { it.endsWith("A") }
            .map { findDistance(map, it, { it.endsWith("Z") }, instruction).toLong() }
        return lcm(distances)
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day08_test")
//    check(part1(testInput) == 6)
    check(part2(testInput) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
