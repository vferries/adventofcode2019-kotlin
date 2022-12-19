package advent

import java.lang.IllegalArgumentException

object Day24 {
    fun part2(map: List<String>, iterations: Int): Int {
        var currentState = map.asSequence().mapIndexed { row, line -> line.indices.map { col -> Pos(col, row) } }.flatten()
            .filter { map[it.second][it.first] == '#' }.map { p -> Tile(p, 0) }.toSet()
        repeat(iterations) {
            val nextCandidates = (currentState + currentState.flatMap(Tile::neighbors)).toSet()
            val nextState = nextCandidates.filter { tile ->
                val aliveNeighbors = tile.neighbors().count { currentState.contains(it) }
                aliveNeighbors == 1 || (aliveNeighbors == 2 && !currentState.contains(tile))  }.toSet()
            currentState = nextState
        }
        return currentState.size
    }

    data class Tile(val pos: Pos, val level: Int) {
        fun neighbors(): Set<Tile> =
            when (pos) {
                Pos(0, 0) -> setOf(
                    Tile(Pos(0, 1), level),
                    Tile(Pos(1, 0), level),
                    Tile(Pos(2, 1), level + 1),
                    Tile(Pos(1, 2), level + 1)
                )

                Pos(0, 1) -> setOf(
                    Tile(Pos(0, 0), level),
                    Tile(Pos(0, 2), level),
                    Tile(Pos(1, 1), level),
                    Tile(Pos(1, 2), level + 1)
                )

                Pos(0, 2) -> setOf(
                    Tile(Pos(0, 1), level),
                    Tile(Pos(0, 3), level),
                    Tile(Pos(1, 2), level),
                    Tile(Pos(1, 2), level + 1)
                )

                Pos(0, 3) -> setOf(
                    Tile(Pos(0, 2), level),
                    Tile(Pos(0, 4), level),
                    Tile(Pos(1, 3), level),
                    Tile(Pos(1, 2), level + 1)
                )

                Pos(0, 4) -> setOf(
                    Tile(Pos(0, 3), level),
                    Tile(Pos(1, 4), level),
                    Tile(Pos(2, 3), level + 1),
                    Tile(Pos(1, 2), level + 1)
                )

                Pos(1, 0) -> setOf(
                    Tile(Pos(1, 1), level),
                    Tile(Pos(0, 0), level),
                    Tile(Pos(2, 0), level),
                    Tile(Pos(2, 1), level + 1)
                )

                Pos(1, 1) -> setOf(
                    Tile(Pos(1, 0), level),
                    Tile(Pos(1, 2), level),
                    Tile(Pos(0, 1), level),
                    Tile(Pos(2, 1), level)
                )

                Pos(1, 2) -> setOf(
                    Tile(Pos(1, 1), level),
                    Tile(Pos(1, 3), level),
                    Tile(Pos(0, 2), level),
                    Tile(Pos(0, 0), level - 1),
                    Tile(Pos(0, 1), level - 1),
                    Tile(Pos(0, 2), level - 1),
                    Tile(Pos(0, 3), level - 1),
                    Tile(Pos(0, 4), level - 1)
                )

                Pos(1, 3) -> setOf(
                    Tile(Pos(1, 2), level),
                    Tile(Pos(1, 4), level),
                    Tile(Pos(0, 3), level),
                    Tile(Pos(2, 3), level)
                )

                Pos(1, 4) -> setOf(
                    Tile(Pos(0, 4), level),
                    Tile(Pos(2, 4), level),
                    Tile(Pos(1, 3), level),
                    Tile(Pos(2, 3), level + 1)
                )

                Pos(2, 0) -> setOf(
                    Tile(Pos(2, 1), level),
                    Tile(Pos(1, 0), level),
                    Tile(Pos(3, 0), level),
                    Tile(Pos(2, 1), level + 1)
                )

                Pos(2, 1) -> setOf(
                    Tile(Pos(2, 0), level),
                    Tile(Pos(3, 1), level),
                    Tile(Pos(1, 1), level),
                    Tile(Pos(0, 0), level - 1),
                    Tile(Pos(1, 0), level - 1),
                    Tile(Pos(2, 0), level - 1),
                    Tile(Pos(3, 0), level - 1),
                    Tile(Pos(4, 0), level - 1)
                )

                Pos(2, 3) -> setOf(
                    Tile(Pos(2, 4), level),
                    Tile(Pos(1, 3), level),
                    Tile(Pos(3, 3), level),
                    Tile(Pos(0, 4), level - 1),
                    Tile(Pos(1, 4), level - 1),
                    Tile(Pos(2, 4), level - 1),
                    Tile(Pos(3, 4), level - 1),
                    Tile(Pos(4, 4), level - 1)
                )

                Pos(2, 4) -> setOf(
                    Tile(Pos(2, 3), level),
                    Tile(Pos(1, 4), level),
                    Tile(Pos(3, 4), level),
                    Tile(Pos(2, 3), level + 1)
                )

                Pos(3, 0) -> setOf(
                    Tile(Pos(3, 1), level),
                    Tile(Pos(2, 0), level),
                    Tile(Pos(4, 0), level),
                    Tile(Pos(2, 1), level + 1)
                )

                Pos(3, 1) -> setOf(
                    Tile(Pos(3, 0), level),
                    Tile(Pos(3, 2), level),
                    Tile(Pos(2, 1), level),
                    Tile(Pos(4, 1), level)
                )

                Pos(3, 2) -> setOf(
                    Tile(Pos(3, 1), level),
                    Tile(Pos(3, 3), level),
                    Tile(Pos(4, 2), level),
                    Tile(Pos(4, 0), level - 1),
                    Tile(Pos(4, 1), level - 1),
                    Tile(Pos(4, 2), level - 1),
                    Tile(Pos(4, 3), level - 1),
                    Tile(Pos(4, 4), level - 1)
                )

                Pos(3, 3) -> setOf(
                    Tile(Pos(3, 2), level),
                    Tile(Pos(3, 4), level),
                    Tile(Pos(2, 3), level),
                    Tile(Pos(4, 3), level)
                )

                Pos(3, 4) -> setOf(
                    Tile(Pos(2, 4), level),
                    Tile(Pos(4, 4), level),
                    Tile(Pos(3, 3), level),
                    Tile(Pos(2, 3), level + 1)
                )

                Pos(4, 0) -> setOf(
                    Tile(Pos(3, 0), level),
                    Tile(Pos(4, 1), level),
                    Tile(Pos(2, 1), level + 1),
                    Tile(Pos(3, 2), level + 1)
                )

                Pos(4, 1) -> setOf(
                    Tile(Pos(4, 0), level),
                    Tile(Pos(4, 2), level),
                    Tile(Pos(3, 1), level),
                    Tile(Pos(3, 2), level + 1)
                )

                Pos(4, 2) -> setOf(
                    Tile(Pos(4, 1), level),
                    Tile(Pos(4, 3), level),
                    Tile(Pos(3, 2), level),
                    Tile(Pos(3, 2), level + 1)
                )

                Pos(4, 3) -> setOf(
                    Tile(Pos(4, 2), level),
                    Tile(Pos(4, 4), level),
                    Tile(Pos(3, 3), level),
                    Tile(Pos(3, 2), level + 1)
                )

                Pos(4, 4) -> setOf(
                    Tile(Pos(3, 4), level),
                    Tile(Pos(4, 3), level),
                    Tile(Pos(2, 3), level + 1),
                    Tile(Pos(3, 2), level + 1)
                )

                else -> throw IllegalArgumentException("Pos $pos is not a valid position")
            }
    }
}