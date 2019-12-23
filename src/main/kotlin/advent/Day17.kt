package advent

import java.lang.UnsupportedOperationException
import java.math.BigInteger

fun main() {
    val program = loadFile("day17.txt").readText().split(",").map(String::toBigInteger)
    val interpreter = IntCode(program.toMutableList(), mutableListOf())
    interpreter.compute()
    val map = outputsToMap(interpreter.outputs)
    //println(map.joinToString("\n"))
    println(calculateAlignmentParameters(map))

    var pos = posFromRobot(map)
    var direction = posToDirection(map.valueAtPos(pos))

    val instructions = mutableListOf<Char>()
    val dirCount = Direction.values().size
    do {
        val rightDir = Direction.values()[(direction.ordinal + 1) % dirCount]
        val leftDir = Direction.values()[(dirCount + direction.ordinal - 1) % dirCount]
        val rightPos = nextPos(pos, rightDir)
        val leftPos = nextPos(pos, leftDir)
        if (map.valueAtPos(rightPos) == '#') {
            direction = rightDir
            pos = rightPos
            instructions.add('R')
        } else if (map.valueAtPos(leftPos) == '#') {
            direction = leftDir
            pos = leftPos
            instructions.add('L')
        } else {
            println("Finished")
            break
        }
        var count = 1
        while (map.valueAtPos(nextPos(pos, direction)) == '#') {
            pos = nextPos(pos, direction)
            count++
        }
        instructions.addAll(count.toString().toList())
    }
    while (true)
    println(instructions)
    //println(instructions.filter { it == 'L' || it == 'R' }.size)

/*
    A,B,A,C,B,C,A,B,A,C
    A = R6,L10,R8,R8
    B = R12,L8,L10
    C = R12,L10,R6,L10
    */
    val mainRoutine = listOf('A', ',', 'B', ',', 'A', ',', 'C', ',', 'B', ',', 'C', ',', 'A', ',', 'B', ',', 'A', ',', 'C', '\n')
    println("Main routine: ${mainRoutine.map(Char::toInt)}")
    val a = listOf('R', ',', '6', ',', 'L', ',', '1', '0', ',', 'R', ',', '8', ',', 'R', ',', '8', '\n')
    println("A routine: ${a.map(Char::toInt)}")
    val b = listOf('R', ',', '1', '2', ',', 'L', ',', '8', ',', 'L', ',', '1', '0', '\n')
    println("B routine: ${b.map(Char::toInt)}")
    val c = listOf('R', ',', '1', '2', ',', 'L', ',', '1', '0', ',', 'R', ',', '6', ',', 'L', ',', '1', '0', '\n')
    println("C routine: ${c.map(Char::toInt)}")
    val videoFeed = listOf('n', '\n')
    println("Video feed: ${videoFeed.map(Char::toInt)}")
    val input = (mainRoutine + a + b + c + videoFeed).map(Char::toInt).map(Int::toBigInteger)
    println(input)
    val fullProgram = program.toMutableList()
    fullProgram[0] = 2.toBigInteger()
    val computer = IntCode(fullProgram, input.toMutableList())
    computer.compute()
    println("Finished with value ${computer.outputs.last()}")
}

private fun List<String>.valueAtPos(pos: Pos): Char {
    if (pos.first < 0 || pos.first >= this[0].length || pos.second < 0 || pos.second >= this.size) return '.'
    return this[pos.second][pos.first]
}

fun nextPos(pos: Pos, dir: Direction): Pos {
    return when (dir) {
        Direction.UP -> Pos(pos.first, pos.second - 1)
        Direction.RIGHT -> Pos(pos.first + 1, pos.second)
        Direction.DOWN -> Pos(pos.first, pos.second + 1)
        Direction.LEFT -> Pos(pos.first - 1, pos.second)
    }
}

fun posToDirection(c: Char): Direction =
        when (c) {
            '^' -> Direction.UP
            '>' -> Direction.RIGHT
            'v' -> Direction.DOWN
            '<' -> Direction.LEFT
            else -> throw UnsupportedOperationException("Unknown direction")
        }

fun posFromRobot(map: List<String>): Pos {
    val y = map.indexOfFirst { line -> line.any { it != '#' && it != '.' } }
    val x = map[y].indexOfFirst { it != '#' && it != '.' }
    return Pos(x, y)
}

fun outputsToMap(outputs: List<BigInteger>): List<String> {
    return outputs.map { it.toInt().toChar() }
            .joinToString("")
            .lines()
            .filter { it.isNotEmpty() }
}

fun calculateAlignmentParameters(list: List<String>): Int {
    var sum = 0
    (list.indices).forEach { y ->
        (list[0].indices).forEach { x ->
            if (x > 0 && y > 0 && y < list.lastIndex && x < list[0].lastIndex &&
                    list[y][x] == '#' &&
                    list[y - 1][x] == '#' &&
                    list[y + 1][x] == '#' &&
                    list[y][x - 1] == '#' &&
                    list[y][x + 1] == '#') sum += x * y
        }
    }
    return sum
}
