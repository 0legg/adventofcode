package net.olegg.aoc.year2019.day11

import kotlinx.coroutines.ExperimentalCoroutinesApi
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
@ExperimentalCoroutinesApi
object Day11 : DayOf2019(11) {
  override fun first(data: String): Any? {
    val program = data
      .trim()
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
              else -> throw IllegalArgumentException()
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

  override fun second(data: String): Any? {
    val program = data
      .trim()
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
              else -> throw IllegalArgumentException()
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

      val minx = map.map { it.key.x }.minOrNull() ?: 0
      val maxx = map.map { it.key.x }.maxOrNull() ?: 0
      val miny = map.map { it.key.y }.minOrNull() ?: 0
      val maxy = map.map { it.key.y }.maxOrNull() ?: 0

      return@runBlocking (miny..maxy).joinToString("\n", prefix = "\n") { y ->
        (minx..maxx).joinToString("") { x ->
          when (map.getOrDefault(Vector2D(x, y), 0)) {
            0L -> "  "
            1L -> "██"
            else -> throw IllegalArgumentException()
          }
        }
      }
    }

    return result
  }
}

@ExperimentalCoroutinesApi
fun main() = SomeDay.mainify(Day11)
