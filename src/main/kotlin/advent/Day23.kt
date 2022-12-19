package advent

import java.math.BigInteger
import java.util.*
import kotlin.concurrent.thread

object Day23 {
    fun part1(): Int {
        val program = loadFile("day23.txt").readText().split(',').map { it.toBigInteger() }.toMutableList()
        val computers = (0 until 50).map { IntCode(program, mutableListOf(it.toBigInteger())) }
        computers.forEach { thread { it.compute() } }
        while (true) {
            for (nic in computers) {
                if (nic.outputs.size >= 3) {
                    val (i, x, y) = nic.outputs.take(3)
                    println("$i $x $y")
                    if (i.toInt() == 255) {
                        return y.toInt()
                    } else {
                        computers[i.toInt()].inputs.addAll(listOf(x, y))
                    }

                    repeat(3) {
                        nic.outputs.removeAt(0)
                    }
                }
            }
        }
    }

    fun part2(): Int {
        val program = loadFile("day23.txt").readText().split(',').map { it.toBigInteger() }.toMutableList()
        val computers = (0 until 50).map {
            IntCode(
                program, mutableListOf(it.toBigInteger()), outputs = Collections.synchronizedList(
                    mutableListOf()
                )
            )
        }
        computers.forEach { thread { it.compute() } }
        lateinit var lastNat: Pair<BigInteger, BigInteger>
        var lastSent: BigInteger? = null
        while (true) {
            var inputs = BooleanArray(computers.size)
            for (nic in computers) {
                synchronized(nic.outputs) {
                    if (nic.outputs.size >= 3) {
                        val (i, x, y) = nic.outputs.take(3)
                        println("$i $x $y")
                        if (i.toInt() == 255) {
                            println("Last nat = $x $y")
                            lastNat = x to y
                        } else {
                            synchronized(computers[i.toInt()].inputs) {
                                computers[i.toInt()].inputs.addAll(listOf(x, y))
                                inputs[i.toInt()] = true
                            }
                        }

                        repeat(3) {
                            nic.outputs.removeAt(0)
                        }
                    }
                }
            }
            if (inputs.none { it }) {
                if (lastSent != null && lastSent == lastNat.second) {
                    return lastSent.toInt()
                }
                println("Sending = ${lastNat.first} ${lastNat.second}")
                lastSent = lastNat.second
                synchronized(computers[0].inputs) {
                    computers[0].inputs.addAll(lastNat.toList())
                }
            }
        }
    }
}