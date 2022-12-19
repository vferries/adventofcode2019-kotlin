package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Tests {
    @Test
    fun `with input 12345678`() {
        assertEquals("23845678", Day16.part1("12345678"))
    }
    @Test
    fun `with real input part 1`() {
        assertEquals("44098263", Day16.part1(loadFile("day16.txt").readText()))
    }
    @Test
    fun `with real input part 2`() {
        // > 5970221
        assertEquals("12482168", Day16.part2(loadFile("day16.txt").readText()))
    }
}