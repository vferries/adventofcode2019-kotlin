package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day21Tests {
    @Nested
    inner class Part1 {
        @Test
        fun `Should find the correct hull damages`() {
            assertEquals("19354437", Day21.part1())
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun `Should find the correct hull damages`() {
            assertEquals("1145373084", Day21.part2())
        }
    }
}
