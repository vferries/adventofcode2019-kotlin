package advent

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

typealias Pos = Pair<Int, Int>

fun bestSlotForMap(lines: List<String>): Pair<Pos, List<Pos>> {
    val asteroidPositions = (0 until lines.size)
            .flatMap { x -> (0 until lines[0].length).map { x to it } }
            .filter { (x, y) -> lines[y][x] == '#' }
    val countByPos = asteroidPositions.map { (x, y) ->
        val recentered = asteroidPositions.map { (i, j) -> i - x to j - y }.filter { (i, j) -> !(i == 0 && j == 0)}
        val filtered = recentered.filter { (x1, y1) -> recentered
                .none { (x2, y2) ->
                    // Same side
                    ((x1 != 0 && x1.sign == x2.sign) || (y1 != 0 && y1.sign == y2.sign)) &&
                            // Closer
                            (x1.absoluteValue > x2.absoluteValue || y1.absoluteValue > y2.absoluteValue) &&
                            // Points aligned
                            x1 * y2 == x2 * y1 } }
        (x to y) to filtered
    }
    return countByPos.maxBy { it.second.size }!!
}

fun main(args: Array<String>) {
    val map = File("src/main/resources/day10.txt").readLines()
    val max = bestSlotForMap(map)
    println(max.second.size)
    println(max.first)
    val visibleAsteroids = max.second
    val angles = visibleAsteroids.sortedBy { (x, y) -> toAngle(x, y) }
    println(angles)
    val asteroids = angles.map { (x, y) -> x + max.first.first to y + max.first.second }
    (0 until map.size).forEach { x ->
        (0 until map[0].length).forEach { y ->
            print(if (asteroids.contains(x to y)) "${asteroids.indexOf(x to y)}  ".substring(0, 4) else "    ")
        }
        println()
    }
    val (xDestroyed, yDestroyed) = angles[199]
    val finalX = xDestroyed + max.first.first
    val finalY = yDestroyed + max.first.second
    println(finalX * 100 + finalY)
    // 605 > x < 850
}

fun toAngle(x: Int, y: Int): Double {
    return (540 - Math.toDegrees(Math.atan2(y.toDouble(), x.toDouble()))) % 360
}
