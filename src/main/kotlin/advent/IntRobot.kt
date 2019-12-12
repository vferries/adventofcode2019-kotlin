package advent

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}

class IntRobot(private val intCode: IntCode, val map: MutableMap<Pos, Int>) {
    val painting: MutableMap<Pos, Int> = map.withDefault { 0 }
    private var direction = Direction.UP
    private var currentPos = 0 to 0

    fun run() {
        while (!intCode.finished) {
            val currentValue = painting.getValue(currentPos)
            intCode.inputs.add(currentValue.toBigInteger())
            intCode.processToNextOutput()
            val colorToPaint = intCode.outputs.last().toInt()
            intCode.processToNextOutput()
            painting[currentPos] = colorToPaint
            val newDirection = intCode.outputs.last().toInt()
            turn(newDirection)
            move()
        }
    }

    private fun turn(newDirection: Int) {
        val shift = if (newDirection == 0) -1 else +1
        val directions = Direction.values()
        direction = directions[(directions.size + direction.ordinal + shift) % directions.size]
    }

    private fun move() {
        currentPos = when (direction) {
            Direction.UP -> currentPos.first to currentPos.second + 1
            Direction.DOWN -> currentPos.first to currentPos.second - 1
            Direction.LEFT -> currentPos.first - 1 to currentPos.second
            Direction.RIGHT -> currentPos.first + 1 to currentPos.second
        }
    }
}