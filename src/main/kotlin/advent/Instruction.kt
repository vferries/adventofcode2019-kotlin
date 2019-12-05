package advent

import java.lang.UnsupportedOperationException

sealed class Instruction(val length: Int) {
    abstract fun process(list: MutableList<Int>)
}

abstract class InstructionWithMode(val modes: List<Int>, length: Int): Instruction(length) {
    protected fun accessWithMode(index: Int, value: Int, list: MutableList<Int>): Int =
            when (modes.getOrElse(index) {0}) {
                0 -> list[value]
                1 -> value
                else -> throw UnsupportedOperationException("Unknown mode ${modes.getOrElse(index) {0}}")
            }
}

class AddInstruction(val operand1: Int, val operand2: Int, val destination: Int, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(list: MutableList<Int>) {
        list[destination] = accessWithMode(0, operand1, list) + accessWithMode(1, operand2, list)
    }
}

class MulInstruction(val operand1: Int, val operand2: Int, val destination: Int, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(list: MutableList<Int>) {
        list[destination] = accessWithMode(0, operand1, list) * accessWithMode(1, operand2, list)
    }
}

class HaltInstruction : Instruction(1) {
    override fun process(list: MutableList<Int>) = Unit
}

class InputInstruction(val destination: Int, val input: Int, modes: List<Int>) : Instruction(2) {
    override fun process(list: MutableList<Int>) {
        list[destination] = input
    }
}

class OutputInstruction(val destination: Int, modes: List<Int>) : Instruction(2) {
    override fun process(list: MutableList<Int>) {
        println("Output = ${list[destination]}")
    }
}