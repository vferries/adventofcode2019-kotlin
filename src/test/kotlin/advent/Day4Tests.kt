package advent

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Tests {
    private val day4 = Day4()

    @Test
    fun `Part 1 - Simple matching example`() {
        assertTrue(day4.meetCriteria(111111))
    }

    @Test
    fun `Part 1 - Simple erroneous example 1`() {
        assertFalse(day4.meetCriteria(223450))
    }

    @Test
    fun `Part 1 - Simple erroneous example 2`() {
        assertFalse(day4.meetCriteria(123789))
    }

    @Test
    fun `Part 2 - Matching sample`() {
        assertTrue(day4.meetCriteria2(112233))
    }

    @Test
    fun `Part 2 - Larger group than 2`() {
        assertFalse(day4.meetCriteria2(123444))
    }

    @Test
    fun `Part 2 - Matching sample 2`() {
        assertTrue(day4.meetCriteria2(111122))
    }
}
