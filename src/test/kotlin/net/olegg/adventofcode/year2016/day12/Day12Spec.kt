package net.olegg.adventofcode.year2016.day12

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object Day12Spec : Spek({
    describe("Task 1") {
        val day = Day12()

        context("sample input") {
            val result = day.first("""
                |cpy 41 a
                |inc a
                |inc a
                |dec a
                |jnz a 2
                |dec a
                |""".trimMargin())

            it("should return 42") {
                assertEquals("42", result, "Wrong output for sample")
            }
        }
    }
})
