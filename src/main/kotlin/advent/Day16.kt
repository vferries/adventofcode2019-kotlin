package advent

import kotlin.math.abs

object Day16 {
    fun part1(text: String): String {
        val digits = text.chunked(1).map(String::toInt).toIntArray()
        repeat(100) {
            for (x in digits.indices) {
                var sign = 1
                digits[x] = abs(
                    (x until digits.size step 2 * x + 2).sumBy { base ->
                        (base until minOf(base + x + 1, digits.size)).sumBy(digits::get)
                            .let { sign * it }
                            .also { sign *= -1 }
                    }
                ) % 10
            }
        }
        return digits.take(8).joinToString("")
    }

    fun part2(text: String): String {
        val digits = text.chunked(1).map(String::toInt)
        val offset = digits.take(7).fold(0) { acc, digit -> 10 * acc + digit }
        val length = digits.size
        val newLength = 10000 * length
        val value = (offset until newLength).map { digits[it % length] }.toIntArray()
        repeat(100) {
            value.indices.reversed().fold(0) { acc, i ->
                val d = abs(acc + value[i]) % 10
                value[i] = d
                d
            }
        }
        return value.take(8).joinToString("")
    }
}