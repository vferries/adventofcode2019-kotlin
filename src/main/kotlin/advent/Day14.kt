package advent

fun main() {
    val chemicals = loadFile("day14.txt").readLines().map(::parseLine).toMap()
    println(chemicals)
    println(calculateNeededOres(chemicals))

    println(howManyFuels(chemicals, 1000000000000L))
    // > 782199
    println(calculateNeededOres(chemicals, 782199))
}

fun howManyFuels(chemicals: Map<Chemical, List<Chemical>>, availableOres: Long): Long {
    var base = availableOres / calculateNeededOres(chemicals).toInt()
    var upper = base * 10
    do {
        val midPoint = (base + upper) / 2
        if (calculateNeededOres(chemicals, midPoint) > availableOres) {
            upper = midPoint
        } else {
            base = midPoint
        }
    } while (base < upper - 1)
    return base
}

fun calculateNeededOres(chemicals: Map<Chemical, List<Chemical>>, fuelCount: Long = 1L): Long {
    var neededOres = 0L
    val toProduce = mutableListOf(Chemical(fuelCount, "FUEL"))
    val treated = mutableListOf<String>()
    while (toProduce.isNotEmpty()) {
        val nextOnes = toProduce.filter { (_, s) ->
            chemicals.none { (reactive, list) -> list.map(Chemical::symbol).contains(s) && !treated.contains(reactive.symbol) }
        }

        toProduce.removeAll(nextOnes)
        treated.addAll(nextOnes.map(Chemical::symbol))

        nextOnes.forEach { head ->
            if (head.symbol == "ORE") {
                neededOres += head.count
            } else {
                val key = chemicals.keys.find { it.symbol == head.symbol }!!
                val reactives = chemicals.getValue(key)
                val restingProduct = head.count % key.count
                val reactionCount = head.count / key.count + if (restingProduct == 0L) 0 else 1
                reactives.forEach { (count, symbol) ->
                    addToList(toProduce, Chemical(count * reactionCount, symbol))
                }
            }
        }
    }
    return neededOres
}

fun addToList(list: MutableList<Chemical>, chemical: Chemical) {
    val current = list.find { it.symbol == chemical.symbol }
    if (current != null) {
        list.remove(current)
        list.add(Chemical(current.count + chemical.count, chemical.symbol))
    } else {
        list.add(chemical)
    }
}

fun parseLine(line: String): Pair<Chemical, List<Chemical>> {
    val (reactives, resultant) = line.split(" => ")
    return resultant.toChemical() to reactives.split(", ").map(String::toChemical)
}

data class Chemical(val count: Long, val symbol: String)

fun String.toChemical(): Chemical {
    val (count, symbol) = this.split(" ")
    return Chemical(count.toLong(), symbol)
}

// < 2934492