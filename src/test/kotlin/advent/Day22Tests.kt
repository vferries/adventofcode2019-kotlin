package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day22Tests {
    @Nested
    inner class Part1 {
        @Test
        fun `Should find the correct card`() {
            assertEquals(4684, Day22(loadFile("day22.txt").readLines()).part1())
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun `Should find the correct card`() {
            assertEquals(452290953297, Day22(loadFile("day22.txt").readLines()).part2())
        }
    }
}
