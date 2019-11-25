package net.olegg.adventofcode.year2016.day21

import net.olegg.adventofcode.year2016.day21.Day21.Op.Companion.fromString
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object Day21Spec : Spek({
  val parseToList = { str: String -> str.trimIndent().lines().mapNotNull { fromString(it) } }

  describe("Task 1") {
    val day = Day21()

    context("abcde, no op") {
      val result = day.scramble("abcde", listOf())

      it("should return abcde") {
        assertEquals("abcde", result, "no op failed")
      }
    }

    context("abcde, swap position") {
      val result = day.scramble("abcde", parseToList("""
                swap position 4 with position 0
                """))

      it("should return ebcda") {
        assertEquals("ebcda", result, "swap position failed")
      }
    }

    context("ebcda, swap letter") {
      val result = day.scramble("ebcda", parseToList("""
                swap letter d with letter b
                """))

      it("should return edcba") {
        assertEquals("edcba", result, "swap letter failed")
      }
    }

    context("edcba, reverse") {
      val result = day.scramble("edcba", parseToList("""
                reverse positions 0 through 4
                """))

      it("should return abcde") {
        assertEquals("abcde", result, "reverse failed")
      }
    }

    context("abcde, rotate left") {
      val result = day.scramble("abcde", parseToList("""
                rotate left 1 step
                """))

      it("should return bcdea") {
        assertEquals("bcdea", result, "rotate left failed")
      }
    }

    context("abcde, rotate right") {
      val result = day.scramble("abcde", parseToList("""
                rotate right 1 step
                """))

      it("should return eabcd") {
        assertEquals("eabcd", result, "rotate right failed")
      }
    }

    context("bcdea, move position") {
      val result = day.scramble("bcdea", parseToList("""
                move position 1 to position 4
                """))

      it("should return bdeac") {
        assertEquals("bdeac", result, "move position failed")
      }
    }

    context("bdeac, move position") {
      val result = day.scramble("bdeac", parseToList("""
                move position 3 to position 0
                """))

      it("should return abdec") {
        assertEquals("abdec", result, "move position failed")
      }
    }

    context("abdec, rotate based on position") {
      val result = day.scramble("abdec", parseToList("""
                rotate based on position of letter b
                """))

      it("should return ecabd") {
        assertEquals("ecabd", result, "rotate based on position failed")
      }
    }

    context("ecabd, rotate based on position") {
      val result = day.scramble("ecabd", parseToList("""
                rotate based on position of letter d
                """))

      it("should return decab") {
        assertEquals("decab", result, "rotate based on position failed")
      }
    }

    context("abcde, full test") {
      val result = day.scramble("abcde", parseToList("""
                swap position 4 with position 0
                swap letter d with letter b
                reverse positions 0 through 4
                rotate left 1 step
                move position 1 to position 4
                move position 3 to position 0
                rotate based on position of letter b
                rotate based on position of letter d
                """))

      it("should return decab") {
        assertEquals("decab", result, "full test failed")
      }
    }
  }
})
