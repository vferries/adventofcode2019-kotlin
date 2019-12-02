package advent

import java.io.File

class Day1 {
    fun fuelRequired(mass: Int): Int {
        return (mass/3) - 2
    }

    fun fuelRequiredWithFuelMass(mass: Int): Int {
        var total = 0
        var next = fuelRequired(mass)
        while (next >= 0) {
            total += next
            next = fuelRequired(next)
        }
        return total
    }
}

fun main() {
    val day1 = Day1()
    val lines = File("src/main/resources/day1.txt").readLines().map(String::toInt)
    println("PART 1")
    val sum1 = lines.map(day1::fuelRequired).sum()
    println(sum1)
    println("PART 2")
    val sum2 = lines.map(day1::fuelRequiredWithFuelMass).sum()
    println(sum2)
}
