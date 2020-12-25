package net.olegg.aoc.year2020.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 21](https://adventofcode.com/2020/day/21)
 */
object Day21 : DayOf2020(21) {
  override fun first(data: String): Any? {
    val foods = data
      .trim()
      .lines()
      .map { it.replace("(", "").replace(")", "").replace(",", "") }
      .map { it.split("contains").toPair() }
      .map { it.first.trim().split(" ").toSet() to it.second.trim().split(" ").toSet() }

    //val allAllergens = foods.flatMap { it.second }.toSet()
    val allItems = foods.flatMap { it.first }.toSet()

    val possible = foods
      .flatMap { (items, allergens) ->
        allergens.map { it to items }
      }
      .groupBy { it.first }
      .mapValues { it.value.map { mapping -> mapping.second }
        .fold(allItems) { acc, value -> acc.intersect(value) }
        .toMutableSet()
      }
      .toMutableMap()

    val finalAllergens = mutableMapOf<String, String>()
    while (possible.isNotEmpty()) {
      val toRemove = possible.filterValues { it.size == 1 }.mapValues { it.value.first() }
      val removedItems = toRemove.map { it.value }.toSet()
      finalAllergens += toRemove
      possible -= toRemove.keys
      possible.forEach {
        it.value -= removedItems
      }
    }

    val badItems = finalAllergens.values.toSet()
    val safeItems = allItems - badItems

    return foods.flatMap { it.first }.count { it in safeItems }
  }

  override fun second(data: String): Any? {
    val foods = data
      .trim()
      .lines()
      .map { it.replace("(", "").replace(")", "").replace(",", "") }
      .map { it.split("contains").toPair() }
      .map { it.first.trim().split(" ").toSet() to it.second.trim().split(" ").toSet() }

    //val allAllergens = foods.flatMap { it.second }.toSet()
    val allItems = foods.flatMap { it.first }.toSet()

    val possible = foods
      .flatMap { (items, allergens) ->
        allergens.map { it to items }
      }
      .groupBy { it.first }
      .mapValues { it.value.map { mapping -> mapping.second }
        .fold(allItems) { acc, value -> acc.intersect(value) }
        .toMutableSet()
      }
      .toMutableMap()

    val finalAllergens = mutableMapOf<String, String>()
    while (possible.isNotEmpty()) {
      val toRemove = possible.filterValues { it.size == 1 }.mapValues { it.value.first() }
      val removedItems = toRemove.map { it.value }.toSet()
      finalAllergens += toRemove
      possible -= toRemove.keys
      possible.forEach {
        it.value -= removedItems
      }
    }

    return finalAllergens.toList().sortedBy { it.first }.joinToString(",") { it.second }
  }
}

fun main() = SomeDay.mainify(Day21)
