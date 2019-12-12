package advent

import java.math.BigInteger

class IntCode(var program: MutableList<BigInteger>, var inputs: MutableList<BigInteger> = mutableListOf(BigInteger.ONE), var outputs: MutableList<BigInteger> = mutableListOf()) {
    var finished = false
        private set
    var currentIndex = BigInteger.ZERO
    var currentBase = BigInteger.ZERO
    val instructions = program.mapIndexed { index, value -> index.toBigInteger() to value }.toMap().toMutableMap().withDefault { BigInteger.ZERO }

    fun processToNextOutput() {
        while (instructions.getValue(currentIndex).rem(100.toBigInteger()) != 99.toBigInteger()) {
            //println("$currentIndex, $program")
            val current = instructions.getValue(currentIndex)
            val modes = (current.div(100.toBigInteger())).toString().map(Char::toString).map(String::toInt).reversed()
            val opCode = current.rem(100.toBigInteger())
            val instruction: Instruction = when (opCode.toInt()) {
                1 -> AddInstruction(instructions.getValue(currentIndex + 1.toBigInteger()), instructions.getValue(currentIndex + 2.toBigInteger()), instructions.getValue(currentIndex + 3.toBigInteger()), modes)
                2 -> MulInstruction(instructions.getValue(currentIndex + 1.toBigInteger()), instructions.getValue(currentIndex + 2.toBigInteger()), instructions.getValue(currentIndex + 3.toBigInteger()), modes)
                // Opcode 3 takes a single integer as input and saves it to the position given by its only parameter. For example, the instruction 3,50 would take an input value and store it at address 50.
                3 -> InputInstruction(instructions.getValue(currentIndex + 1.toBigInteger()), inputs, modes)
                // Opcode 4 outputs the value of its only parameter. For example, the instruction 4,50 would output the value at address 50.
                4 -> OutputInstruction(instructions.getValue(currentIndex + 1.toBigInteger()), modes, outputs)
                // Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets the instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                5 -> JumpIfTrue(instructions.getValue(currentIndex + 1.toBigInteger()), instructions.getValue(currentIndex + 2.toBigInteger()), modes)
                // Opcode 6 is jump-if-false: if the first parameter is zero, it sets the instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                6 -> JumpIfFalse(instructions.getValue(currentIndex + 1.toBigInteger()), instructions.getValue(currentIndex + 2.toBigInteger()), modes)
                // Opcode 7 is less than: if the first parameter is less than the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                7 -> LessThan(instructions.getValue(currentIndex + 1.toBigInteger()), instructions.getValue(currentIndex + 2.toBigInteger()), instructions.getValue(currentIndex + 3.toBigInteger()), modes)
                // Opcode 8 is equals: if the first parameter is equal to the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                8 -> Equals(instructions.getValue(currentIndex + 1.toBigInteger()), instructions.getValue(currentIndex + 2.toBigInteger()), instructions.getValue(currentIndex + 3.toBigInteger()), modes)
                // Opcode 9 adjusts the relative base by the value of its only parameter.
                9 -> AdjustBase(instructions.getValue(currentIndex + 1.toBigInteger()), modes)
                99 -> {
                    finished = true
                    HaltInstruction()
                }
                else -> throw UnsupportedOperationException("Unknown code $opCode")
            }
            val (index, base) = instruction.process(currentIndex, currentBase, instructions)
            currentIndex = index
            currentBase = base
            if (opCode == 4.toBigInteger()) break
        }
        if (instructions.getValue(currentIndex).rem(100.toBigInteger()) == 99.toBigInteger()) {
            finished = true
        }
    }

    fun compute() {
        while (!finished) processToNextOutput()
    }

    fun getModifiedProgram(): List<BigInteger> {
        return (0 until instructions.keys.size).map { instructions.getValue(it.toBigInteger()) }
    }
}