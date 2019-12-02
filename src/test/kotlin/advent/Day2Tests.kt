package advent

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

class Day2Tests {
    private val day2 = Day2()

    @Test
    fun `Simple Example Full`() {
        val list = mutableListOf(1,9,10,3,2,3,11,0,99,30,40,50)
        assertIterableEquals(listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50), day2.processCode(list))
    }

    @Test
    fun `Simple Example (1 + 1 = 2)`() {
        val list = mutableListOf(1,0,0,0,99)
        assertIterableEquals(listOf(2,0,0,0,99), day2.processCode(list))
    }

    @Test
    fun `Simple Example (3 * 2 = 6)`() {
        val list = mutableListOf(2,3,0,3,99)
        assertIterableEquals(listOf(2,3,0,6,99), day2.processCode(list))
    }

    @Test
    fun `Simple Example (99 * 99 = 9801`() {
        val list = mutableListOf(2,4,4,5,99,0)
        assertIterableEquals(listOf(2,4,4,5,99,9801), day2.processCode(list))
    }

    @Test
    fun `Simple Example with 2 instructions`() {
        val list = mutableListOf(1,1,1,4,99,5,6,0,99)
        assertIterableEquals(listOf(30,1,1,4,2,5,6,0,99), day2.processCode(list))
    }
}
