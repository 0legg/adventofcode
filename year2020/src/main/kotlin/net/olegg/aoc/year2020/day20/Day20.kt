package net.olegg.aoc.year2020.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 20](https://adventofcode.com/2020/day/20)
 */
object Day20 : DayOf2020(20) {
  private val NUMBER = "\\d+".toRegex()
  private const val SIZE = 12

  private val MONSTER = """
    |                  # 
    |#    ##    ##    ###
    | #  #  #  #  #  #   
  """.trimMargin().lines().map {
    it.toList()
  }

  override fun first(): Any? {
    val tiles = data
      .split("\n\n")
      .map { it.lines() }
      .map { (NUMBER.find(it.first())?.value?.toIntOrNull() ?: 0) to it.drop(1).map { l -> l.toList() } }
      .map { it.first to profiles(it.second) }

    val profileCounts = tiles
      .flatMap { it.second }
      .groupingBy { it }
      .eachCount()

    val corners = tiles.filter { it.second.count { prof -> profileCounts[prof] == 1 } == 4 }

    return corners.map { it.first.toLong() }.reduce(Long::times)
  }

  override fun second(): Any? {
    val tiles = data
      .split("\n\n")
      .map { it.lines() }
      .associate { (NUMBER.find(it.first())?.value?.toIntOrNull() ?: 0) to it.drop(1).map { l -> l.toList() } }

    val profiles = tiles.mapValues { profiles(it.value) }

    val profileCounts = profiles
      .values
      .flatten()
      .groupingBy { it }
      .eachCount()

    val topLeftTile = profiles.entries.first { tile ->
      tile.value.count { prof -> profileCounts[prof] == 1 } == 4
    }.key

    val available = tiles
      .mapValues { orderedProfiles(it.value) }
      .toMutableMap()

    val topLeft = available[topLeftTile]!!
      .first { prof -> prof.take(2).map { profileCounts[it] } == listOf(1, 1) }

    val grid = List(SIZE) { MutableList(SIZE) { 0 to listOf<Int>() } }
    grid[0][0] = topLeftTile to topLeft

    available -= topLeftTile

    (1..<SIZE).forEach { x ->
      val left = grid[0][x - 1]
      val rightCount = available.count { left.second[2] in it.value.flatten() }
      if (rightCount != 1) {
        error("Tiles available $rightCount")
      }
      val right = available.entries
        .first { left.second[2] in it.value.map { order -> order[0] } }
        .toPair()

      val rightOrder = right.second.first { left.second[2] == it[0] }

      grid[0][x] = right.first to rightOrder
      available -= right.first
    }

    (1..<SIZE).forEach { y ->
      val top = grid[y - 1][0]
      val bottomCount = available.count { top.second[3] in it.value.flatten() }
      if (bottomCount != 1) {
        error("Tiles available $bottomCount")
      }
      val bottom = available.entries
        .first { top.second[3] in it.value.map { order -> order[1] } }
        .toPair()

      val bottomOrder = bottom.second.first { top.second[3] == it[1] }

      grid[y][0] = bottom.first to bottomOrder
      available -= bottom.first
    }

    (1..<SIZE).forEach { y ->
      (1..<SIZE).forEach { x ->
        val left = grid[y][x - 1]
        val top = grid[y - 1][x]
        val edge = listOf(left.second[2], top.second[3])
        val nextCount = available.count { edge in it.value.map { order -> order.take(2) } }
        if (nextCount != 1) {
          error("Tiles available $nextCount")
        }

        val next = available.entries
          .first { edge in it.value.map { order -> order.take(2) } }
          .toPair()

        val nextOrder = next.second.first { edge == it.take(2) }

        grid[y][x] = next.first to nextOrder
        available -= next.first
      }
    }

    val targetGrid = grid.flatMap { row ->
      val cells = row.map { (tileNum, profile) ->
        matchProfile(tiles.getValue(tileNum), profile)
      }.map {
        it.drop(1).dropLast(1).map { line -> line.drop(1).dropLast(1) }
      }

      cells[0].indices.map { line ->
        cells.flatMap { it[line] }
      }
    }

    val matchingGrid = (0..3)
      .scan(targetGrid) { acc, _ -> acc.rotate() }
      .flatMap { listOf(it, it.map { row -> row.reversed() }) }
      .first { currGrid ->
        currGrid.indices.any { y ->
          currGrid.indices.any { x ->
            hasPattern(x, y, currGrid)
          }
        }
      }

    val finalGrid = matchingGrid.map { it.toMutableList() }

    finalGrid.indices.forEach { y ->
      finalGrid.indices.forEach { x ->
        replacePattern(x, y, finalGrid)
      }
    }

    return finalGrid.sumOf { row -> row.count { it == '#' } }
  }

  private fun profile(tile: List<List<Char>>): List<Int> {
    val top = tile.first()
    val bottom = tile.last()
    val left = tile.map { it.first() }
    val right = tile.map { it.last() }

    return listOf(left, top, right, bottom)
      .map { it.joinToString(separator = "").replace('#', '1').replace('.', '0').toInt(2) }
  }

  private fun profiles(tile: List<List<Char>>): List<Int> {
    val top = tile.first()
    val bottom = tile.last()
    val left = tile.map { it.first() }
    val right = tile.map { it.last() }

    return listOf(left, top, right, bottom)
      .map { it.joinToString(separator = "").replace('#', '1').replace('.', '0') }
      .flatMap { listOf(it.toInt(2), it.reversed().toInt(2)) }
  }

  private fun orderedProfiles(tile: List<List<Char>>): List<List<Int>> {
    val top = tile.first()
    val bottom = tile.last().reversed()
    val left = tile.map { it.first() }.reversed()
    val right = tile.map { it.last() }

    return listOf(listOf(left, top, right, bottom))
      .flatMap { order -> (0..<3).scan(order) { acc, _ -> acc.drop(1) + acc.take(1) } }
      .flatMap { listOf(it, it.reversed().map { order -> order.reversed() }) }
      .map { listOf(it[0].reversed(), it[1], it[2], it[3].reversed()) }
      .map { order -> order.map { it.joinToString(separator = "").replace('#', '1').replace('.', '0').toInt(2) } }
  }

  private fun matchProfile(
    tile: List<List<Char>>,
    profile: List<Int>
  ): List<List<Char>> {
    return (0..3)
      .scan(tile) { acc, _ -> acc.rotate() }
      .flatMap { listOf(it, it.map { row -> row.reversed() }) }
      .first { profile(it) == profile }
  }

  private fun <T> List<List<T>>.rotate(): List<List<T>> {
    val size = this.size

    val target = List(size) { y -> MutableList(size) { x -> this[y][x] } }

    for (i in 0..<size / 2) {
      for (j in i..<size - i - 1) {
        target[i][j] = this[size - 1 - j][i]
        target[size - 1 - j][i] = this[size - 1 - i][size - 1 - j]
        target[size - 1 - i][size - 1 - j] = this[j][size - 1 - i]
        target[j][size - 1 - i] = this[i][j]
      }
    }

    return target
  }

  private fun hasPattern(
    x: Int,
    y: Int,
    map: List<List<Char>>
  ): Boolean {
    return MONSTER.mapIndexed { my, row ->
      row.mapIndexed { mx, char ->
        val tx = x + mx
        val ty = y + my
        if (ty !in map.indices || tx !in map[ty].indices) return false
        char != '#' || map[ty][tx] != '.'
      }.all { it }
    }.all { it }
  }

  private fun replacePattern(
    x: Int,
    y: Int,
    map: List<MutableList<Char>>
  ) {
    if (hasPattern(x, y, map)) {
      MONSTER.forEachIndexed { my, row ->
        row.forEachIndexed { mx, char ->
          val tx = x + mx
          val ty = y + my
          if (ty !in map.indices || tx !in map[ty].indices) return
          if (char == '#') {
            map[ty][tx] = '*'
          }
        }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day20)
