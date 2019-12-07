package advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day5Tests {
    @Test
    fun `Part 1 - Simple matching example`() {
        val intCode = IntCode()
        val list = mutableListOf(1002, 4, 3, 4, 33)
        intCode.compute(list)
        assertIterableEquals(listOf(1002, 4, 3, 4, 99), list)
    }

    @Test
    fun `Part 2 - 1 if equals 8`() {
        val list = listOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
        val intCode = IntCode(8)
        intCode.compute(list.toMutableList())
        assertEquals(1, intCode.outputs.last())
        val intCode2 = IntCode(1)
        intCode2.compute(list.toMutableList())
        assertEquals(0, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - 1 if less than 8`() {
        val list = listOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
        val intCode = IntCode(1)
        intCode.compute(list.toMutableList())
        assertEquals(1, intCode.outputs.last())
        val intCode2 = IntCode(12)
        intCode2.compute(list.toMutableList())
        assertEquals(0, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - 1 if equals 8 immediate`() {
        val list = listOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
        val intCode = IntCode(8)
        intCode.compute(list.toMutableList())
        assertEquals(1, intCode.outputs.last())
        val intCode2 = IntCode(1)
        intCode2.compute(list.toMutableList())
        assertEquals(0, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - 1 if less than 8 immediate`() {
        val list = listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
        val intCode = IntCode(1)
        intCode.compute(list.toMutableList())
        assertEquals(1, intCode.outputs.last())
        val intCode2 = IntCode(12)
        intCode2.compute(list.toMutableList())
        assertEquals(0, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - jump test 1`() {
        val list = listOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
        val intCode = IntCode(0)
        intCode.compute(list.toMutableList())
        assertEquals(0, intCode.outputs.last())
        val intCode2 = IntCode(12)
        intCode2.compute(list.toMutableList())
        assertEquals(1, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - jump test immediate`() {
        val list = listOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
        val intCode = IntCode(0)
        intCode.compute(list.toMutableList())
        assertEquals(0, intCode.outputs.last())
        val intCode2 = IntCode(12)
        intCode2.compute(list.toMutableList())
        assertEquals(1, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - complete example`() {
        val list = listOf(3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99)
        val intCode = IntCode(5)
        intCode.compute(list.toMutableList())
        assertEquals(999, intCode.outputs.last())
        val intCode2 = IntCode(8)
        intCode2.compute(list.toMutableList())
        assertEquals(1000, intCode2.outputs.last())
        val intCode3 = IntCode(12)
        intCode3.compute(list.toMutableList())
        assertEquals(1001, intCode3.outputs.last())
    }


}
