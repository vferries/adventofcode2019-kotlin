package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day23Tests {
    @Nested
    inner class Part1 {
        @Test
        fun `Should find the correct value for packet sent at address 255`() {
            assertEquals(16660, Day23.part1())
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun `Should find the correct value for NAT packet sent twice`() {
            assertEquals(0, Day23.part2())
        }
    }
}
