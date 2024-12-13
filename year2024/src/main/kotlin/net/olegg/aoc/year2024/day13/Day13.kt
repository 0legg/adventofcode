package net.olegg.aoc.year2024.day13

import com.microsoft.z3.Context
import com.microsoft.z3.Status
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 13](https://adventofcode.com/2024/day/13)
 */
object Day13 : DayOf2024(13) {
  private val digits = "(\\d+)".toRegex()
  override fun first(): Any? {
    val max = 100
    val rawConfigs = data.split("\n\n")
      .map { it.lines() }
      .map { config ->
        config.map { row ->
          val parsed = digits.findAll(row).map { it.value.toInt() }.toList()
          Vector2D(parsed.first(), parsed.last())
        }
      }

    return rawConfigs.sumOf { config ->
      val (buttonA, buttonB, prize) = config
      (0..max).flatMap { a ->
        (0..max).mapNotNull { b ->
          if (buttonA * a + buttonB * b == prize) (3 * a + b) else null
        }
      }.minOrNull() ?: 0
    }
  }

  @Suppress("UsePropertyAccessSyntax")
  override fun second(): Any? {
    val add = 10000000000000L
    val rawConfigs = data.split("\n\n")
      .map { it.lines() }
      .map { config ->
        config.map { row ->
          val parsed = digits.findAll(row).map { it.value.toInt() }.toList()
          Vector2D(parsed.first(), parsed.last())
        }
      }

    return rawConfigs.sumOf { config ->
      val (buttonA, buttonB, prize) = config
      Context().use { ctx ->
        val zero = ctx.mkInt(0)
        val three = ctx.mkInt(3)
        val ax = ctx.mkInt(buttonA.x)
        val ay = ctx.mkInt(buttonA.y)
        val bx = ctx.mkInt(buttonB.x)
        val by = ctx.mkInt(buttonB.y)
        val px = ctx.mkInt(prize.x + add)
        val py = ctx.mkInt(prize.y + add)

        val a = ctx.mkConst(
          ctx.mkSymbol("a"),
          ctx.getIntSort(),
        )
        val b = ctx.mkConst(
          ctx.mkSymbol("b"),
          ctx.getIntSort(),
        )
        val res = ctx.mkAdd(ctx.mkMul(a, three), b)

        val opt = ctx.mkOptimize()
        val min = opt.MkMinimize(res)

        val status = opt.Check(
          ctx.mkGe(a, zero),
          ctx.mkGe(b, zero),
          ctx.mkEq(ctx.mkAdd(ctx.mkMul(ax, a), ctx.mkMul(bx, b)), px),
          ctx.mkEq(ctx.mkAdd(ctx.mkMul(ay, a), ctx.mkMul(by, b)), py),
        )

        if (status == Status.SATISFIABLE) {
          min.value.toString().toLong()
        } else {
          0
        }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day13)
