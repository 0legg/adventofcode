package net.olegg.aoc.year2022.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.gcd
import net.olegg.aoc.utils.lcf
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 17](https://adventofcode.com/2022/day/17)
 */
object Day17 : DayOf2022(17) {
  private val rocks = listOf(
    """
      ####
    """.trimIndent(),
    """
      .#.
      ###
      .#.
    """.trimIndent(),
    """
      ..#
      ..#
      ###
    """.trimIndent(),
    """
      #
      #
      #
      #
    """.trimIndent(),
    """
      ##
      ##
    """.trimIndent(),
  ).map { raw -> raw.lines().map { it.toList() } }

  override fun first(): Any? {
    return solve(2022L)
  }

  override fun second(): Any? {
    return solve(1000000000000L)
  }

  private fun solve(steps: Long): Long {
    val jets = data
      .map { char ->
        when (char) {
          '<' -> -1
          '>' -> 1
          else -> 0
        }
      }

    val stack = ArrayDeque<MutableList<Char>>()
    var jetIndex = 0
    val heights = mutableListOf<Int>()

    val seen = mutableMapOf<Triple<Int, Int, Vector2D>, Int>()

    val until = steps.coerceAtMost(Int.MAX_VALUE.toLong()).toInt()

    repeat(until) { step ->
      val rockIndex = step % rocks.size
      val rock = rocks[rockIndex]
      repeat(3 + rock.size) {
        stack.addFirst(MutableList(7) { '.' })
      }
      var position = Vector2D(2, 0)
      var counter = 0
      while (true) {
        if (counter % 2 == 0) {
          val jet = jets[jetIndex]
          val next = Vector2D(position.x + jet, position.y)
          if (canMove(rock, stack, next)) {
            position = next
          }
          jetIndex = (jetIndex + 1) % jets.size
        } else {
          val next = Vector2D(position.x, position.y + 1)
          if (canMove(rock, stack, next)) {
            position = next
          } else {
            break
          }
        }
        counter++
      }

      rock.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
          if (c == '#') {
            stack[y + position.y][x + position.x] = '#'
          }
        }
      }

      while (stack.first().all { it == '.' }) {
        stack.removeFirst()
      }

      val state = Triple(rockIndex, jetIndex, position)
      if (state in seen) {
        val prev = seen.getValue(state)
        val loopSize = step - prev
        val loops = (steps - prev) / loopSize
        val tailSize = steps - prev - loopSize * loops

        val head = heights[prev].toLong()
        val body = (stack.size.toLong() - head) * loops
        val tail = heights[prev + tailSize.toInt() - 1] - head
        return head + body + tail
      } else {
        seen[state] = step
      }

      heights.add(stack.size)
    }

    return stack.size.toLong()
  }

  private fun canMove(rock: List<List<Char>>, stack: List<List<Char>>, topLeft: Vector2D): Boolean {
    val bottomRight = Vector2D(topLeft.x + rock.first().size - 1, topLeft.y + rock.size - 1)
    if (!stack.fit(topLeft) || !stack.fit(bottomRight)) {
      return false
    }

    rock.forEachIndexed { y, row ->
      row.forEachIndexed { x, c ->
        if (stack[y + topLeft.y][x + topLeft.x] == '#' && c == '#') return false
      }
    }

    return true
  }
}

fun main() = SomeDay.mainify(Day17)
