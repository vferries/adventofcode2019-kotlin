package advent

import java.io.File

fun main(args: Array<String>) {
    val layers = File("src/main/resources/day8.txt").readText().map { it.toString().toInt() }.chunked(6*25)
    val minLayer = layers.minBy { layer ->
        layer.count {it == 0}
    }!!
    val count1 = minLayer.count { it == 1}
    val count2 = minLayer.count { it == 2}
    println(count1 * count2)

    (0..5).forEach { y ->
        (0..24).forEach { x ->
            var layer = 0
            while (layers[layer][x + 25 * y] == 2 && layer < 149) layer++
            print(if (layers[layer][x + 25 * y] == 0) " " else "#")
        }
        println()
    }
}