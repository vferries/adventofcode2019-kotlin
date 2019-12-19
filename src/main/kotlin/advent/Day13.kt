package advent

import java.math.BigInteger
import kotlin.math.sign

fun main() {
    val program = loadFile("day13.txt").readText().split(",").map(String::toBigInteger)
    val interpreter = IntCode(program.toMutableList(), mutableListOf())
    interpreter.compute()
    interpreter.outputs.filterIndexed { index, _ -> index % 3 == 2 }.count { it == 2.toBigInteger() }
    val map = mutableMapOf<Pos, Int>()
    interpreter.outputs.map(BigInteger::toInt).chunked(3).map { (x, y, tile) ->
        map[x to y] = tile
    }
    printBoard(map)

    val mutableProgram = program.toMutableList()
    mutableProgram[0] = 2.toBigInteger()
    val game = IntCode(mutableProgram, mutableListOf())
    var score = 0
    var paddleX = 0
    var ballX = 0

    var initialized = false
    game.inputs.add(BigInteger.ZERO)
    while (!game.finished) {
        game.processToNextOutput()
        game.processToNextOutput()
        game.processToNextOutput()
        val (x, y, tile) = game.outputs.takeLast(3).map(BigInteger::toInt)
        if (x == -1 && y == 0) {
            score = tile
            initialized = true
        } else {
            map[x to y] = tile
            if (tile == 4) {
                ballX = x
            }
            if (tile == 3) {
                paddleX = x
            }
        }
        if (initialized && tile == 4) {
            printBoard(map)
            //TODO Find last ball position and update joystick
            game.inputs.add((ballX-paddleX).sign.toBigInteger())
        }
    }
    println("Finished with score $score, won = ${!map.containsValue(2)}")
}
// > 14182

fun printBoard(map: MutableMap<Pos, Int>) {
    val minX = map.keys.map { it.first }.min()!!
    val maxX = map.keys.map { it.first }.max()!!
    val minY = map.keys.map { it.second }.min()!!
    val maxY = map.keys.map { it.second }.max()!!
    (minY..maxY).forEach { y ->
        (minX..maxX).forEach { x ->
            print(when (map[x to y]) {
                1 -> '▓'
                2 -> '■'
                3 -> '_'
                4 -> 'O'
                else -> ' '
            })
        }
        println()
    }
    println()
}