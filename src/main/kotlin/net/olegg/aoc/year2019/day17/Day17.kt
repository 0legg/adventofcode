package net.olegg.aoc.year2019.day17

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.CCW
import net.olegg.aoc.utils.CW
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 17](https://adventofcode.com/2019/day/17)
 */
object Day17 : DayOf2019(17) {
  override fun first(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    return runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      GlobalScope.launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      val map = output.toList()
        .map { it.toChar() }
        .joinToString(separator = "")
        .trim()
        .lines()
        .map { it.toList() }

      return@runBlocking map.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          val pos = Vector2D(x, y)

          return@mapIndexedNotNull x.takeIf {
            c == '#' && Neighbors4.map { pos + it.step }.all { map[it] == '#' }
          }
        }.sumBy { it * y }
      }.sum()
    }
  }

  override fun second(data: String): Any? {
    val program = data
      .trim()
      .parseLongs(",")
      .toLongArray()

    val interactive = program.copyOf().also { it[0] = 2 }

    val map = runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      return@runBlocking output.toList()
        .map { it.toChar() }
        .joinToString(separator = "")
        .trim()
        .lines()
        .map { it.toList() }
    }

    return runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      GlobalScope.launch {
        val intcode = Intcode(interactive)
        intcode.eval(input, output)
        output.close()
      }

      val start = map.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          Vector2D(x, y).takeIf { c in setOf('^', 'v', '<', '>') }
        }
      }.flatten().first()

      val direction = when (map[start.y][start.x]) {
        '^' -> U
        'v' -> D
        '<' -> L
        '>' -> R
        else -> throw IllegalStateException()
      }

      val movement = generateSequence(Triple('X' to 0, start, direction)) { (_, from, dir) ->
        val leftStep = CCW[dir] ?: throw IllegalStateException()
        val rightStep = CW[dir] ?: throw IllegalStateException()
        val left = from + leftStep.step
        val right = from + rightStep.step

        return@generateSequence when {
          map[left] == '#' -> {
            val length = generateSequence(1) { it + 1 }
              .map { from + leftStep.step * it }
              .takeWhile { map[it] == '#' }
              .toList()
              .size

            Triple('L' to length, from + leftStep.step * length, leftStep)
          }
          map[right] == '#' -> {
            val length = generateSequence(1) { it + 1 }
              .map { from + rightStep.step * it }
              .takeWhile { map[it] == '#' }
              .toList()
              .size

            Triple('R' to length, from + rightStep.step * length, rightStep)
          }
          else -> null
        }
      }

      fun List<Pair<Char, Int>>.stringify() = joinToString(separator = ",") { "${it.first},${it.second}" }
      val longProgram = movement.map { it.first }.drop(1).toList()

      val maybeAs = longProgram
        .scan(emptyList<Pair<Char, Int>>()) { acc, value -> acc + value }
        .drop(1)
        .takeWhile { it.stringify().length <= 20 }

      val dicts = maybeAs.flatMap { a ->
        val (alength, aposition) = generateSequence(0) { acc ->
          when {
            longProgram.matches(acc, a) -> acc + a.size
            else -> null
          }
        }.toList().let { it.size - 1 to it.last() }

        val maybeBs = longProgram
          .subList(aposition, longProgram.size)
          .scan(emptyList<Pair<Char, Int>>()) { acc, value -> acc + value }
          .drop(1)
          .takeWhile { it.stringify().length <= 20 }

        maybeBs.flatMap { b ->
          val (blength, bposition) = generateSequence(aposition) { acc ->
            when {
              longProgram.matches(acc, a) -> acc + a.size
              longProgram.matches(acc, b) -> acc + b.size
              else -> null
            }
          }.toList().let { alength + it.size - 1 to it.last() }

          val maybeCs = longProgram
            .subList(bposition, longProgram.size)
            .scan(emptyList<Pair<Char, Int>>()) { acc, value -> acc + value }
            .drop(1)
            .takeWhile { it.stringify().length <= 20 }

          maybeCs.mapNotNull { c ->
            val (clength, cposition) = generateSequence(bposition) { acc ->
              when {
                longProgram.matches(acc, a) -> acc + a.size
                longProgram.matches(acc, b) -> acc + b.size
                longProgram.matches(acc, c) -> acc + c.size
                else -> null
              }
            }.toList().let { blength + it.size - 1 to it.last() }

            return@mapNotNull listOf(a, b, c).takeIf { cposition == longProgram.size && clength <= 10 }
          }
        }
      }

      val (a, b, c) = dicts.first()

      val encoding = generateSequence(0 to emptyList<Char>()) { (position, acc) ->
        when {
          longProgram.matches(position, a) -> (position + a.size) to acc + 'A'
          longProgram.matches(position, b) -> (position + b.size) to acc + 'B'
          longProgram.matches(position, c) -> (position + c.size) to acc + 'C'
          else -> null
        }
      }.last().second.joinToString(separator = ",")

      listOf(encoding, a.stringify(), b.stringify(), c.stringify(), "n")
        .joinToString(separator = "\n", postfix = "\n")
        .forEach {
          input.send(it.toLong())
        }

      return@runBlocking output.toList().last()
    }
  }

  private operator fun <T> List<List<T>>.get(v: Vector2D): T? = when {
    v.y !in indices -> null
    v.x !in this[v.y].indices -> null
    else -> this[v.y][v.x]
  }

  private fun <T> List<T>.matches(from: Int, other: List<T>): Boolean {
    return other.size + from <= size && subList(from, from + other.size) == other
  }
}

fun main() = SomeDay.mainify(Day17)
