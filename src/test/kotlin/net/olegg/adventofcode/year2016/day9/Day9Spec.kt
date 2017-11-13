package net.olegg.adventofcode.year2016.day9

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.DefaultAsserter

object Day9Spec : Spek({
    val a = DefaultAsserter()

    given("Task 1") {
        val day = Day9()

        on("ADVENT") {
            val result = day.first("ADVENT")
            it("should return 6") {
                a.assertEquals(message = "Plain text fails", expected = "6", actual = result)
            }
        }

        on("A(1x5)BC") {
            val result = day.first("A(1x5)BC")
            it("should return 7") {
                a.assertEquals(message = "Simple repetition fails", expected = "7", actual = result)
            }
        }

        on("(3x3)XYZ") {
            val result = day.first("(3x3)XYZ")
            it("should return 9") {
                a.assertEquals(message = "Complex repetition fails", expected = "9", actual = result)
            }
        }

        on("A(2x2)BCD(2x2)EFG") {
            val result = day.first("A(2x2)BCD(2x2)EFG")
            it("should return 11") {
                a.assertEquals(message = "Multiple repetition fails", expected = "11", actual = result)
            }
        }

        on("(6x1)(1x3)A") {
            val result = day.first("(6x1)(1x3)A")
            it("should return 6") {
                a.assertEquals(message = "Unfolds once", expected = "6", actual = result)
            }
        }

        on("X(8x2)(3x3)ABCY") {
            val result = day.first("X(8x2)(3x3)ABCY")
            it("should return 18") {
                a.assertEquals(message = "Unfolds once while repeat", expected = "18", actual = result)
            }
        }
    }
})