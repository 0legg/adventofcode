package net.olegg.adventofcode.year2016.day12

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

object Day12Spec : Spek({
    given("Task 1") {
        val day = Day12()

        on("sample input") {
            val result = day.first("""
cpy 41 a
inc a
inc a
dec a
jnz a 2
dec a
                """)

            it("should return 42") {
                assertEquals("42", result, "Wrong output for sample")
            }
        }
    }
})
