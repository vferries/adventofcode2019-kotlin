package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day24Tests {
    @Nested
    inner class Part1 {
        // Didn't find where I put the code
    }

    @Nested
    inner class Part2 {
        @Test
        fun `Should fold space and time correctly on sample`() {
            assertEquals(99, Day24.part2(loadFile("day24_sample.txt").readLines(), 10))
        }

        @Test
        fun `Should fold space and time correctly`() {
            assertEquals(1896, Day24.part2(loadFile("day24.txt").readLines(), 200))
        }
    }
}
