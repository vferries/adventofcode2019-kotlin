package advent

import java.io.File

fun main() {
    val combinations = permute((0..5).toList())
    val program = File("src/main/resources/day7.txt").readText().split(",").map(String::toInt)
//    println(combinations.map { calculateThrusterSignal(program, it) }.max())

    val combinations2 = permute((5..10).toList())
    val max = combinations2.map { calculateThrusterSignal(program, it) }.max()
    println("Max is $max")

}

fun calculateThrusterSignal(program: List<Int>, phaseSetting: List<Int>): Int {
    try {
        val EtoA = mutableListOf<Int>()
        EtoA.add(phaseSetting[0])
        EtoA.add(0)
        val AtoB = mutableListOf<Int>()
        AtoB.add(phaseSetting[1])
        val BtoC = mutableListOf<Int>()
        BtoC.add(phaseSetting[2])
        val CtoD = mutableListOf<Int>()
        CtoD.add(phaseSetting[3])
        val DtoE = mutableListOf<Int>()
        DtoE.add(phaseSetting[4])
        val A = IntCode(program.toMutableList(), EtoA, AtoB)
        val B = IntCode(program.toMutableList(), AtoB, BtoC)
        val C = IntCode(program.toMutableList(), BtoC, CtoD)
        val D = IntCode(program.toMutableList(), CtoD, DtoE)
        val E = IntCode(program.toMutableList(), DtoE, EtoA)

        var loopCount = 1
        while (!E.finished) {
            A.processToNextOutput()
            B.processToNextOutput()
            C.processToNextOutput()
            D.processToNextOutput()
            E.processToNextOutput()
            println("Loop ${loopCount++}")
        }
        return E.outputs.last()
    } catch (e: Exception) {
        println(e)
        return 0
    }
}

fun <Int> permute(list: List<Int>): List<List<Int>> {
    if (list.size == 1) return listOf(list)
    val perms = mutableListOf<List<Int>>()
    val sub = list[0]
    for (perm in permute(list.drop(1)))
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, sub)
            perms.add(newPerm)
        }
    return perms
}