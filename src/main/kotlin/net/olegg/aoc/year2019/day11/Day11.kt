package net.olegg.aoc.year2019.day11

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 11](https://adventofcode.com/2019/day/11)
 */
object Day11 : DayOf2019(11) {
  override fun first(): Any? {
    val program = data
      .parseLongs(",")
      .toLongArray()

    val result = runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)
      val painted = mutableSetOf<Vector2D>()

      coroutineScope {
        launch {
          val map = mutableMapOf<Vector2D, Long>()
          var position = Vector2D()
          var direction = Directions.U.step

          while (!output.isClosedForReceive) {
            input.send(map.getOrDefault(position, 0L))
            map[position] = output.receive()
            painted += position
            val turn = output.receive()
            direction = when (turn) {
              0L -> Vector2D(direction.y, -direction.x)
              1L -> Vector2D(-direction.y, direction.x)
              else -> error("Turn is not 0 or 1: $turn")
            }
            position = position + direction
          }
        }

        launch {
          val intcode = Intcode(program)
          intcode.eval(input, output)
          output.close()
        }
      }

      return@runBlocking painted
    }

    return result.size
  }

  override fun second(): Any? {
    val program = data
      .parseLongs(",")
      .toLongArray()

    val result = runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)
      val map = mutableMapOf<Vector2D, Long>(Vector2D() to 1)

      coroutineScope {
        launch {
          var position = Vector2D()
          var direction = Directions.U.step

          while (!output.isClosedForReceive) {
            input.send(map.getOrDefault(position, 0L))
            map[position] = output.receive()
            val turn = output.receive()
            direction = when (turn) {
              0L -> Vector2D(direction.y, -direction.x)
              1L -> Vector2D(-direction.y, direction.x)
              else -> error("Turn is not 0 or 1: $turn")
            }
            position = position + direction
          }
        }

        launch {
          val intcode = Intcode(program)
          intcode.eval(input, output)
          output.close()
        }
      }

      val minx = map.minOf { it.key.x }
      val maxx = map.maxOf { it.key.x }
      val miny = map.minOf { it.key.y }
      val maxy = map.maxOf { it.key.y }

      return@runBlocking (miny..maxy).joinToString("\n", prefix = "\n") { y ->
        (minx..maxx).joinToString("") { x ->
          when (val cell = map.getOrDefault(Vector2D(x, y), 0)) {
            0L -> "  "
            1L -> "██"
            else -> error("Unexpected value $cell")
          }
        }
      }
    }

    return result
  }
}

fun main() = SomeDay.mainify(Day11)
