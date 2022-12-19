package advent

import java.lang.UnsupportedOperationException
import java.math.BigInteger

sealed class Instruction(val length: Int) {
    abstract fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger>
}

abstract class InstructionWithMode(val modes: List<Int>, length: Int) : Instruction(length) {
    protected fun accessWithMode(index: Int, base: BigInteger, value: BigInteger, list: MutableMap<BigInteger, BigInteger>): BigInteger =
            when (modes.getOrElse(index) {0}) {
                0 -> list.getValue(value)
                1 -> value
                2 -> list.getValue(base + value)
                else -> throw UnsupportedOperationException("Unknown mode ${modes.getOrElse(index) {0}}")
            }
}

class AddInstruction(val operand1: BigInteger, val operand2: BigInteger, val destination: BigInteger, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        //println("Add")
        val offset = if (modes.getOrElse(2) {0} == 2) base else BigInteger.ZERO
        list[destination + offset] = accessWithMode(0, base, operand1, list) + accessWithMode(1, base, operand2, list)
        val newIndex = index + length.toBigInteger()
        return newIndex to base
    }
}

class MulInstruction(val operand1: BigInteger, val operand2: BigInteger, val destination: BigInteger, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        //println("Mult")
        val offset = if (modes.getOrElse(2) {0} == 2) base else BigInteger.ZERO
        list[destination + offset] = accessWithMode(0, base, operand1, list) * accessWithMode(1, base, operand2, list)
        val newIndex = index + length.toBigInteger()
        return newIndex to base
    }
}

class HaltInstruction : Instruction(1) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        val newIndex = index + length.toBigInteger()
        return newIndex to base
    }
}

class InputInstruction(val destination: BigInteger, val input: MutableList<BigInteger>, modes: List<Int>) : InstructionWithMode(modes, 2) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        //println("Input index")
        synchronized(input) {
            val offset = if (modes.getOrElse(0) {0} == 2) base else BigInteger.ZERO
            if (input.isNotEmpty()) {
                list[destination + offset] = input.removeAt(0)
            } else {
                list[destination + offset] = BigInteger.valueOf(-1)
            }
            val newIndex = index + length.toBigInteger()
            return newIndex to base
        }
    }
}

class OutputInstruction(private val destination: BigInteger, modes: List<Int>, private val outputs: MutableList<BigInteger>) : InstructionWithMode(modes, 2) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        //println("Output = $destination")
        synchronized(outputs) {
            outputs.add(accessWithMode(0, base, destination, list))
            val newIndex = index + length.toBigInteger()
            return newIndex to base
        }
    }
}

class JumpIfFalse(val operand1: BigInteger, val destination: BigInteger, modes: List<Int>) : InstructionWithMode(modes, 3) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        //println("Jump if false ${accessWithMode(0, base, operand1, list)}")
        val newIndex = if (accessWithMode(0, base, operand1, list) == BigInteger.ZERO)
            accessWithMode(1, base, destination, list)
        else index + length.toBigInteger()
        return newIndex to base
    }
}

class JumpIfTrue(val operand1: BigInteger, val destination: BigInteger, modes: List<Int>) : InstructionWithMode(modes, 3) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        //println("Jump if true ${accessWithMode(0, base, operand1, list)}")
        val newIndex = if (accessWithMode(0, base, operand1, list) != BigInteger.ZERO)
            accessWithMode(1, base, destination, list)
        else index + length.toBigInteger()
        return newIndex to base
    }
}

class LessThan(val operand1: BigInteger, val operand2: BigInteger, val destination: BigInteger, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        //println("LessThan ${accessWithMode(0, base, operand1, list)} < ${accessWithMode(1, base, operand2, list)}")
        val offset = if (modes.getOrElse(2) {0} == 2) base else BigInteger.ZERO
        list[destination + offset] = if (accessWithMode(0, base, operand1, list) < accessWithMode(1, base, operand2, list))
            BigInteger.ONE
        else
            BigInteger.ZERO
        val newIndex = index + length.toBigInteger()
        return newIndex to base
    }
}

class Equals(val operand1: BigInteger, val operand2: BigInteger, val destination: BigInteger, modes: List<Int>) : InstructionWithMode(modes, 4) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        //println("Equals $index, ${accessWithMode(0, base, operand1, list)} and ${accessWithMode(1, base, operand2, list)}")
        val offset = if (modes.getOrElse(2) {0} == 2) base else BigInteger.ZERO
        list[destination + offset] = if (accessWithMode(0, base, operand1, list) == accessWithMode(1, base, operand2, list))
            BigInteger.ONE
        else
            BigInteger.ZERO
        val newIndex = index + length.toBigInteger()
        return newIndex to base
    }
}

class AdjustBase(val operand1: BigInteger, modes: List<Int>) : InstructionWithMode(modes, 2) {
    override fun process(index: BigInteger, base: BigInteger, list: MutableMap<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
        val newIndex = index + length.toBigInteger()
        return newIndex to base + accessWithMode(0, base, operand1, list)
    }
}