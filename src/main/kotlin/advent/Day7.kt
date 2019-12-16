package advent

import java.math.BigInteger

fun main() {
    val combinations = permute((0..5).toList())
    val program = loadFile("day7.txt").readText().split(",").map(String::toInt)
//    println(combinations.map { calculateThrusterSignal(program, it) }.max())

    val combinations2 = permute((5..10).toList())
    val max = combinations2.map { calculateThrusterSignal(program, it.map(Int::toBigInteger)) }.max()
    println("Max is $max")

}

fun calculateThrusterSignal(program: List<Int>, phaseSetting: List<BigInteger>): BigInteger {
    try {
        val EtoA = mutableListOf<BigInteger>()
        EtoA.add(phaseSetting[0])
        EtoA.add(BigInteger.ZERO)
        val AtoB = mutableListOf<BigInteger>()
        AtoB.add(phaseSetting[1])
        val BtoC = mutableListOf<BigInteger>()
        BtoC.add(phaseSetting[2])
        val CtoD = mutableListOf<BigInteger>()
        CtoD.add(phaseSetting[3])
        val DtoE = mutableListOf<BigInteger>()
        DtoE.add(phaseSetting[4])
        val A = IntCode(program.map(Int::toBigInteger).toMutableList(), EtoA, AtoB)
        val B = IntCode(program.map(Int::toBigInteger).toMutableList(), AtoB, BtoC)
        val C = IntCode(program.map(Int::toBigInteger).toMutableList(), BtoC, CtoD)
        val D = IntCode(program.map(Int::toBigInteger).toMutableList(), CtoD, DtoE)
        val E = IntCode(program.map(Int::toBigInteger).toMutableList(), DtoE, EtoA)

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
        return BigInteger.ZERO
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