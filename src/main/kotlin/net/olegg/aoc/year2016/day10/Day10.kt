package net.olegg.aoc.year2016.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 10](https://adventofcode.com/2016/day/10)
 */
object Day10 : DayOf2016(10) {
  private val VALUE_PATTERN = "value (\\d+) goes to bot (\\d+)".toRegex()
  private val GIVE_PATTERN = "bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)".toRegex()

  override fun first(): Any? {
    val bots = mutableMapOf<Int, MutableSet<Int>>()
    val actions = mutableMapOf<Int, Pair<Int, Int>>()
    val search = setOf(17, 61)

    val (values, gives) = lines.partition { VALUE_PATTERN.matches(it) }

    values.forEach { command ->
      VALUE_PATTERN.matchEntire(command)?.let { match ->
        val (value, id) = match.destructured
        bots.getOrPut(id.toInt()) { mutableSetOf() } += value.toInt()
      }
    }

    gives.forEach { command ->
      GIVE_PATTERN.matchEntire(command)?.let { match ->
        val (id, lowType, lowId, highType, highId) = match.destructured
        actions[id.toInt()] = Pair(
          if (lowType == "output") -lowId.toInt() - 1 else lowId.toInt(),
          if (highType == "output") -highId.toInt() - 1 else highId.toInt()
        )
      }
    }

    while (search !in bots.values) {
      val active = bots.filter { it.value.size == 2 }
      active
        .map { bot -> actions.getOrDefault(bot.key, 0 to 0) to bot.value }
        .forEach { (action, value) ->
          val min = value.minOrNull() ?: Int.MIN_VALUE
          val max = value.maxOrNull() ?: Int.MAX_VALUE
          bots.getOrPut(action.first) { mutableSetOf() } += min
          bots.getOrPut(action.second) { mutableSetOf() } += max
        }

      active.keys
        .filter { it >= 0 }
        .forEach { id ->
          bots.remove(id)
          actions.remove(id)
        }
    }

    return bots.filter { it.value == search }.keys.joinToString()
  }

  override fun second(): Any? {
    val bots = mutableMapOf<Int, MutableSet<Int>>()
    val actions = mutableMapOf<Int, Pair<Int, Int>>()

    val (values, gives) = lines.partition { VALUE_PATTERN.matches(it) }

    values.forEach { command ->
      VALUE_PATTERN.matchEntire(command)?.let { match ->
        val (value, id) = match.destructured
        bots.getOrPut(id.toInt()) { mutableSetOf() } += value.toInt()
      }
    }

    gives.forEach { command ->
      GIVE_PATTERN.matchEntire(command)?.let { match ->
        val (id, lowType, lowId, highType, highId) = match.destructured
        actions[id.toInt()] = Pair(
          if (lowType == "output") -lowId.toInt() - 1 else lowId.toInt(),
          if (highType == "output") -highId.toInt() - 1 else highId.toInt()
        )
      }
    }

    while (bots.any { it.value.size == 2 } && actions.isNotEmpty()) {
      val active = bots.filter { it.value.size == 2 }
      active
        .map { bot -> actions.getOrDefault(bot.key, 0 to 0) to bot.value }
        .forEach { (action, value) ->
          val min = value.minOrNull() ?: Int.MIN_VALUE
          val max = value.maxOrNull() ?: Int.MAX_VALUE
          bots.getOrPut(action.first) { mutableSetOf() } += min
          bots.getOrPut(action.second) { mutableSetOf() } += max
        }

      active.keys
        .filter { it >= 0 }
        .forEach { id ->
          bots.remove(id)
          actions.remove(id)
        }
    }

    return (-3..-1)
      .map { bots[it].orEmpty().sum() }
      .reduce { a, b -> a * b }
  }
}

fun main() = SomeDay.mainify(Day10)
