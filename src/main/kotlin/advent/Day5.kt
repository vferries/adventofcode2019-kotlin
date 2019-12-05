package advent

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val interpreter = IntCode()
    val numbers = File("src/main/resources/day5.txt").readText().split(",").map(String::toInt).toMutableList()
    println("PART 1")
    println(interpreter.compute(numbers))
    println("PART 2")
}
