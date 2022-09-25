package net.olegg.aoc.year2017.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 7](https://adventofcode.com/2017/day/7)
 */
object Day7 : DayOf2017(7) {
  override fun first(): Any? {
    return lines
      .map { line -> line.split(" ").map { it.replace(",", "") } }
      .fold(emptySet<String>() to emptySet<String>()) { acc, list ->
        val used = if (list.size > 3) acc.second + list.subList(3, list.size) else acc.second
        val free = (acc.first + list[0]) - used
        return@fold free to used
      }
      .first
      .first()
  }

  override fun second(): Any? {
    val disks = lines
      .map { it.replace("[()\\->,]".toRegex(), "").split("\\s+".toRegex()) }
      .associate { it[0] to (it[1].toInt() to it.subList(2, it.size)) }

    val head = disks.entries.fold(emptySet<String>() to emptySet<String>()) { acc, disk ->
      val used = acc.second + disk.value.second
      val free = (acc.first + disk.key) - used
      return@fold free to used
    }
      .first
      .first()

    val weights = getWeights(disks, head)

    var curr = head
    var result = 0
    do {
      val disk = disks.getOrDefault(curr, 0 to emptyList())
      val odd = disk.second.map { it to (weights[it] ?: 0) }
        .groupBy { it.second }
        .toList()
        .sortedBy { it.second.size }

      if (odd.size != 1) {
        curr = odd.first().second.first().first
        result = (disks[curr]?.first ?: 0) + odd.last().first - odd.first().first
      }
    } while (odd.size != 1)

    return result
  }

  private fun getWeights(map: Map<String, Pair<Int, List<String>>>, root: String): Map<String, Int> {
    val curr = map.getOrDefault(root, 0 to emptyList())
    val inter = curr.second
      .fold(emptyMap<String, Int>()) { acc, value -> acc + getWeights(map, value) }
      .let { children ->
        children + mapOf(root to curr.first + curr.second.sumOf { children[it] ?: 0 })
      }
    return inter
  }
}

fun main() = SomeDay.mainify(Day7)
