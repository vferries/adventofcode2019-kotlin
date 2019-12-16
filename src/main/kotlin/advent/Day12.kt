package advent

import kotlin.math.absoluteValue
import kotlin.math.sign

data class Pos3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Pos3D) = Pos3D(x + other.x, y + other.y, z + other.z)
}

data class Planet(val position: Pos3D, val velocity: Pos3D = Pos3D(0, 0, 0)) {
    fun totalEnergy() = energy(position) * energy(velocity)

    fun energy(p: Pos3D) = p.x.absoluteValue + p.y.absoluteValue + p.z.absoluteValue
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

fun repeatingState(planets: List<Planet>): Int {
    var stepCount = 1
    var runner = nextStep(nextStep(planets))
    var follower = nextStep(planets)
    while (runner.toString() != follower.toString()) {
        runner = nextStep(nextStep(runner))
        follower = nextStep(follower)
        stepCount++
    }
    return stepCount
}

fun main() {
    val planets = loadFile("day12.txt").readLines().map {
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