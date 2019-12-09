package advent

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

class Day2Tests {
    @Test
    fun `Simple Example Full`() {
        val list = listOf(1,9,10,3,2,3,11,0,99,30,40,50).map(Int::toBigInteger).toMutableList()
        val interpreter = IntCode(list)
        interpreter.compute()
        assertIterableEquals(listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50).map(Int::toBigInteger), interpreter.getModifiedProgram())
    }

    @Test
    fun `Simple Example (1 + 1 = 2)`() {
        val list = listOf(1,0,0,0,99).map(Int::toBigInteger).toMutableList()
        val interpreter = IntCode(list)
        interpreter.compute()
        assertIterableEquals(listOf(2,0,0,0,99).map(Int::toBigInteger), interpreter.getModifiedProgram())
    }

    @Test
    fun `Simple Example (3 * 2 = 6)`() {
        val list = listOf(2,3,0,3,99).map(Int::toBigInteger).toMutableList()
        val interpreter = IntCode(list)
        interpreter.compute()
        assertIterableEquals(listOf(2,3,0,6,99).map(Int::toBigInteger), interpreter.getModifiedProgram())
    }

    @Test
    fun `Simple Example (99 * 99 = 9801`() {
        val list = listOf(2,4,4,5,99,0).map(Int::toBigInteger).toMutableList()
        val interpreter = IntCode(list)
        interpreter.compute()
        assertIterableEquals(listOf(2,4,4,5,99,9801).map(Int::toBigInteger), interpreter.getModifiedProgram())
    }

    @Test
    fun `Simple Example with 2 instructions`() {
        val list = listOf(1,1,1,4,99,5,6,0,99).map(Int::toBigInteger).toMutableList()
        val interpreter = IntCode(list)
        interpreter.compute()
        assertIterableEquals(listOf(30,1,1,4,2,5,6,0,99).map(Int::toBigInteger), interpreter.getModifiedProgram())
    }
}
