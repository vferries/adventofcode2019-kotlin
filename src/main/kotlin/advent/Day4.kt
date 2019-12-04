package advent

import java.io.File
import kotlin.math.absoluteValue

class Day4 {
    fun meetCriteria(number: Int): Boolean {
        val digits = number.toString().chunked(1).map(String::toInt)
        //Two adjacent digits are the same (like 22 in 122345).
        val adjacentNumbers = digits.filterIndexed { index, n ->
            index != 0 && digits[index - 1] == n
        }.isNotEmpty()
        //Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
        val increasing = digits.filterIndexed { index, n ->
            index != 0 && digits[index - 1] > n
        }.isEmpty()
        return adjacentNumbers && increasing
    }

    fun meetCriteria2(number: Int): Boolean {
        val digits = number.toString().chunked(1).map(String::toInt)
        val hasDouble = digits.groupBy { it }.values.map { it.size }.contains(2)
        return meetCriteria(number) && hasDouble
    }
}

fun main() {
    val day4 = Day4()
    println("PART 1")
    //356261-846303
    println((356261..846303).count(day4::meetCriteria))
    println("PART 2")
    println((356261..846303).count(day4::meetCriteria2))
}
