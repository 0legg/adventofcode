package net.olegg.aoc.year2023.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 22](https://adventofcode.com/2023/day/22)
 */
object Day22 : DayOf2023(22) {
  private val Z_STEP = Vector3D(0, 0, 1)
  override fun first(): Any? {
    val start = lines
      .map { line ->
        line.split("~")
          .map { it.parseInts(",") }
          .map { Vector3D(it[0], it[1], it[2]) }
          .toPair()
      }
      .sortedBy { minOf(it.first.z, it.second.z) }

    val filled = mutableSetOf<Vector3D>()

    val end = buildList {
      start.forEach { brick ->
        generateSequence(brick) { it.first - Z_STEP to it.second - Z_STEP }
          .takeWhile { it.first.z > 0 && it.second.z > 0 }
          .map {
            val delta = it.second - it.first
            val dir = delta.dir()
            List(delta.manhattan() + 1) { i -> it.first + dir * i }
          }
          .takeWhile { blocks -> blocks.none { it in filled } }
          .last()
          .let {
            add(it)
            filled.addAll(it)
          }
      }
    }

    println(end)

    val supports = end.mapIndexed { a, blocksA ->
      end.mapIndexedNotNull { b, blocksB ->
        if (a != b && blocksA.any { it + Z_STEP in blocksB }) {
          b
        } else {
          null
        }
      }
    }

    val revSupports = end.mapIndexed { a, blocksA ->
      end.mapIndexedNotNull { b, blocksB ->
        if (a != b && blocksB.any { it + Z_STEP in blocksA }) {
          b
        } else {
          null
        }
      }
    }

    println(supports)
    println()
    println(revSupports)

    return supports.count { support ->
      support.all { block -> revSupports[block].size > 1 }
    }
  }
}

fun main() = SomeDay.mainify(Day22)
