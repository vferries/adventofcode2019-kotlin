package advent

import org.junit.jupiter.api.Test

class Day16Tests {
    @Test
    fun `with input 12345678`() {
        val input = "12345678".map(Char::toString).map(String::toInt)
        val pattern = listOf(0, 1, 0, -1)
        val result = input.mapIndexed { i, x ->
            //TODO duplicate pattern ? appli round on division ?
            lastDigit(input.mapIndexed { index, e ->
                e * pattern[(index+1) % pattern.size]
            }.sum())
        }
        println(result)
    }

    private fun lastDigit(i: Int): Int = i.toString().last().toString().toInt()
}