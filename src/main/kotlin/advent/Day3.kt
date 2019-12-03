package advent

import java.io.File
import kotlin.math.absoluteValue

class Day3 {
    private fun calculateAllPositions(path: String): Set<Position> {
        val set = hashSetOf<Position>()
        val elems = path.split(",")
        var currentPosition = Position(0, 0)
        elems.forEach {
            val next =
                when (it[0]) {
                    'U' -> Position(0, -1)
                    'D' -> Position(0, 1)
                    'L' -> Position(-1, 0)
                    'R' -> Position(1, 0)
                    else -> throw UnsupportedOperationException()
                }
            val count = it.substring(1).toInt()
            repeat(count) {
                currentPosition = Position(currentPosition.x + next.x, currentPosition.y + next.y)
                set.add(currentPosition)
            }
        }
        return set
    }

    private fun positionsWithStepCount(path: String): Map<Position, Int> {
        val map = hashMapOf<Position, Int>()
        val elems = path.split(",")
        var currentPosition = Position(0, 0)
        var stepCount = 1
        elems.forEach {
            val next =
                    when (it[0]) {
                        'U' -> Position(0, -1)
                        'D' -> Position(0, 1)
                        'L' -> Position(-1, 0)
                        'R' -> Position(1, 0)
                        else -> throw UnsupportedOperationException()
                    }
            val count = it.substring(1).toInt()
            repeat(count) {
                currentPosition = Position(currentPosition.x + next.x, currentPosition.y + next.y)
                map[currentPosition] = stepCount++
            }
        }
        return map
    }

    fun manhattanIntersectingDistance(paths: List<String>): Int {
        val positions = paths.map(this::calculateAllPositions)
        val intersections = positions[0].intersect(positions[1])
        return intersections.map { (x, y) -> x.absoluteValue + y.absoluteValue }.min() ?: -1
    }

    fun steps(paths: List<String>): Int {
        val positions = paths.map(this::positionsWithStepCount)
        val commonKeys = positions[0].keys.intersect(positions[1].keys)
        return commonKeys.map { positions[0].getValue(it) + positions[1].getValue(it) }.min() ?: -1
    }
}

fun main() {
    val day3 = Day3()
    val lines = File("src/main/resources/day3.txt").readLines()
    println("PART 1")
    println(day3.manhattanIntersectingDistance(lines))
    println("PART 2")
    println(day3.steps(lines))
}
