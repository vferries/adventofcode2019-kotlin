package advent

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val numbers = File("src/main/resources/day5.txt").readText().split(",").map(String::toInt)
    val interpreter = IntCode(numbers.toMutableList())
    println("PART 1")
    interpreter.compute()
    println(interpreter.outputs.last())
    println("PART 2")
    val interpreter2 = IntCode(numbers.toMutableList(), mutableListOf(5))
    interpreter2.compute()
    println(interpreter2.outputs.last())
}
