package advent

import java.util.*


fun shortestPathToCollectKeys(input: List<String>): Int {
    val initPos = findInitPos(input)
    val mapWithoutPos = input.map { line -> line.replace("@", ".") }

    val map = mutableMapOf<Pos, Char>()
    for (row in mapWithoutPos.indices) {
        for (col in mapWithoutPos[row].indices) {
            val value = mapWithoutPos[row][col]
            if (value != '#') {
                map[Pos(col, row)] = value
            }
        }
    }

    val initState = CurrentState(initPos, setOf(), 0)
    val nextStates = PriorityQueue<CurrentState>(compareBy { it.steps })
    nextStates.add(initState)
    val mapBestScores = mutableMapOf<CurrentState, Int>()
    while (true) {
        val nextState = nextStates.poll()
        //println("nextstate $nextState")
        if (mapBestScores.containsKey(nextState) && mapBestScores.getValue(nextState) <= nextState.steps) {
            continue
        } else {
            mapBestScores[nextState] = nextState.steps
        }
        if (finishedState(map, nextState.keys)) return nextState.steps
        val nextMoves = shortestPathsToKeys(map, nextState)
        nextStates.addAll(nextMoves.map { entry ->
            val pos = entry.key
            val (c, steps) = entry.value
            CurrentState(pos, nextState.keys + c, nextState.steps + steps)
        })
        //println(nextStates)
    }
}

fun part2(input: List<String>): Int {
    val initPos = findInitPos(input)
    val toRemove = initPos.neighbors() + initPos
    val mapWithoutPos = input.map { line -> line.replace("@", ".") }

    val map = mutableMapOf<Pos, Char>()
    for (row in mapWithoutPos.indices) {
        for (col in mapWithoutPos[row].indices) {
            val value = mapWithoutPos[row][col]
            if (value != '#' && !toRemove.contains(Pos(col, row))) {
                map[Pos(col, row)] = value
            }
        }
    }

    val initPositions = listOf(Pos(-1, -1), Pos(-1, 1), Pos(1, -1), Pos(1, 1)).map { initPos + it }
    val initState = QuadriState(initPositions, setOf(), 0)
    val nextStates = PriorityQueue<QuadriState>(compareBy { it.steps })
    nextStates.add(initState)
    val mapBestScores = mutableMapOf<QuadriState, Int>()
    while (true) {
        val nextState = nextStates.poll()
        if (mapBestScores.containsKey(nextState) && mapBestScores.getValue(nextState) <= nextState.steps) {
            continue
        } else {
            mapBestScores[nextState] = nextState.steps
        }
        if (finishedState(map, nextState.keys)) return nextState.steps

        val nextMoves: Map<List<Pos>, Pair<Char, Int>> = (0..3).map { i ->
            shortestPathsToKeys(
                map,
                CurrentState(nextState.positions[i], nextState.keys, nextState.steps)
            ).map { entry ->
                val positions = ArrayList(nextState.positions)
                positions[i] = entry.key
                positions to entry.value
            }
        }.fold(mapOf()) { m1, m2 -> m1.plus(m2) }

        nextStates.addAll(nextMoves.map { entry ->
            val positions = entry.key
            val (c, steps) = entry.value
            QuadriState(positions, nextState.keys + c, nextState.steps + steps)
        })
        //println(nextStates)
    }
}

private operator fun Pos.plus(other: Pos): Pos = Pos(first + other.first, second + other.second)

fun finishedState(map: Map<Pos, Char>, keys: Set<Char>): Boolean {
    return map.values.none { c -> c.isLowerCase() && !keys.contains(c) }
}

data class CurrentState(val currentPos: Pos, val keys: Set<Char>, val steps: Int) {
    override fun equals(other: Any?): Boolean =
        other is CurrentState && currentPos == other.currentPos && keys.size == other.keys.size && keys.all {
            other.keys.contains(
                it
            )
        }

    override fun hashCode(): Int {
        var result = currentPos.hashCode()
        result = 31 * result + keys.hashCode()
        return result
    }
}

data class QuadriState(val positions: List<Pos>, val keys: Set<Char>, val steps: Int) {
    override fun equals(other: Any?): Boolean =
        other is QuadriState && positions == other.positions && keys.size == other.keys.size && keys.all {
            other.keys.contains(
                it
            )
        }

    override fun hashCode(): Int {
        var result = positions.hashCode()
        result = 31 * result + keys.hashCode()
        return result
    }
}

fun shortestPathsToKeys(map: Map<Pos, Char>, currentState: CurrentState): MutableMap<Pos, Pair<Char, Int>> {
    val visited = mutableSetOf<Pos>()
    val toVisit = mutableListOf(currentState.currentPos to 0)
    val mapShortestPaths = mutableMapOf<Pos, Pair<Char, Int>>()
    while (toVisit.size > 0) {
        val (pos, steps) = toVisit.removeAt(0)
        if (visited.contains(pos)) continue
        visited.add(pos)
        val value = map.getValue(pos)
        if (value.isLowerCase() && !currentState.keys.contains(value)) {
            mapShortestPaths[pos] = value to steps
        }
        toVisit.addAll(pos.neighbors().filter(validPosition(map, currentState)).map { it to steps + 1 })
    }
    //println("$currentState $mapShortestPaths")
    return mapShortestPaths
}

private fun validPosition(map: Map<Pos, Char>, currentState: CurrentState): (Pos) -> Boolean {
    return { (x, y) ->
        map.containsKey(Pos(x, y)) && !(map.getValue(Pos(x, y))
            .isUpperCase() && !currentState.keys.contains(map.getValue(Pos(x, y)).toLowerCase()))
    }
}

fun Pos.neighbors(): List<Pos> {
    return listOf(
        Pos(this.first - 1, this.second),
        Pos(this.first + 1, this.second),
        Pos(this.first, this.second + 1),
        Pos(this.first, this.second - 1)
    )
}

private fun findInitPos(map: List<String>): Pos {
    var y = 0
    map.mapIndexed { index, line -> if (line.contains('@')) y = index }
    val x = map[y].indexOf('@')
    return Pos(x, y)
}

fun main() {
    val map = loadFile("day18.txt").readLines()
    println(shortestPathToCollectKeys(map))
}

// < 11052