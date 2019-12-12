package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Tests {
    @Test
    fun `should find the correct station with simple example`() {
        val map =
                """......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####""".lines()
        val result = bestSlotForMap(map)
        val (x, y) = result.first
        assertEquals(33, result.second.size)
        assertEquals(5, x)
        assertEquals(8, y)
    }

    @Test
    fun `should find the correct station with simple example 2`() {
        val map =
                """#.#...#.#.
.###....#.
.#....#...
##.#.#.#.#
....#.#.#.
.##..###.#
..#...##..
..##....##
......#...
.####.###.""".lines()
        val result = bestSlotForMap(map)
        val (x, y) = result.first
        assertEquals(35, result.second.size)
        assertEquals(1, x)
        assertEquals(2, y)
    }

    @Test
    fun `should find the correct station with simple example 3`() {
        val map =
""".#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#..""".lines()
        val result = bestSlotForMap(map)
        val (x, y) = result.first
        assertEquals(41, result.second.size)
        assertEquals(6, x)
        assertEquals(3, y)
    }

    @Test
    fun `should find the correct station with simple example 4`() {
        val map =
                """.#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##""".lines()
        val result = bestSlotForMap(map)
        val (x, y) = result.first
        assertEquals(210, result.second.size)
        assertEquals(11, x)
        assertEquals(13, y)
    }

}