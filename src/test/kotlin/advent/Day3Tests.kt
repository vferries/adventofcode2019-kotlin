package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

class Day3Tests {
    private val day3 = Day3()

    @Test
    fun `Part 1 example 1`() {
        val paths = listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83")
        assertEquals(159, day3.manhattanIntersectingDistance(paths))
    }

    @Test
    fun `Part 1 example 2`() {
        val paths = listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
        assertEquals(135, day3.manhattanIntersectingDistance(paths))
    }

    @Test
    fun `Part 2 example 1`() {
        val paths = listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83")
        assertEquals(610, day3.steps(paths))
    }

    @Test
    fun `Part 2 example 2`() {
        val paths = listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
        assertEquals(410, day3.steps(paths))
    }
}
