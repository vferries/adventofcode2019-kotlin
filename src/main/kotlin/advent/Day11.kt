package advent

fun main() {
    val program = loadFile("day11.txt").readText().split(",").map(String::toBigInteger)
    val robot = IntRobot(IntCode(program.toMutableList(), mutableListOf()), mutableMapOf())
    robot.run()
    println(robot.painting.keys.size)

    val map = mutableMapOf((0 to 0) to 1)
    val robot2 = IntRobot(IntCode(program.toMutableList(), mutableListOf()), map)
    robot2.run()
    (60 downTo -60).forEach { i ->
        (-60..60).forEach { j ->
            print(if (map[j to i] == 1) "X" else " ")
        }
        println()
    }
}