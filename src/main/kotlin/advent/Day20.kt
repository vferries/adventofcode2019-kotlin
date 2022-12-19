package advent

import java.lang.IllegalStateException
import java.util.*

object Day20 {
    fun part1(maze: List<String>): Int {
        val portals = portals(maze)
        val validPositions = maze.indices.flatMap { row -> maze[row].indices.map { col -> Pos(col, row) } }
            .filter { p -> maze[p.second][p.first] == '.' }
        val initPos = portals["AA"]!![0].first
        val endPos = portals["ZZ"]!![0].first
        val portalsMap = portals
            .filter { entry -> entry.value.size == 2 }
            .flatMap { entry ->
                val (p1, p2) = entry.value
                listOf(p1.first to p2.first, p2.first to p1.first)
            }.toMap()
        val shortestPaths = mutableMapOf<Pos, MutableMap<Pos, Int>>()
        for (pos in portalsMap.keys + initPos) {
            val toVisit = PriorityQueue<Pair<Int, Pos>>(compareBy { it.first })
            val visited = mutableSetOf<Pos>()
            val posMap = mutableMapOf<Pos, Int>()
            toVisit.add(0 to pos)
            while (toVisit.isNotEmpty()) {
                val (steps, p) = toVisit.poll()
                if (visited.contains(p)) {
                    continue
                }
                visited.add(p)
                if (p != pos && portalsMap.keys.contains(p)) {
                    posMap[portalsMap[p]!!] = steps + 1
                } else if (p == endPos) {
                    posMap[p] = steps
                }
                toVisit.addAll(p.neighbors().filter { validPositions.contains(it) }.map { steps + 1 to it })
            }
            shortestPaths[pos] = posMap
        }
        println(shortestPaths)

        val visited = mutableSetOf<Pos>()
        val toVisit = PriorityQueue<Pair<Int, Pos>>(compareBy { it.first })
        toVisit.add(0 to initPos)
        while (toVisit.isNotEmpty()) {
            val (steps, p) = toVisit.poll()
            println("$steps $p ${toVisit.size}")
            if (visited.contains(p)) {
                continue
            }
            if (p == endPos) {
                return steps
            }
            visited.add(p)
            toVisit.addAll(shortestPaths[p]!!.map { it.value + steps to it.key })
        }
        throw IllegalStateException("No exit found")
    }

    fun part2(maze: List<String>): Int {
        val portals = portals(maze)
        val validPositions = maze.indices.flatMap { row -> maze[row].indices.map { col -> Pos(col, row) } }
            .filter { p -> maze[p.second][p.first] == '.' }
        val initPos = portals["AA"]!![0].first
        val endPos = portals["ZZ"]!![0].first
        val portalsMap = portals
            .filter { entry -> entry.value.size == 2 }
            .flatMap { entry ->
                val (p1, p2) = entry.value
                listOf(p1.first to (p2.first to p1.second), p2.first to (p1.first to p2.second))
            }.toMap()
        val shortestPaths = mutableMapOf<Pos, MutableMap<Pos, Pair<Int, Int>>>()
        for (pos in portalsMap.keys + initPos) {
            val toVisit = PriorityQueue<Pair<Int, Pos>>(compareBy { it.first })
            val visited = mutableSetOf<Pos>()
            val posMap = mutableMapOf<Pos, Pair<Int, Int>>()
            toVisit.add(0 to pos)
            while (toVisit.isNotEmpty()) {
                val (steps, p) = toVisit.poll()
                if (visited.contains(p)) {
                    continue
                }
                visited.add(p)
                if (portalsMap.keys.contains(p)) {
                    val levelDiff = when (portalsMap[p]!!.second) {
                        Edge.INNER -> 1
                        Edge.OUTER -> -1
                    }
                    posMap[portalsMap[p]!!.first] = steps + 1 to levelDiff
                } else if (p == endPos) {
                    posMap[p] = steps to 0
                }
                toVisit.addAll(p.neighbors().filter { validPositions.contains(it) }.map { steps + 1 to it })
            }
            shortestPaths[pos] = posMap
        }
        println(shortestPaths)

        val visited = mutableSetOf<Pair<Pos, Int>>()
        val toVisit = PriorityQueue<Triple<Int, Pos, Int>>(compareBy<Triple<Int, Pos, Int>> { it.first }.thenBy { it.third })
        toVisit.add(Triple(0, initPos, 0))
        while (toVisit.isNotEmpty()) {
            val (steps, p, level) = toVisit.poll()
            if (visited.contains(p to level)) {
                continue
            }
            if (level < 0) {
                continue
            }
            if (p == endPos && level == 0) {
                return steps
            }
            visited.add(p to level)
            toVisit.addAll((shortestPaths[p]?: mutableMapOf()).map { Triple(it.value.first + steps, it.key, level + it.value.second) })
        }
        throw IllegalStateException("No exit found")
    }

