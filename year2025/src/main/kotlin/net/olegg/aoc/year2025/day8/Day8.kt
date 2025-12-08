package net.olegg.aoc.year2025.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.UnionFind
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2025.DayOf2025
import java.util.PriorityQueue

/**
 * See [Year 2025, Day 8](https://adventofcode.com/2025/day/8)
 */
object Day8 : DayOf2025(8) {
  override fun first(): Any? {
    val boxes = lines
      .map { it.parseInts(",") }
      .map { Vector3D(it[0], it[1], it[2]) }

    val edges = boxes
      .indices
      .toList()
      .pairs()
      .map { (a, b) ->
        Triple(
          a,
          b,
          (boxes[b] - boxes[a]).run {
            x.toLong() * x.toLong() + y.toLong() * y.toLong() + z.toLong() * z.toLong()
          }
        )
      }
    val queue = PriorityQueue<Triple<Int, Int, Long>>(compareBy { it.third })
    queue.addAll(edges)
    val uf = UnionFind(boxes.size)

    repeat(1000) {
      val curr = queue.remove()
      uf.union(curr.first, curr.second)
    }

    val counts = boxes.indices
      .map { uf.root(it) }
      .groupingBy { it }
      .eachCount()

    return counts.values
      .sortedDescending()
      .take(3)
      .reduce(Int::times)
  }
}

fun main() = SomeDay.mainify(Day8)
