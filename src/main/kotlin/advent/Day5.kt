package advent

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val interpreter = IntCode()
    val numbers = File("src/main/resources/day5.txt").readText().split(",").map(String::toInt)
    println("PART 1")
    interpreter.compute(numbers.toMutableList())
    println(interpreter.outputs.last())
    println("PART 2")
    val interpreter2 = IntCode(5)
    interpreter2.compute(numbers.toMutableList())
    println(interpreter2.outputs.last())
}
