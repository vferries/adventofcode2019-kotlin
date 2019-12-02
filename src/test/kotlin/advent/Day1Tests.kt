package advent

import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day1Tests {
    @ParameterizedTest(name = "{0} should be {1}")
    @CsvSource(
            "12,    2",
            "14,    2",
            "1969,  654",
            "100756,  33583"
    )
    fun fuelRequired(mass: Int, expectedResult: Int) {
        val day1 = Day1()
        assertEquals(expectedResult, day1.fuelRequired(mass))
    }

    @ParameterizedTest(name = "{0} should be {1} with fuel mass")
    @CsvSource(
            "12,    2",
            "1969,  966",
            "100756, 50346"
    )
    fun fuelRequiredWithFuelMass(mass: Int, expectedResult: Int) {
        val day1 = Day1()
        assertEquals(expectedResult, day1.fuelRequiredWithFuelMass(mass))
    }
}
