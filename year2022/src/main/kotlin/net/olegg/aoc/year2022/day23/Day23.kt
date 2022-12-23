package net.olegg.aoc.year2022.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.DL
import net.olegg.aoc.utils.Directions.DR
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Directions.UL
import net.olegg.aoc.utils.Directions.UR
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 23](https://adventofcode.com/2022/day/23)
 */
object Day23 : DayOf2022(23) {
  override fun first(): Any? {
    val start = matrix
      .flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          Vector2D(x, y).takeIf { c == '#' }
        }
      }

    val startDirs = listOf(
      U to listOf(UL, U, UR),
      D to listOf(DL, D, DR),
      L to listOf(UL, L, DL),
      R to listOf(UR, R, DR),
    )

    val (finish, _) = (1..10).fold(start to startDirs) { (elves, dirs), _ ->
      val proposed = elves.map { elf ->
        if (Directions.Neighbors8.none { elf + it.step in elves }) {
          elf
        } else {
          val maybeDir = dirs.firstOrNull { dir ->
            dir.second.none { elf + it.step in elves }
          }
          maybeDir?.let { elf + it.first.step } ?: elf
        }
      }

      val counts = proposed.groupingBy { it }.eachCount()

      val nextElves = elves.zip(proposed) { curr, next ->
        if (counts[next] == 1) next else curr
      }

      nextElves to dirs.drop(1) + dirs.take(1)
    }

    val minX = finish.minOf { it.x }
    val maxX = finish.maxOf { it.x }
    val minY = finish.minOf { it.y }
    val maxY = finish.maxOf { it.y }

    return (maxX - minX + 1) * (maxY - minY + 1) - finish.size
  }

  override fun second(): Any? {
    var elves = matrix
      .flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          Vector2D(x, y).takeIf { c == '#' }
        }
      }

    val dirs = ArrayDeque(
      listOf(
        U to listOf(UL, U, UR),
        D to listOf(DL, D, DR),
        L to listOf(UL, L, DL),
        R to listOf(UR, R, DR),
      )
    )

    var moves = 0
    do {
      moves++
      val proposed = elves.map { elf ->
        if (Directions.Neighbors8.none { elf + it.step in elves }) {
          elf
        } else {
          val maybeDir = dirs.firstOrNull { dir ->
            dir.second.none { elf + it.step in elves }
          }
          maybeDir?.let { elf + it.first.step } ?: elf
        }
      }

      val counts = proposed.groupingBy { it }.eachCount()

      val nextElves = elves.zip(proposed) { curr, next ->
        if (counts[next] == 1) next else curr
      }

      val moved = elves.zip(nextElves).any { it.first != it.second }
      elves = nextElves
      dirs.addLast(dirs.removeFirst())
    } while (moved)

    return moves
  }
}

fun main() = SomeDay.mainify(Day23)
