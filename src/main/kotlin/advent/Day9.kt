package advent

import java.math.BigInteger

fun main() {
    val program = loadFile("day9.txt").readText().split(",").map(String::toBigInteger)
    val interpreter = IntCode(program.toMutableList(), mutableListOf(BigInteger.ONE))
    interpreter.compute()
    println("Final output ${interpreter.outputs}")


    val interpreter2 = IntCode(program.toMutableList(), mutableListOf(2.toBigInteger()))
    interpreter2.compute()
    println("Final output ${interpreter2.outputs}")
}
