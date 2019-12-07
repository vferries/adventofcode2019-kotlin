package advent

import java.lang.UnsupportedOperationException

sealed class Instruction(val length: Int) {
    abstract fun process(index: Int, list: MutableList<Int>): Int
}

abstract class InstructionWithMode(val modes: List<Int>, length: Int) : Instruction(length) {
    protected fun accessWithMode(index: Int, value: Int, list: MutableList<Int>): Int =
            when (modes.getOrElse(index) { 0 }) {
                0 -> list[value]
                1 -> value
                else -> throw UnsupportedOperationException("Unknown mode ${modes.getOrElse(index) { 0 }}")
            }
}

class AddInstruction(val operand1: Int, val operand2: Int, val destination: Int, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(index: Int, list: MutableList<Int>): Int {
        println("Add")
        list[destination] = accessWithMode(0, operand1, list) + accessWithMode(1, operand2, list)
        return index + length
    }
}

class MulInstruction(val operand1: Int, val operand2: Int, val destination: Int, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(index: Int, list: MutableList<Int>): Int {
        println("Mult")
        list[destination] = accessWithMode(0, operand1, list) * accessWithMode(1, operand2, list)
        return index + length
    }
}

class HaltInstruction : Instruction(1) {
    override fun process(index: Int, list: MutableList<Int>): Int = index + length
}

class InputInstruction(val destination: Int, val input: MutableList<Int>, modes: List<Int>) : Instruction(2) {
    override fun process(index: Int, list: MutableList<Int>): Int {
        println("Input index")
        list[destination] = input.first()
        input.removeAt(0)
        return index + length
    }
}

class OutputInstruction(private val destination: Int, modes: List<Int>, private val outputs: MutableList<Int>) : InstructionWithMode(modes, 2) {
    override fun process(index: Int, list: MutableList<Int>): Int {
        println("Output = $destination")
        outputs.add(accessWithMode(0, destination, list))
        return index + length
    }
}

class JumpIfFalse(val operand1: Int, val destination: Int, modes: List<Int>) : InstructionWithMode(modes, 3) {
    override fun process(index: Int, list: MutableList<Int>): Int {
        println("Jump if false ${accessWithMode(0, operand1, list)}")
        return if (accessWithMode(0, operand1, list) == 0) accessWithMode(1, destination, list) else index + length
    }
}

class JumpIfTrue(val operand1: Int, val destination: Int, modes: List<Int>) : InstructionWithMode(modes, 3) {
    override fun process(index: Int, list: MutableList<Int>): Int {
        println("Jump if true ${accessWithMode(0, operand1, list)}")
        return if (accessWithMode(0, operand1, list) != 0) accessWithMode(1, destination, list) else index + length
    }
}

class LessThan(val operand1: Int, val operand2: Int, val destination: Int, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(index: Int, list: MutableList<Int>): Int {
        println("LessThan ${accessWithMode(0, operand1, list)} < ${accessWithMode(1, operand2, list)}")
        list[destination] = if (accessWithMode(0, operand1, list) < accessWithMode(1, operand2, list)) 1 else 0
        return index + length
    }
}

class Equals(val operand1: Int, val operand2: Int, val destination: Int, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(index: Int, list: MutableList<Int>): Int {
        println("Equals $index, ${accessWithMode(0, operand1, list)} and ${accessWithMode(1, operand2, list)}")
        list[destination] = if (accessWithMode(0, operand1, list) == accessWithMode(1, operand2, list)) 1 else 0
        return index + length
    }
}
