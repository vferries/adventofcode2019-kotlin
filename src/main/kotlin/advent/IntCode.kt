package advent

import java.lang.UnsupportedOperationException

class IntCode(var program: MutableList<Int>, private var inputs: MutableList<Int> = mutableListOf(1), var outputs: MutableList<Int> = mutableListOf()) {
    var finished = false
        private set
    var currentIndex = 0

    fun processToNextOutput() {
        while (program[currentIndex] % 100 != 99) {
            println("$currentIndex, $program")
            val current = program[currentIndex]
            val modes = (current / 100).toString().map(Char::toString).map(String::toInt).reversed()
            val opCode = current % 100
            val instruction: Instruction = when (opCode) {
                1 -> AddInstruction(program[currentIndex + 1], program[currentIndex + 2], program[currentIndex + 3], modes)
                2 -> MulInstruction(program[currentIndex + 1], program[currentIndex + 2], program[currentIndex + 3], modes)
                // Opcode 3 takes a single integer as input and saves it to the position given by its only parameter. For example, the instruction 3,50 would take an input value and store it at address 50.
                3 -> InputInstruction(program[currentIndex + 1], inputs, modes)
                // Opcode 4 outputs the value of its only parameter. For example, the instruction 4,50 would output the value at address 50.
                4 -> OutputInstruction(program[currentIndex + 1], modes, outputs)
                // Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets the instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                5 -> JumpIfTrue(program[currentIndex + 1], program[currentIndex + 2], modes)
                // Opcode 6 is jump-if-false: if the first parameter is zero, it sets the instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                6 -> JumpIfFalse(program[currentIndex + 1], program[currentIndex + 2], modes)
                // Opcode 7 is less than: if the first parameter is less than the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                7 -> LessThan(program[currentIndex + 1], program[currentIndex + 2], program[currentIndex + 3], modes)
                // Opcode 8 is equals: if the first parameter is equal to the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                8 -> Equals(program[currentIndex + 1], program[currentIndex + 2], program[currentIndex + 3], modes)
                99 -> {
                    finished = true
                    HaltInstruction()
                }
                else -> throw UnsupportedOperationException("Unknown code ${opCode}")
            }
            currentIndex = instruction.process(currentIndex, program)
            if (opCode == 4) break
        }
        if (program[currentIndex] % 100 == 99) {
            finished = true
        }
    }

    fun compute() {
        while (!finished) processToNextOutput()
    }
}