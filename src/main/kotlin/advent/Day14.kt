package advent

import kotlin.math.ceil

fun main() {
    val chemicals = loadFile("day14.txt").readLines().map(::parseLine).toMap()
    println(chemicals)
    println(calculateNeededOres(chemicals))
}

fun calculateNeededOres(chemicals: Map<Chemical, List<Chemical>>): Long {
    val remainingTransformations = chemicals.toMutableMap()
    val toProduce = mutableListOf(Chemical(1, "FUEL"))
    val remaining = mutableListOf<Chemical>()
    var neededOres = 0L
    val treated = mutableListOf<String>()
    while (toProduce.isNotEmpty()) {
        // TODO DO NOT USE A STACK, CHECK WHICH PRODUCTS ARE ONLY PRODUCED AND NOT CONSTITUANTS (except already treated)
        // TODO TREAT THOSE FIRST
        val nextOnes = toProduce.filter { (_, s) ->
            remainingTransformations.none { (reactive, list) -> list.map(Chemical::symbol).contains(s) && !treated.contains(reactive.symbol) }
        }
        println(nextOnes)

        toProduce.removeAll(nextOnes)
        treated.addAll(nextOnes.map(Chemical::symbol))

        nextOnes.forEach { head ->
            if (head.symbol == "ORE") {
                neededOres += head.count
            } else {
                val previousRest = remaining.find { it.symbol == head.symbol }
                if (previousRest != null) {
                    remaining.remove(previousRest)
                    val rest = head.count - previousRest.count
                    if (rest > 0) {
                        toProduce.add(Chemical(rest, head.symbol))
                    }
                    if (rest < 0) {
                        remaining.add(Chemical(-rest, head.symbol))
                    }
                } else {
                    val key = chemicals.keys.find { it.symbol == head.symbol }!!
                    val reactives = chemicals.getValue(key)
                    var reactionCount = ceil(head.count.toDouble() / key.count).toInt()
                    while (reactives.all { (nb, r) -> remaining.any { (nbRem, rRem) -> r == rRem && nbRem >= nb } }) {
                        // Remove from remaining
                        reactives.forEach { (n, r) ->
                            val rem = remaining.find { it.symbol == r }!!
                            remaining.remove(rem)
                            if (rem.count > n) {
                                remaining.add(Chemical(rem.count, r))
                            }
                        }
                        reactionCount--
                    }
                    reactives.forEach { (count, symbol) ->
                        toProduce.add(Chemical(count * reactionCount, symbol))
                    }
                    val rest = (key.count * reactionCount) % head.count
                    if (rest > 0) {
                        // Check if not already present
                        val rem = remaining.find { it.symbol == head.symbol }
                        if (rem != null) {
                            remaining.remove(rem)
                            remaining.add(Chemical(rest + rem.count, head.symbol))
                        } else {
                            remaining.add(Chemical(rest, head.symbol))
                        }
                    }
                }
            }
        }
    }
    println(remaining)
    return neededOres
}

fun parseLine(line: String): Pair<Chemical, List<Chemical>> {
    val (reactives, resultant) = line.split(" => ")
    return resultant.toChemical() to reactives.split(", ").map(String::toChemical)
}

data class Chemical(val count: Int, val symbol: String)

fun String.toChemical(): Chemical {
    val (count, symbol) = this.split(" ")
    return Chemical(count.toInt(), symbol)
}

// < 2934492