package net.olegg.aoc.year2019.day15

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode
import java.util.ArrayDeque

/**
 * See [Year 2019, Day 15](https://adventofcode.com/2019/day/15)
 */
object Day15 : DayOf2019(15) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseLongs(",")
        .toLongArray()

    val map = mutableMapOf(Vector2D() to (1L to 0))

    runBlocking {
      val input = Channel<Long>(Channel.UNLIMITED)
      val output = Channel<Long>(Channel.UNLIMITED)

      GlobalScope.launch {
        val intcode = Intcode(program)
        intcode.eval(input, output)
        output.close()
      }

      val stack = ArrayDeque<Move>(Neighbors4.map { Move.Forward(it, Vector2D(), 1) })

      launch {
        while (stack.isNotEmpty()) {
          val curr = stack.pop()
          input.send(codes[curr.move] ?: 0L)
          val result = output.receive()
          if (curr is Move.Forward) {
            val newPosition = curr.position + curr.move.step
            when (result) {
              0L -> {
                map[newPosition] = 0L to -1
              }
              1L, 2L -> {
                val prev = map[newPosition]
                if (prev == null || prev.second > curr.distance) {
                  map[newPosition] = result to curr.distance
                  stack.push(Move.Return(returns[curr.move] ?: throw IllegalStateException()))
                  Neighbors4.filter { it != returns[curr.move] }
                      .filter { (map[newPosition + it.step]?.second ?: Int.MAX_VALUE) > curr.distance + 1 }
                      .map { Move.Forward(it, newPosition, curr.distance + 1) }
                      .forEach { stack.push(it) }
                }
              }
            }
          }
        }
      }
    }

    return map.values.first { it.first == 2L }.second
  }

  sealed class Move {
    abstract val move: Directions

    data class Forward(
        override val move: Directions,
        val position: Vector2D,
        val distance: Int
    ): Move()

    data class Return(
        override val move: Directions
    ): Move()
  }

  private val returns = mapOf(U to D, D to U, L to R, R to L)
  private val codes = mapOf(U to 1L, D to 2L, L to 3L, R to 4L)
}

fun main() = SomeDay.mainify(Day15)
