package advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day9Tests {
    @Test
    fun `Quine`() {
        val program = listOf(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99).map(Int::toBigInteger)
        val interpreter = IntCode(program.toMutableList())
        interpreter.compute()
        print(interpreter.outputs)
        assertIterableEquals(program, interpreter.outputs)
    }

    @Test
    fun `16 digits`() {
        val program = listOf(1102,34915192,34915192,7,4,7,99,0).map(Int::toBigInteger)
        val interpreter = IntCode(program.toMutableList())
        interpreter.compute()
        assertEquals(16, interpreter.outputs.last().toString().length)
    }

    @Test
    fun `Large number`() {
        val program = listOf(104,1125899906842624,99).map(Long::toBigInteger)
        val interpreter = IntCode(program.toMutableList())
        interpreter.compute()
        assertEquals(1125899906842624.toBigInteger(), interpreter.outputs.last())
    }
}
