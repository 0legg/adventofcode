package net.olegg.aoc.year2025.day10

import com.microsoft.z3.Context
import com.microsoft.z3.Status
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2025.DayOf2025
import java.util.BitSet
import kotlin.use

/**
 * See [Year 2025, Day 10](https://adventofcode.com/2025/day/10)
 */
object Day10 : DayOf2025(10) {
  override fun first(): Any? {
    return lines
      .map { line ->
        val chunks = line
          .split(" ")
          .map { chunk -> chunk.substring(1, chunk.length - 1) }

        val size = chunks.first().length
        val target = BitSet(size)
        chunks.first().forEachIndexed { index, ch ->
          if (ch == '#') {
            target[index] = true
          }
        }

        val buttons = chunks
          .drop(1)
          .dropLast(1)
          .map { chunk ->
            BitSet(size).also { button ->
              chunk.parseInts(",").forEach {
                button[it] = true
              }
            }
          }

        Triple(size, target, buttons)
      }
      .sumOf { (size, target, buttons) ->
        val queue = ArrayDeque(listOf(BitSet(size) to 0))
        val seen = mutableSetOf<BitSet>()
        while (queue.isNotEmpty()) {
          val (curr, step) = queue.removeFirst()
          if (curr == target) {
            return@sumOf step
          }
          if (!seen.add(curr)) {
            continue
          }
          val allNext = buttons
            .map { button ->
              BitSet(size).also { next ->
                next.or(curr)
                (0..<size).filter { button[it] }.onEach { next.flip(it) }
              }
            }
            .filter { it !in seen }

          queue.addAll(allNext.map { it to step + 1 })
        }
        return@sumOf -1
      }
  }

  @Suppress("UsePropertyAccessSyntax")
  override fun second(): Any? {
    return lines
      .map { line ->
        val chunks = line
          .split(" ")
          .map { chunk -> chunk.substring(1, chunk.length - 1) }

        val target = chunks.last().parseInts(",")

        val buttons = chunks
          .drop(1)
          .dropLast(1)
          .map { chunk ->
            chunk.parseInts(",").toSet()
          }

        target to buttons
      }
      .sumOf { (target, buttons) ->
        Context().use { ctx ->
          val opt = ctx.mkOptimize()

          val presses = List(buttons.size) { i ->
            ctx.mkConst(
              ctx.mkSymbol("x_$i"),
              ctx.getIntSort(),
            )
          }
          presses.forEach { press ->
            opt.Assert(
              ctx.mkGe(press, ctx.mkInt(0))
            )
          }

          target.forEachIndexed { index, wire ->
            val matchingButtons = buttons
              .zip(presses)
              .filter { (button, _) -> index in button }
              .map { it.second }

            opt.Assert(
            ctx.mkEq(
                ctx.mkInt(wire),
                ctx.mkAdd(*matchingButtons.toTypedArray())
              )
            )
          }

          val res = ctx.mkAdd(*presses.toTypedArray())
          val min = opt.MkMinimize(res)

          if (opt.Check() == Status.SATISFIABLE) {
            min.value.toString().toLong()
          } else {
            0L
          }
        }
      }
  }
}

fun main() = SomeDay.mainify(Day10)
