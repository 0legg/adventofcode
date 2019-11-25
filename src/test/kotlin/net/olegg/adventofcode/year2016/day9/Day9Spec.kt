package net.olegg.adventofcode.year2016.day9

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.DefaultAsserter
import kotlin.test.DefaultAsserter.assertEquals

object Day9Spec : Spek({

  describe("Task 1") {
    val day = Day9()

    context("ADVENT") {
      val result = day.first("ADVENT")
      it("should return 6") {
        assertEquals(message = "Plain text", expected = 6L, actual = result)
      }
    }

    context("A(1x5)BC") {
      val result = day.first("A(1x5)BC")
      it("should return 7") {
        assertEquals(message = "Simple repetition", expected = 7L, actual = result)
      }
    }

    context("(3x3)XYZ") {
      val result = day.first("(3x3)XYZ")
      it("should return 9") {
        assertEquals(message = "Complex repetition", expected = 9L, actual = result)
      }
    }

    context("A(2x2)BCD(2x2)EFG") {
      val result = day.first("A(2x2)BCD(2x2)EFG")
      it("should return 11") {
        assertEquals(message = "Multiple repetition", expected = 11L, actual = result)
      }
    }

    context("(6x1)(1x3)A") {
      val result = day.first("(6x1)(1x3)A")
      it("should return 6") {
        assertEquals(message = "Unfolds once", expected = 6L, actual = result)
      }
    }

    context("X(8x2)(3x3)ABCY") {
      val result = day.first("X(8x2)(3x3)ABCY")
      it("should return 18") {
        assertEquals(message = "Unfolds once while repeat", expected = 18L, actual = result)
      }
    }
  }

  describe("Task 2") {
    val day = Day9()

    context("(3x3)XYZ") {
      val result = day.second("(3x3)XYZ")
      it("should return 9") {
        assertEquals(message = "Complex repetition", expected = 9L, actual = result)
      }
    }

    context("X(8x2)(3x3)ABCY") {
      val result = day.second("X(8x2)(3x3)ABCY")
      it("should return 20") {
        assertEquals(message = "Depth = 2", expected = 20L, actual = result)
      }
    }

    context("(27x12)(20x12)(13x14)(7x10)(1x12)A") {
      val result = day.second("(27x12)(20x12)(13x14)(7x10)(1x12)A")
      it("should return 241920") {
        assertEquals(message = "Deep depth", expected = 241920L, actual = result)
      }
    }

    context("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN") {
      val result = day.second("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")
      it("should return 445") {
        assertEquals(message = "Long string", expected = 445L, actual = result)
      }
    }
  }
})
