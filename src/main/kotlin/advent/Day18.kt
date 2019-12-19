package advent


fun shortestPathToCollectKeys(map: List<String>): Int {
    val initPos = findInitPos(map)

    val mapWithoutPos = map.map { line -> line.replace("@", ".") }

    val initState = CurrentState(initPos, listOf(initPos), setOf('.'), 0)
    val nextStates = mutableListOf<CurrentState>()
    nextStates.add(initState)

    while (true) {
        val nextState = nextStates.first()
        nextStates.removeAt(0)

        if (finishedState(mapWithoutPos, nextState.keys)) return nextState.steps


        //TODO Calculate and only add shortest paths to new keys
        nextStates.addAll(nextMoves(mapWithoutPos, nextState))
    }
}

fun finishedState(map: List<String>, keys: Set<Char>): Boolean {
    return map.joinToString("").none { c -> c.isLowerCase() && !keys.contains(c) }
}

data class CurrentState(val currentPos: Pos, val visitedPos: List<Pos>, val keys: Set<Char>, val steps: Int)

fun nextMoves(map: List<String>, currentState: CurrentState): List<CurrentState> =
        currentState.currentPos.neighbors()
                .filter(validPosition(map, currentState))
                .filter { pos -> !currentState.visitedPos.contains(pos) }
                .map { pos ->
                    val current = map[pos.second][pos.first]
                    // Reset visited if new door or new key only
                    val newVisited = (if (currentState.keys.contains(current))
                        currentState.visitedPos
                    else
                        listOf()) + pos
                    CurrentState(pos, newVisited, currentState.keys + current, currentState.steps + 1)
                }

private fun validPosition(map: List<String>, currentState: CurrentState): (Pos) -> Boolean {
    return { (x, y) ->
        val value = map[y][x]
        value != '#' && !(value.isUpperCase() && !currentState.keys.contains(value.toLowerCase()))
    }
}

private fun Pos.neighbors(): List<Pos> {
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