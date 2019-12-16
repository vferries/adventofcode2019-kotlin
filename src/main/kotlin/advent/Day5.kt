package advent

fun main() {
    val numbers = loadFile("day5.txt").readText().split(",").map(String::toInt)
    val interpreter = IntCode(numbers.map(Int::toBigInteger).toMutableList())
    println("PART 1")
    interpreter.compute()
    println(interpreter.outputs.last())
    println("PART 2")
    val interpreter2 = IntCode(numbers.map(Int::toBigInteger).toMutableList(), mutableListOf(5.toBigInteger()))
    interpreter2.compute()
    println(interpreter2.outputs.last())
}
