package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day20Tests {
    @Nested
    inner class Part1 {
        @Test
        fun `Should find the shortest path for sample 1`() {
            assertEquals(23, Day20.part1(loadFile("day20_sample1.txt").readLines()))
        }

        @Test
        fun `Should find the shortest path for sample 2`() {
            assertEquals(58, Day20.part1(loadFile("day20_sample2.txt").readLines()))
        }

        @Test
        fun `Should find the shortest path for input`() {
            assertEquals(684, Day20.part1(loadFile("day20.txt").readLines()))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun `Should find the shortest path for sample 3`() {
            assertEquals(396, Day20.part2(loadFile("day20_sample3.txt").readLines()))
        }

        @Test
        fun `Should find the shortest path for input`() {
            assertEquals(7758, Day20.part2(loadFile("day20.txt").readLines()))
        }
    }
}
