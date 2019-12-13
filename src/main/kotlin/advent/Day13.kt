package advent

import java.io.File
import java.math.BigInteger

fun main() {
    val program = File("src/main/resources/day13.txt").readText().split(",").map(String::toBigInteger)
    val interpreter = IntCode(program.toMutableList(), mutableListOf())
    interpreter.compute()
    interpreter.outputs.filterIndexed { index, _ -> index % 3 == 2 }.count { it == 2.toBigInteger() }
    var map = mutableMapOf<Pos, Int>()
    interpreter.outputs.map(BigInteger::toInt).chunked(3).map { (x, y, tile) ->
        map[x to y] = tile
    }
    (0..21).forEach { y ->
        (0..42).forEach { x ->
            print(map[x to y])
        }
        println()
    }
    println(map)

    val mutableProgram = program.toMutableList()
    mutableProgram[0] = 2.toBigInteger()
    val game = IntCode(mutableProgram, mutableListOf())
    var score = 0

    //initBoard()
    while (map.containsValue(2) && !game.finished) {
        game.processToNextOutput()
        game.processToNextOutput()
        game.processToNextOutput()
        val (x, y, tile) = game.outputs.takeLast(3).map(BigInteger::toInt)
        println("Received $x,$y tile $tile")
        if (x == -1 && y == 0) {
            score = tile
        } else {
            map[x to y] = tile
            //TODO Find last ball position and update joystick
            game.inputs.add(BigInteger.ZERO)
        }
    }
    println("Finished with score $score, won = ${!map.containsValue(2)}")
}