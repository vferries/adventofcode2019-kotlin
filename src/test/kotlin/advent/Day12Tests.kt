package advent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Tests {
    @Test
    fun `should find the correct total energy with simple example`() {
        val planets = listOf(
                Planet(Pos3D(-1, 0, 2)),
                Planet(Pos3D(2, -10, -7)),
                Planet(Pos3D(4, -8, 8)),
                Planet(Pos3D(3, 5, -1)))
        val result = computeTotalEnergy(planets, 10)
        assertEquals(179, result)
    }

    @Test
    fun `should find the correct total energy with second example`() {
        val planets = listOf(
                Planet(Pos3D(-8, -10, 0)),
                Planet(Pos3D(5, 5, 10)),
                Planet(Pos3D(2, -7, 3)),
                Planet(Pos3D(9, -8, -3)))
        val result = computeTotalEnergy(planets, 100)
        assertEquals(1940, result)
    }

    @Test
    fun `should go back to origin with simple example`() {
        val planets = listOf(
                Planet(Pos3D(-1, 0, 2)),
                Planet(Pos3D(2, -10, -7)),
                Planet(Pos3D(4, -8, 8)),
                Planet(Pos3D(3, 5, -1)))
        val result = repeatingState(planets)
        assertEquals(2772, result)
    }

    @Test
    fun `should go back to origin with second example`() {
        val planets = listOf(
                Planet(Pos3D(-8, -10, 0)),
                Planet(Pos3D(5, 5, 10)),
                Planet(Pos3D(2, -7, 3)),
                Planet(Pos3D(9, -8, -3)))
        val result = repeatingState(planets)
        assertEquals(4686774924, result)
    }
}