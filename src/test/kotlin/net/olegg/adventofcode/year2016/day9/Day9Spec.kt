package net.olegg.adventofcode.year2016.day9

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.DefaultAsserter

object Day9Spec : Spek({
    val a = DefaultAsserter()

    describe("Task 1") {
        val day = Day9()

        context("ADVENT") {
            val result = day.first("ADVENT")
            it("should return 6") {
                a.assertEquals(message = "Plain text", expected = "6", actual = result)
            }
        }

        context("A(1x5)BC") {
            val result = day.first("A(1x5)BC")
            it("should return 7") {
                a.assertEquals(message = "Simple repetition", expected = "7", actual = result)
            }
        }

        context("(3x3)XYZ") {
            val result = day.first("(3x3)XYZ")
            it("should return 9") {
                a.assertEquals(message = "Complex repetition", expected = "9", actual = result)
            }
        }

        context("A(2x2)BCD(2x2)EFG") {
            val result = day.first("A(2x2)BCD(2x2)EFG")
            it("should return 11") {
                a.assertEquals(message = "Multiple repetition", expected = "11", actual = result)
            }
        }

        context("(6x1)(1x3)A") {
            val result = day.first("(6x1)(1x3)A")
            it("should return 6") {
                a.assertEquals(message = "Unfolds once", expected = "6", actual = result)
            }
        }

        context("X(8x2)(3x3)ABCY") {
            val result = day.first("X(8x2)(3x3)ABCY")
            it("should return 18") {
                a.assertEquals(message = "Unfolds once while repeat", expected = "18", actual = result)
            }
        }
    }

    describe("Task 2") {
        val day = Day9()

        context("(3x3)XYZ") {
            val result = day.second("(3x3)XYZ")
            it("should return 9") {
                a.assertEquals(message = "Complex repetition", expected = "9", actual = result)
            }
        }

        context("X(8x2)(3x3)ABCY") {
            val result = day.second("X(8x2)(3x3)ABCY")
            it("should return 20") {
                a.assertEquals(message = "Depth = 2", expected = "20", actual = result)
            }
        }

        context("(27x12)(20x12)(13x14)(7x10)(1x12)A") {
            val result = day.second("(27x12)(20x12)(13x14)(7x10)(1x12)A")
            it("should return 241920") {
                a.assertEquals(message = "Deep depth", expected = "241920", actual = result)
            }
        }

        context("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN") {
            val result = day.second("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")
            it("should return 445") {
                a.assertEquals(message = "Long string", expected = "445", actual = result)
            }
        }
    }
})