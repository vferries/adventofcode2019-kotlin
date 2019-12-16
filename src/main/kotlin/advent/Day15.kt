package advent

import java.lang.UnsupportedOperationException
import java.math.BigInteger

fun main() {
    val program = loadFile("day15.txt").readText().split(",").map(String::toBigInteger)
    val currentPos = Pos(0, 0)
    val visited = mutableSetOf(currentPos)
    var nextMoves = (1..4).map { listOf(it) to Pos(0, 0) }
    val map = mutableMapOf(currentPos to 1)
    while (nextMoves.isNotEmpty()) {
        nextMoves = nextMoves.flatMap { (directions, position) ->
            val currentType = processToCurrentPos(program, directions)
            map[position] = currentType
            var wall = false
            when (currentType) {
                2 -> {
                    println("Found the source after ${directions.size} moves")
                }
                0 -> {
                    wall = true
                }
            }
            if (wall) {
                listOf()
            } else {
                (1..4).filter {
                    !visited.contains(newPos(it, position))
                }.map {
                    visited.add(newPos(it, position))
                    Pair(directions + it, newPos(it, position))
                }
            }
        }
    }
    (-21..21).forEach { y ->
        (-20..22).forEach { x ->
            print(map.getOrDefault(x to y, 8))
        }
        println()
    }
    var steps = 0
    while (map.values.contains(1)) {
        val nexts = map.filter { (pos, value) -> value == 1 && (1..4).map { newPos(it, pos) }.any { map[it] == 2 } }
        if (nexts.isNotEmpty()) println("Next = ${nexts.map { it.key }}, remaining = ${map.filter { it.value == 1 }.map { it.key }}, steps = $steps")
        steps++
        nexts.forEach { (p, _) -> map[p] = 2 }
    }
    println("Needs $steps steps")
    // TODO Never finishes, stuck after 319 steps, with two missing, correct result = 320
}

fun processToCurrentPos(program: List<BigInteger>, direction: List<Int>): Int {
    val interpreter = IntCode(program.toMutableList(), direction.map(Int::toBigInteger).toMutableList())
    while (interpreter.outputs.size < direction.size) {
        interpreter.processToNextOutput()
    }
    return interpreter.outputs.last().toInt()
}

fun newPos(direction: Int, pos: Pos) = when (direction) {
    1 -> Pos(pos.first, pos.second + 1)
    2 -> Pos(pos.first, pos.second - 1)
    3 -> Pos(pos.first + 1, pos.second)
    4 -> Pos(pos.first - 1, pos.second)
    else -> throw UnsupportedOperationException("Unknown direction $direction")
}

