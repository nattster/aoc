import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    data class Directions(val n: Boolean, val s: Boolean, val w: Boolean, val e: Boolean)
    data class Node(val y: Int, val x: Int)

    fun getPossibleDirections(map: List<String>, node: Node): List<Node> {
        val width = map[0].length
        val height = map.size

        val current = map[node.y][node.x]
        val out = mutableListOf<Node>()
        val (n, s, w, e) = when (current) {
            'S' -> Directions(true, true, true, true)
            '|' -> Directions(true, true, false, false)
            '-' -> Directions(false, false, true, true)
            'F' -> Directions(false, true, false, true)
            '7' -> Directions(false, true, true, false)
            'J' -> Directions(true, false, true, false)
            'L' -> Directions(true, false, false, true)
            '.' -> Directions(false, false, false, false)
            else -> Directions(false, false, false, false)
        }
        if (w && node.x - 1 >= 0 && setOf('-', 'L', 'F').contains(map[node.y][node.x - 1])) {
            out.add(Node(node.y, node.x - 1))
        }
        if (e && node.x + 1 < width && setOf('-', 'J', '7').contains(map[node.y][node.x + 1])) {
            out.add(Node(node.y, node.x + 1))
        }
        if (s && node.y + 1 < height && setOf('|', 'L', 'J').contains(map[node.y + 1][node.x])) {
            out.add(Node(node.y + 1, node.x))
        }
        if (n && node.y - 1 >= 0 && setOf('|', '7', 'F').contains(map[node.y - 1][node.x])) {
            out.add(Node(node.y - 1, node.x))
        }
        return out
    }

    data class Path(val previousNode: MutableMap<Node, Node>, val source: Node, val target: Node, val dist: Int)
    fun findPath(map: List<String>): Path {
        val width = map[0].length
        val height = map.size

        val q: Queue<Node> = LinkedList()
        // find starting position
        for (y in 0..<height) {
            for (x in 0..<width) {
                if (map[y][x] == 'S') {
                    q.add(Node(y, x))
                }
            }
        }

        val source = q.first()
        val distances: MutableMap<Node, Int> = mutableMapOf()
        distances[source] = 0
        val visited: MutableSet<Node> = mutableSetOf()
        val previousNode: MutableMap<Node, Node> = mutableMapOf()
        while(q.isNotEmpty()) {
            val node = q.remove()
            visited.add(node)

            val nextPos = getPossibleDirections(map, node)
            for (pos in nextPos) {
                if (distances.contains(pos)) {
                    // check if same distance from two direction?
                    if (distances[node]!! + 1 == distances[pos]) {
                        // found answer
                        return Path(previousNode, source, pos, distances[pos]!!)
                    }
                    continue
                }
                if (!q.contains(pos)) {
                    distances[pos] = distances[node]!! + 1
                    previousNode[pos] = node
                    q.add(pos)
                }
            }
        }
        throw Exception("could not find path")
    }
    fun part1(map: List<String>): Int = findPath(map).dist
    fun insidePolygon(polygon: List<Node>, p: Node): Boolean {
        var counter = 0
        var p1 = polygon.first()
        val N = polygon.size
        for (i in 1..N) {
            val p2 = polygon[i % N]
            if (p.y > min(p1.y, p2.y)) {
                if (p.y <= max(p1.y, p2.y)) {
                    if (p.x <= max(p1.x, p2.x)) {
                        if (p1.y != p2.y) {
                            val xinters = ((p.y - p1.y) * (p2.x - p1.x)).toDouble() / (p2.y - p1.y)+p1.x
                            if (p1.x == p2.x || p.x <= xinters) {
                                counter++
                            }
                        }
                    }
                }
            }
            p1 = p2
        }
        return counter % 2 == 1
    }
    fun part2(input: List<String>): Int {
        val path = findPath(input)

        // rebuild polygon of loop
        val boundary = mutableListOf<Node>()
        boundary.add(path.target)
        val prevNodes = getPossibleDirections(input, path.target)
        var node = prevNodes.first()
        while (node != path.source) {
            boundary.add(node)
            node = path.previousNode[node]!!
        }
        boundary.add(path.source)

        // another route from source to target (need to reverse to build correct polygon)
        node = prevNodes.get(1)
        val secondPath = mutableListOf<Node>()
        while (node != path.source) {
            secondPath.add(node)
            node = path.previousNode[node]!!
        }
        boundary.addAll(secondPath.reversed())

        var inCount = 0
        for (y in 0..<input.size) {
            for (x in 0..<input[0].length) {
                if (!boundary.contains(Node(y, x))) {
                    if (insidePolygon(boundary, Node(y, x))) {
                        inCount += 1
                        print("I")
                    } else {
                        print("O")
                    }
                } else {
                    print(input[y][x])
                }
            }
            println()
        }
        return inCount
    }

    // test if implementation meets criteria from the description, like:/**/
    val testInput = readInput("Day10_test")
//    check(part1(testInput) == 4)
    check(part2(testInput) == 10)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
