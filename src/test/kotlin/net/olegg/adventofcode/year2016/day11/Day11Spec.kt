package net.olegg.adventofcode.year2016.day11

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

object Day11Spec : Spek({
    given("Task 1") {
        val day = Day11()

        on("small task") {
            val result = day.first("""
The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
The second floor contains a hydrogen generator.
The third floor contains a lithium generator.
The fourth floor contains nothing relevant.
                """)

            it("should return 11") {
                assertEquals("11", result, "4 items")
            }
        }
    }
})
