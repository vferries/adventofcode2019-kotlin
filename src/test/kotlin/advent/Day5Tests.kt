package advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day5Tests {
    @Test
    fun `Part 1 - Simple matching example`() {
        val list = listOf(1002, 4, 3, 4, 33).map(Int::toBigInteger)
        val intCode = IntCode(list.toMutableList(), mutableListOf())
        intCode.compute()
        assertIterableEquals(listOf(1002, 4, 3, 4, 99).map(Int::toBigInteger), intCode.getModifiedProgram())
    }

    @Test
    fun `Part 2 - 1 if equals 8`() {
        val list = listOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8).map(Int::toBigInteger)
        val intCode = IntCode(list.toMutableList(), mutableListOf(8.toBigInteger()))
        intCode.compute()
        assertEquals(BigInteger.ONE, intCode.outputs.last())
        val intCode2 = IntCode(list.toMutableList(), mutableListOf(1.toBigInteger()))
        intCode2.compute()
        assertEquals(BigInteger.ZERO, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - 1 if less than 8`() {
        val list = listOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8).map(Int::toBigInteger)
        val intCode = IntCode(list.toMutableList(), mutableListOf(1.toBigInteger()))
        intCode.compute()
        assertEquals(BigInteger.ONE, intCode.outputs.last())
        val intCode2 = IntCode(list.toMutableList(), mutableListOf(12.toBigInteger()))
        intCode2.compute()
        assertEquals(BigInteger.ZERO, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - 1 if equals 8 immediate`() {
        val list = listOf(3, 3, 1108, -1, 8, 3, 4, 3, 99).map(Int::toBigInteger)
        val intCode = IntCode(list.toMutableList(), mutableListOf(8.toBigInteger()))
        intCode.compute()
        assertEquals(BigInteger.ONE, intCode.outputs.last())
        val intCode2 = IntCode(list.toMutableList(), mutableListOf(BigInteger.ONE))
        intCode2.compute()
        assertEquals(BigInteger.ZERO, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - 1 if less than 8 immediate`() {
        val list = listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99).map(Int::toBigInteger)
        val intCode = IntCode(list.toMutableList(), mutableListOf(BigInteger.ONE))
        intCode.compute()
        assertEquals(BigInteger.ONE, intCode.outputs.last())
        val intCode2 = IntCode(list.toMutableList(), mutableListOf(12.toBigInteger()))
        intCode2.compute()
        assertEquals(BigInteger.ZERO, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - jump test 1`() {
        val list = listOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9).map(Int::toBigInteger)
        val intCode = IntCode(list.toMutableList(), mutableListOf(BigInteger.ZERO))
        intCode.compute()
        assertEquals(BigInteger.ZERO, intCode.outputs.last())
        val intCode2 = IntCode(list.toMutableList(), mutableListOf(12.toBigInteger()))
        intCode2.compute()
        assertEquals(BigInteger.ONE, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - jump test immediate`() {
        val list = listOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1).map(Int::toBigInteger)
        val intCode = IntCode(list.toMutableList(), mutableListOf(0.toBigInteger()))
        intCode.compute()
        assertEquals(BigInteger.ZERO, intCode.outputs.last())
        val intCode2 = IntCode(list.toMutableList(), mutableListOf(12.toBigInteger()))
        intCode2.compute()
        assertEquals(BigInteger.ONE, intCode2.outputs.last())
    }

    @Test
    fun `Part 2 - complete example`() {
        val list = listOf(3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99).map(Int::toBigInteger)
        val intCode = IntCode(list.toMutableList(), mutableListOf(5.toBigInteger()))
        intCode.compute()
        assertEquals(999.toBigInteger(), intCode.outputs.last())
        val intCode2 = IntCode(list.toMutableList(), mutableListOf(8.toBigInteger()))
        intCode2.compute()
        assertEquals(1000.toBigInteger(), intCode2.outputs.last())
        val intCode3 = IntCode(list.toMutableList(), mutableListOf(12.toBigInteger()))
        intCode3.compute()
        assertEquals(1001.toBigInteger(), intCode3.outputs.last())
    }


}
