package advent

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

data class Pos3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Pos3D) = Pos3D(x + other.x, y + other.y, z + other.z)
}

data class Planet(val position: Pos3D, val velocity: Pos3D = Pos3D(0, 0, 0)) {
    fun totalEnergy() = energy(position) * energy(velocity)

    fun energy(p: Pos3D) = p.x.absoluteValue + p.y.absoluteValue + p.z.absoluteValue
}

fun nextStepOnAxis(coords: List<Pair<Int, Int>>): List<Pair<Int, Int>> = coords.map { coord ->
    val (c, v) = coord
    val otherPositions = coords.filter { it != coord }.map { it.first }
    val shift = otherPositions.map { c2 -> (c2 - c).sign }.reduce(Int::plus)
    val newVelocity = v + shift
    c + newVelocity to newVelocity
}

fun nextStep(planets: List<Planet>): List<Planet> = planets.map { planet ->
    val (x, y, z) = planet.position
    val otherPlanetPositions = planets.filter { it != planet }.map(Planet::position)
    val shift = otherPlanetPositions.map { (x2, y2, z2) ->
        Pos3D((x2 - x).sign, (y2 - y).sign, (z2 - z).sign)
    }.reduce(Pos3D::plus)
    val newVelocity = planet.velocity + shift
    Planet(planet.position + newVelocity, newVelocity)
}

fun repeatingState(planets: List<Planet>): Long {
    var xAxis = planets.map { p -> p.position.x to p.velocity.x }
    var yAxis = planets.map { p -> p.position.y to p.velocity.y }
    var zAxis = planets.map { p -> p.position.z to p.velocity.z }
    val seenValues = mutableSetOf<List<Pair<Int, Int>>>()
    var xCycle = 0
    while (!seenValues.contains(xAxis)) {
        seenValues.add(xAxis)
        xAxis = nextStepOnAxis(xAxis)
        xCycle++
    }
    seenValues.clear()
    var yCycle = 0
    while (!seenValues.contains(yAxis)) {
        seenValues.add(yAxis)
        yAxis = nextStepOnAxis(yAxis)
        yCycle++
    }
    seenValues.clear()
    var zCycle = 0
    while (!seenValues.contains(zAxis)) {
        seenValues.add(zAxis)
        zAxis = nextStepOnAxis(zAxis)
        zCycle++
    }
    seenValues.clear()
    return xCycle.toLong() * yCycle.toLong() * zCycle.toLong() / (gcd(xCycle, yCycle) * gcd(yCycle, zCycle))
}

fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

fun main() {
    val planets = File("src/main/resources/day12.txt").readLines().map {
        val (x, y, z) = """<x=(.*), y=(.*), z=(.*)>""".toRegex().matchEntire(it)?.destructured!!
        Planet(Pos3D(x.toInt(), y.toInt(), z.toInt()))
    }
    println(planets)
    val totalEnergy = computeTotalEnergy(planets, 1000)
    println(totalEnergy)

    println("Repeats after ${repeatingState(planets)} steps")
}

fun computeTotalEnergy(planets: List<Planet>, nbSteps: Int): Int {
    var planets1 = planets
    repeat(nbSteps) {
        planets1 = nextStep(planets1)
    }
    return planets1.map(Planet::totalEnergy).sum()
}