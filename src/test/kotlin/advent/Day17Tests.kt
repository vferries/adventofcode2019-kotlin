package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17Tests {
    @Test
    fun `Calculate alignment parameters`() {
val map = """..#..........
..#..........
#######...###
#.#...#...#.#
#############
..#...#...#..
..#####...^..""".lines()
        assertEquals(76, calculateAlignmentParameters(map))
    }
}
