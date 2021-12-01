package net.olegg.aoc.year2019.day21

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 21](https://adventofcode.com/2019/day/21)
 */
object Day21 : DayOf2019(21) {
  override fun first(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    return runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      val droid = mutableListOf<String>()
      droid += """
        OR A J
        AND C J
        NOT J J
        AND D J
      """.trimIndent().lines()

      launch(Dispatchers.Default) {
        val intcode = Intcode(program.copyOf())
        intcode.eval(input, output)
        input.close()
        output.close()
      }

      (droid + "WALK").forEach { input.sendCommand(it) }

      val result = output.toList()

      println(result.dropLastWhile { it > 255 }.map { it.toInt().toChar() }.joinToString(""))

      return@runBlocking result.last().takeIf { it > 255 } ?: -1
    }
  }

  override fun second(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    return runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      val droid = mutableListOf<String>()
      droid += """
        NOT H J
        OR C J
        AND B J
        AND A J
        NOT J J
        AND D J
      """.trimIndent().lines()

      launch(Dispatchers.Default) {
        val intcode = Intcode(program.copyOf())
        intcode.eval(input, output)
        input.close()
        output.close()
      }

      (droid + "RUN").forEach { input.sendCommand(it) }

      val result = output.toList()

      println(result.dropLastWhile { it > 255 }.map { it.toInt().toChar() }.joinToString(""))

      return@runBlocking result.last().takeIf { it > 255 } ?: -1
    }
  }

  private suspend fun Channel<Long>.sendCommand(program: String) {
    (program + "\n").forEach {
      send(it.code.toLong())
    }
  }
}

fun main() = SomeDay.mainify(Day21)
