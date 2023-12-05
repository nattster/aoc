import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {

    data class MapEntry(val sourceRange: LongRange, val destinationOffset: Long)
    fun parseMapEntry(entry: String): MapEntry {
        val nums = entry.split(" ").map { it.toLong() }
        return MapEntry(nums[1]..< (nums[1]+nums[2]), nums[0])
    }
    fun parseInput(input: List<String>): Pair<List<Long>, List<List<MapEntry>>> {
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
        return Pair(seeds, allMaps)
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
        val (seeds, allMaps) = parseInput(input)
        // iterate through each seed
        return seeds.minOf { getNestedMap(allMaps, it) }
    }
    fun overlap(range1: LongRange, range2: LongRange): Boolean {
        // check for not overlap
        return !(range1.last < range2.first || range2.last < range1.first)
    }
    fun map(range: LongRange, mapEntry: MapEntry) =
        LongRange(range.first - mapEntry.sourceRange.start + mapEntry.destinationOffset,
            range.last - mapEntry.sourceRange.start + mapEntry.destinationOffset)
    fun propagateRange(ranges: List<LongRange>, allMaps: List<List<MapEntry>>): Long {
        if (allMaps.isEmpty()) {
            return ranges.minOf { it.first }
        }
        val currentMap = allMaps.first()
        val nextStepRange = mutableListOf<LongRange>()
        var activeRanges = LinkedList(ranges)
        while (activeRanges.isNotEmpty()) {
            val range = activeRanges.remove()
            var mapped = false
            for (nextRange in currentMap) {
                if (overlap(range, nextRange.sourceRange)) {
                    val start = max(range.first, nextRange.sourceRange.first)
                    val end = min(range.last, nextRange.sourceRange.last)
                    nextStepRange.add(map(LongRange(start, end), nextRange))
                    if (range.first < start) {
                        activeRanges.add(LongRange(range.first, start-1))
                    }
                    if (range.last > end) {
                        activeRanges.add(LongRange(end+1, range.last))
                    }
                    mapped = true
                    break
                }
            }
            if (!mapped) {
                // pass it unmapped
                nextStepRange.add(range)
            }
        }
        return propagateRange(nextStepRange, allMaps.drop(1))
    }

    fun part2(input: List<String>): Long {
        val (seeds, allMaps) = parseInput(input)
        val ranges = seeds.chunked(2).mapTo(mutableListOf()) { LongRange(it[0], it[0]+it[1]-1) }
        return propagateRange(ranges, allMaps)
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
