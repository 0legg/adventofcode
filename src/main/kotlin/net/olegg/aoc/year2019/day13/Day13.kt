package net.olegg.aoc.year2019.day13

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode
import kotlin.math.sign

/**
 * See [Year 2019, Day 13](https://adventofcode.com/2019/day/13)
 */
@ExperimentalCoroutinesApi
object Day13 : DayOf2019(13) {
  private val SCORE = Vector2D(-1, 0)

  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    val result = runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      val field = output.toList()
          .map { it.toInt() }
          .chunked(3)
          .map { (x, y, tile) -> Vector2D(x, y) to tile }
          .toMap()

      return@runBlocking field
    }

    return result.count { it.value == 2 }
  }

  override fun second(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    val result = runBlocking {
      program[0] = 2L
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)
      val scores = Channel<Int>(Channel.UNLIMITED)

      coroutineScope {
        launch {
          val intcode = Intcode(program)
          intcode.eval(input, output)
          output.close()
          input.close()
        }

        launch {
          val field = mutableMapOf<Vector2D, Int>()
          var me = Vector2D()
          var ball = Vector2D()
          while (!input.isClosedForSend) {
            if (!output.isClosedForReceive) {
              do {
                val (x, y, tile) = List(3) { output.receive().toInt() }
                val pos = Vector2D(x, y)
                when (tile) {
                  3 -> me = pos
                  4 -> ball = pos
                }
                if (pos == SCORE) {
                  scores.send(tile)
                } else {
                  field[pos] = tile
                }
              } while (!output.isClosedForReceive && !output.isEmpty)
            }

            if (!input.isClosedForSend) {
              input.send((ball - me).x.sign.toLong())
            }
          }
        }
      }

      scores.close()

      return@runBlocking scores.toList().last()
    }

    return result
  }

  private fun printField(field: Map<Vector2D, Int>) {
    val minx = field.map { it.key.x }.min() ?: 0
    val maxx = field.map { it.key.x }.max() ?: 0
    val miny = field.map { it.key.y }.min() ?: 0
    val maxy = field.map { it.key.y }.max() ?: 0

    println((miny..maxy).joinToString("\n", prefix = "\n") { y ->
      (minx..maxx).joinToString("") { x -> when (field.getOrDefault(Vector2D(x, y), 0)) {
        0 -> "  "
        1 -> "██"
        2 -> "░░"
        3 -> "=="
        4 -> "()"
        else -> throw IllegalArgumentException()
      } }
    })
  }
}

@ExperimentalCoroutinesApi
fun main() = SomeDay.mainify(Day13)
