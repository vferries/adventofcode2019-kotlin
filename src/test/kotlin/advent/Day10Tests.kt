package advent

import org.junit.jupiter.api.Test

class Day10Tests {
    @Test
    fun `should find the correct station with simple example`() {
        val map = ".#..#\n.....\n#####\n....#\n...##".lines()
        val asteroidPositions = (0 until map.size)
                .flatMap { x -> (0 until map[0].length).map { x to it } }
                .filter { (x, y) -> map[y][x] == '#' }
        println(asteroidPositions)
    }
}