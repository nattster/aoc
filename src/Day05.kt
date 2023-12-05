fun main() {

    data class MapEntry(val sourceRange: LongRange, val destinationOffset: Long)
    fun parseMapEntry(entry: String): MapEntry {
        val nums = entry.split(" ").map { it.toLong() }
        return MapEntry(nums[1]..< (nums[1]+nums[2]), nums[0])
    }
    fun getMap(map: List<MapEntry>, seed: Long): Long {
        for (item in map) {
            if (seed in item.sourceRange) {
                return item.destinationOffset + seed - item.sourceRange.first
            }
        }
        return seed
    }
    fun getNestedMap(map: List<List<MapEntry>>, seed: Long): Long {
        if (map.isEmpty()) {
            return seed
        }
        val mapping = getMap(map.first(), seed)
        return getNestedMap(map.drop(1), mapping)
    }
    fun part1(input: List<String>): Long {
        val seeds = input[0].split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        var currentMap = mutableListOf<MapEntry>()
        val allMaps = mutableListOf<List<MapEntry>>()
        for(line in input.drop(1)) {
            if (line.isNotEmpty()) {
                if (line.contains("map")) {
                    if (currentMap.isNotEmpty()) {
                        allMaps.add(currentMap)
                    }
                    currentMap = mutableListOf()
                } else {
                    currentMap.add(parseMapEntry(line))
                }
            }
        }
        if (currentMap.isNotEmpty()) {
            allMaps.add(currentMap)
        }
        // iterate through each seed
        return seeds.minOf { getNestedMap(allMaps, it) }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
//    check(part2(testInput) == 30L)

    val input = readInput("Day05")
    part1(input).println()
//    part2(input).println()
}
