package advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day7Tests {
    @Test
    fun `Part 1 - Simple matching example`() {
        val program = listOf(3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0)
        val settings = listOf(4,3,2,1,0).map(Int::toBigInteger)
        val result = calculateThrusterSignal(program, settings)
        assertEquals(43210.toBigInteger(), result)
    }

    @Test
    fun `Part 1 - Simple matching example 2`() {
        val program = listOf(3,23,3,24,1002,24,10,24,1002,23,-1,23,
                101,5,23,23,1,24,23,23,4,23,99,0,0)
        val settings = listOf(0,1,2,3,4).map(Int::toBigInteger)
        val result = calculateThrusterSignal(program, settings)
        assertEquals(54321.toBigInteger(), result)
    }

    @Test
    fun `Part 1 - Simple matching example 3`() {
        val program = listOf(3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,
                1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0)
        val settings = listOf(1,0,4,3,2).map(Int::toBigInteger)
        val result = calculateThrusterSignal(program, settings)
        assertEquals(65210.toBigInteger(), result)
    }

    @Test
    fun `Part 2 - Simple matching example 3`() {
        val program = listOf(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
                27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5)
        val settings = listOf(9,8,7,6,5).map(Int::toBigInteger)
        val result = calculateThrusterSignal(program, settings)
        assertEquals(139629729.toBigInteger(), result)
    }
}
