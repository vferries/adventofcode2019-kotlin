package advent

import java.math.BigInteger

fun main() {
    val program = loadFile("day19.txt").readText().split(",").map(String::toBigInteger)
    val positions = (0..49).flatMap { x -> (0..49).map { y -> x to y } }
    val attractedCount = positions.count { (x, y) -> checkIfAttracted(program, x, y) }
    println("Attracted count = $attractedCount")

    val leftMostPoints = mutableListOf(Pos(2,4))
    while(true) {
        val lastPoint = leftMostPoints.last()
        if (lastPoint.second >= 99 && checkIfAttracted(program, lastPoint.first + 99, lastPoint.second - 99)) {
            println("Found point ${lastPoint.first*10000 + lastPoint.second - 99}")
            break
        }

        var leftShift = 0
        var attracted = false
        while (!attracted) {
            attracted = checkIfAttracted(program, lastPoint.first + leftShift, lastPoint.second + 1)
            if (!attracted) leftShift++
        }
        leftMostPoints.add(Pos(lastPoint.first + leftShift, lastPoint.second + 1))
    }
}

fun checkIfAttracted(program: List<BigInteger>, x: Int, y: Int): Boolean {
    val interpreter = IntCode(program.toMutableList(), mutableListOf(x.toBigInteger(), y.toBigInteger()))
    interpreter.compute()
    return interpreter.outputs.last() == BigInteger.ONE
}
