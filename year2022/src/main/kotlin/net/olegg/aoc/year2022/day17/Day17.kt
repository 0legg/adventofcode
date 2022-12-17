package net.olegg.aoc.year2022.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
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

    repeat(2022) { step ->
      val rock = rocks[step % rocks.size]
      repeat(3 + rock.size) {
        stack.addFirst(MutableList(7) { '.' })
      }
      var position = Vector2D(2, 0)
      var counter = 0
      while (true) {
        if (counter % 2 == 0) {
          val jet = jets[jetIndex % jets.size]
          val next = Vector2D(position.x + jet, position.y)
          if (canMove(rock, stack, next)) {
            position = next
          }
          jetIndex++
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
    }

    return stack.size
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
