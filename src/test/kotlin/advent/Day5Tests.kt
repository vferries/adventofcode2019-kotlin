package advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day5Tests {
    private val intCode = IntCode()

    @Test
    fun `Part 1 - Simple matching example`() {
        assertIterableEquals(listOf(1002,4,3,4,99), intCode.compute(mutableListOf(1002,4,3,4,33)))
    }
}
