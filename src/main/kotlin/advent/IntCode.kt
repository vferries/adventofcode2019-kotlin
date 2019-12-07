package advent

import java.lang.UnsupportedOperationException

class IntCode(private val input: Int = 1) {
    var outputs: MutableList<Int> = mutableListOf()

    fun compute(list: MutableList<Int>) {
        var currentIndex = 0
        while (list[currentIndex] % 100 != 99) {
            println("$currentIndex, $list")
            val current = list[currentIndex]
            val modes = (current / 100).toString().map(Char::toString).map(String::toInt).reversed()
            val opCode = current % 100
            val instruction: Instruction = when (opCode) {
                1 -> AddInstruction(list[currentIndex + 1], list[currentIndex + 2], list[currentIndex + 3], modes)
                2 -> MulInstruction(list[currentIndex + 1], list[currentIndex + 2], list[currentIndex + 3], modes)
                // Opcode 3 takes a single integer as input and saves it to the position given by its only parameter. For example, the instruction 3,50 would take an input value and store it at address 50.
                3 -> InputInstruction(list[currentIndex + 1], input, modes)
                // Opcode 4 outputs the value of its only parameter. For example, the instruction 4,50 would output the value at address 50.
                4 -> OutputInstruction(list[currentIndex + 1], modes, outputs)
                // Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets the instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                5 -> JumpIfTrue(list[currentIndex + 1], list[currentIndex + 2], modes)
                // Opcode 6 is jump-if-false: if the first parameter is zero, it sets the instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                6 -> JumpIfFalse(list[currentIndex + 1], list[currentIndex + 2], modes)
                // Opcode 7 is less than: if the first parameter is less than the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                7 -> LessThan(list[currentIndex + 1], list[currentIndex + 2], list[currentIndex + 3], modes)
                // Opcode 8 is equals: if the first parameter is equal to the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                8 -> Equals(list[currentIndex + 1], list[currentIndex + 2], list[currentIndex + 3], modes)
                99 -> HaltInstruction()
                else -> throw UnsupportedOperationException("Unknown code ${opCode}")
            }
            currentIndex = instruction.process(currentIndex, list)
        }
        println("Ending with $currentIndex, ${list[currentIndex] % 100}")
    }
}