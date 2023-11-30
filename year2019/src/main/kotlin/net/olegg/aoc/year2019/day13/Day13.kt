package net.olegg.aoc.year2019.day13

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
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
object Day13 : DayOf2019(13) {
  private val SCORE = Vector2D(-1, 0)

  override fun first(): Any? {
    val program = data
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

      return@runBlocking output.toList()
        .map { it.toInt() }
        .chunked(3)
        .associate { (x, y, tile) -> Vector2D(x, y) to tile }
    }

    return result.count { it.value == 2 }
  }

  override fun second(): Any? {
    val program = data
      .parseLongs(",")
      .toLongArray()

    val result = runBlocking {
      program[0] = 2L
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)
      val score = MutableStateFlow(0)

      coroutineScope {
        launch {
          val intcode = Intcode(program)
          intcode.eval(input, output)
          input.close()
        }

        launch {
          var me = Vector2D()
          var ball = Vector2D()
          runCatching {
            do {
              do {
                val (x, y, tile) = List(3) { output.receive().toInt() }
                val pos = Vector2D(x, y)
                when {
                  pos == SCORE -> score.value = tile
                  tile == 3 -> me = pos
                  tile == 4 -> ball = pos
                }
              } while (!output.isEmpty)

              val toSend = (ball - me).x.sign.toLong()
            } while (input.trySend(toSend).isSuccess)
          }
        }
      }

      return@runBlocking score.value
    }

    return result
  }
}

fun main() = SomeDay.mainify(Day13)