    private fun portals(maze: List<String>): MutableMap<String, List<Pair<Pos, Edge>>> {
        val mazeLength = maze.map { it.length }.max()!!
        val innerFirstRow = maze.drop(2).dropLast(2).indexOfFirst { line -> line.drop(2).dropLast(2).contains(" ") } + 2
        val innerLastRow = maze.drop(2).dropLast(2).indexOfLast { line -> line.drop(2).dropLast(2).contains(" ") } + 2
        val contentRange = (2..maze.size - 3)
        val innerFirstCol = contentRange.first {
            maze.drop(2).dropLast(2).map { line -> line.getOrElse(it) { '#' } }.joinToString("").contains(' ')
        }
        val innerLastCol = contentRange.last {
            maze.drop(2).dropLast(2).map { line -> line.getOrElse(it) { '#' } }.joinToString("").contains(' ')
        }
        println("$innerFirstRow $innerLastRow $innerFirstCol $innerLastCol")
        val portals = mutableMapOf<String, List<Pair<Pos, Edge>>>()
        parseDoorsRow(maze, portals, 0, 2, Edge.OUTER)
        parseDoorsRow(maze, portals, maze.size - 2, maze.size - 3, Edge.OUTER)
        parseDoorsRow(maze, portals, innerFirstRow, innerFirstRow - 1, Edge.INNER)
        parseDoorsRow(maze, portals, innerLastRow - 1, innerLastRow + 1, Edge.INNER)
        parseDoorsCol(maze, portals, 0, 2, Edge.OUTER)
        parseDoorsCol(maze, portals, mazeLength - 2, mazeLength - 3, Edge.OUTER)
        parseDoorsCol(maze, portals, innerFirstCol, innerFirstCol - 1, Edge.INNER)
        parseDoorsCol(maze, portals, innerLastCol - 1, innerLastCol + 1, Edge.INNER)
        return portals
    }

    private fun parseDoorsRow(
        maze: List<String>,
        portals: MutableMap<String, List<Pair<Pos, Edge>>>,
        firstRow: Int,
        cellRow: Int,
        edge: Edge
    ) {
        for (i in maze[firstRow].indices) {
            if (maze[firstRow][i].isUpperCase() && maze[firstRow + 1][i].isUpperCase()) {
                val label = ("" + maze[firstRow][i] + maze[firstRow + 1][i])
                portals[label] = portals.getOrDefault(label, listOf()) + ((i to cellRow) to edge)
            }
        }
    }

    private fun parseDoorsCol(
        maze: List<String>,
        portals: MutableMap<String, List<Pair<Pos, Edge>>>,
        firstCol: Int,
        cellCol: Int,
        edge: Edge
    ) {
        for (i in maze.indices) {
            if (maze[i].length > firstCol + 1 && maze[i][firstCol].isUpperCase() && maze[i][firstCol + 1].isUpperCase()) {
                val label = ("" + maze[i][firstCol] + maze[i][firstCol + 1])
                portals[label] = portals.getOrDefault(label, listOf()) + ((cellCol to i) to edge)
            }
        }
    }

    enum class Edge {
        INNER, OUTER
    }
}

