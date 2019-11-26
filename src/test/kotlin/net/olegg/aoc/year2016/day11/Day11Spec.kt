package net.olegg.aoc.year2016.day11

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object Day11Spec : Spek({
  describe("Task 1") {
    val day = Day11

    context("small task") {
      val result = day.first(
          """|The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
                       |The second floor contains a hydrogen generator.
                       |The third floor contains a lithium generator.
                       |The fourth floor contains nothing relevant.""".trimMargin())

      it("should return 11") {
        assertEquals(11, result, "4 items")
      }
    }
  }
})
