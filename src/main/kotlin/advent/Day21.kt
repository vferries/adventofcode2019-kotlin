package advent

import java.math.BigInteger
import kotlin.streams.toList

object Day21 {
    fun part1(): String {
        val program = loadFile("day21.txt").readText().split(',').map { BigInteger(it) }.toMutableList()
        val inputs = mutableListOf<BigInteger>()
        val instructions = listOf("NOT D T", "OR C T", "AND A T", "NOT T J", "WALK").joinToString("\n", "", "\n")
        val outputs = mutableListOf<BigInteger>()
        inputs.addAll(instructions.codePoints().toList().map { it.toBigInteger() })
        val intCode = IntCode(program, inputs, outputs)
        while (!intCode.finished) {
            intCode.compute()
        }
        return outputs.last().toString()
    }

    fun part2(): String {
        val program = loadFile("day21.txt").readText().split(',').map { BigInteger(it) }.toMutableList()
        val inputs = mutableListOf<BigInteger>()
        val instructions = listOf(
            "NOT C J",
            "AND D J",
            "AND H J",
            "NOT B T",
            "AND D T",
            "OR T J",
            "NOT A T",
            "OR T J",
            "RUN"
        ).joinToString("\n", "", "\n")
        println(instructions)
        val outputs = mutableListOf<BigInteger>()
        inputs.addAll(instructions.codePoints().toList().map { it.toBigInteger() })
        println(instructions.codePoints().toList())
        val intCode = IntCode(program, inputs, outputs)
        while (!intCode.finished) {
            intCode.compute()
        }
        return outputs.last().toString()
    }
}