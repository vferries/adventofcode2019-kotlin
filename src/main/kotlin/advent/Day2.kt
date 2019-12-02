package advent

import java.io.File

class Day2 {
    fun processCode(list: MutableList<Int>): MutableList<Int> {
        var currentIndex = 0
        while (list[currentIndex] != 99) {
            when (list[currentIndex]) {
                1 -> list[list[currentIndex + 3]] = list[list[currentIndex + 1]] + list[list[currentIndex + 2]]
                2 -> list[list[currentIndex + 3]] = list[list[currentIndex + 1]] * list[list[currentIndex + 2]]
            }
            currentIndex += 4
        }
        return list
    }


    fun initializeListWithCopy(list: List<Int>, noun: Int, verb: Int): MutableList<Int> {
        val mutableList = list.toMutableList()
        mutableList[1] = noun
        mutableList[2] = verb
        return mutableList
    }
}

fun main() {
    val day2 = Day2()
    val numbers = File("src/main/resources/day2.txt").readText().split(",").map(String::toInt).toList()
    val list = day2.initializeListWithCopy(numbers, 12, 2)
    val result = day2.processCode(list)
    println("PART 1")
    println(result[0])
    println("PART 2")
    val (noun, verb) = (0..99).flatMap { noun ->
        (0..99).map { verb ->
            (noun to verb) to day2.processCode(day2.initializeListWithCopy(numbers, noun, verb))[0]
        }
    }.find { (_, result) ->
        result == 19690720
    }?.first!!
    println("Found $noun, $verb, with a result of ${100 * noun + verb}")
}
