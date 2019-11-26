package net.olegg.aoc.year2018.day15

import java.util.ArrayDeque
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 15](https://adventofcode.com/2018/day/15)
 */
object Day15 : DayOf2018(15) {
  private val MOVES = listOf(
      -1 to 0,
      1 to 0,
      0 to -1,
      0 to 1
  )

  override fun first(data: String): Any? {
    val map = data
        .trim()
        .lines()
        .map { it.toList() }

    var characters = map
        .mapIndexed { y, row ->
          row.mapIndexedNotNull { x, c ->
            if (c in listOf('E', 'G')) Character(x, y, c, 3, 200) else null
          }
        }
        .flatten()
        .toMutableList()

    val walls = map
        .map { row ->
          row.map { if (it == '#') -1 else Int.MAX_VALUE }
        }

    (0..1_000_000_000).forEach { round ->
      characters = characters
          .filter { it.hit > 0 }
          .sortedWith(compareBy({ it.y }, { it.x }))
          .toMutableList()

      for (character in characters) {
        if (character.hit <= 0) {
          continue
        }

        if (characters.none { it.hit > 0 && it.type != character.type }) {
          return round * characters.filter { it.hit > 0 && it.type == character.type }.sumBy { it.hit }
        }

        val targets = characters.filter { it.hit > 0 && it.type != character.type }
        val currMap = walls.map { it.toMutableList() }

        characters
            .filter { it.hit > 0 }
            .forEach { other -> currMap[other.y][other.x] = -1 }

        currMap[character.y][character.x] = Int.MAX_VALUE

        val adjacent = targets
            .flatMap { target ->
              MOVES.map { target.x + it.first to target.y + it.second }
            }
            .distinct()
            .filter { cell -> currMap[cell.second][cell.first] != -1 }

        if (adjacent.isEmpty()) {
          continue
        }

        val queue = ArrayDeque(listOf(Triple(character.x, character.y, 0)))
        while (queue.isNotEmpty()) {
          val (x, y, step) = queue.poll()
          if (currMap[y][x] > step) {
            currMap[y][x] = step
            MOVES
                .map { x + it.first to y + it.second }
                .filter { (cx, cy) -> currMap[cy][cx] != -1 }
                .filter { (cx, cy) -> currMap[cy][cx] > step + 1 }
                .forEach { (cx, cy) -> queue.offer(Triple(cx, cy, step + 1)) }
          }
        }

        val minDistance = adjacent
            .map { (x, y) -> currMap[y][x] }
            .min() ?: Int.MAX_VALUE

        if (minDistance == Int.MAX_VALUE) {
          continue
        }

        if (minDistance > 0) {
          val nearest = adjacent
              .filter { (x, y) -> currMap[y][x] == minDistance }
              .sortedWith(compareBy({ it.second }, { it.first }))
              .first()

          val moves = mutableListOf<Pair<Int, Int>>()

          val revQueue = ArrayDeque(listOf(Triple(nearest.first, nearest.second, minDistance)))
          while (revQueue.isNotEmpty()) {
            val (x, y, step) = revQueue.poll()
            if (step == 1) {
              moves += (x to y)
            } else {
              MOVES
                  .map { x + it.first to y + it.second }
                  .filter { (cx, cy) -> currMap[cy][cx] != -1 }
                  .filter { (cx, cy) -> currMap[cy][cx] == step - 1 }
                  .forEach { (cx, cy) -> revQueue.offer(Triple(cx, cy, step - 1)) }
            }
          }

          val move = moves
              .sortedWith(compareBy({ it.second }, { it.first }))
              .first()

          character.x = move.first
          character.y = move.second
        }

        val canHit = MOVES.map { character.x + it.first to character.y + it.second }

        val targetsCanHit = targets.filter { (it.x to it.y) in canHit }

        if (targetsCanHit.isNotEmpty()) {
          val target = targetsCanHit
              .sortedWith(compareBy({ it.hit }, { it.y }, { it.x }))
              .first()

          target.hit -= character.attack
        }
      }
    }

    return null
  }

  override fun second(data: String): Any? {
    val map = data
        .trim()
        .lines()
        .map { it.toList() }

    val walls = map
        .map { row ->
          row.map { if (it == '#') -1 else Int.MAX_VALUE }
        }

    return (3..200).first { elvenHit ->
      var characters = map
          .mapIndexed { y, row ->
            row.mapIndexedNotNull { x, c ->
              when (c) {
                'E' -> Character(x, y, c, elvenHit, 200)
                'G' -> Character(x, y, c, 3, 200)
                else -> null
              }
            }
          }
          .flatten()
          .toMutableList()

      val elves = characters.count { it.type == 'E' }

      (0..1_000_000_000).forEach { round ->
        characters = characters
            .filter { it.hit > 0 }
            .sortedWith(compareBy({ it.y }, { it.x }))
            .toMutableList()

        if (characters.count { it.hit > 0 && it.type == 'E' } != elves) {
          return@first false
        }

        for (character in characters) {
          if (character.hit <= 0) {
            if (character.type == 'E') {
              return@first false
            }
            continue
          }

          if (characters.none { it.hit > 0 && it.type != character.type }) {
            if (characters.count { it.hit > 0 && it.type == 'E' } == elves) {
              return round * characters.filter { it.hit > 0 && it.type == character.type }.sumBy { it.hit }
            }
          }

          val targets = characters.filter { it.hit > 0 && it.type != character.type }
          val currMap = walls.map { it.toMutableList() }

          characters
              .filter { it.hit > 0 }
              .forEach { other -> currMap[other.y][other.x] = -1 }

          currMap[character.y][character.x] = Int.MAX_VALUE

          val adjacent = targets
              .flatMap { target ->
                MOVES.map { target.x + it.first to target.y + it.second }
              }
              .distinct()
              .filter { cell -> currMap[cell.second][cell.first] != -1 }

          if (adjacent.isEmpty()) {
            continue
          }

          val queue = ArrayDeque(listOf(Triple(character.x, character.y, 0)))
          while (queue.isNotEmpty()) {
            val (x, y, step) = queue.poll()
            if (currMap[y][x] > step) {
              currMap[y][x] = step
              MOVES
                  .map { x + it.first to y + it.second }
                  .filter { (cx, cy) -> currMap[cy][cx] != -1 }
                  .filter { (cx, cy) -> currMap[cy][cx] > step + 1 }
                  .forEach { (cx, cy) -> queue.offer(Triple(cx, cy, step + 1)) }
            }
          }

          val minDistance = adjacent
              .map { (x, y) -> currMap[y][x] }
              .min() ?: Int.MAX_VALUE

          if (minDistance == Int.MAX_VALUE) {
            continue
          }

          if (minDistance > 0) {
            val nearest = adjacent
                .filter { (x, y) -> currMap[y][x] == minDistance }
                .sortedWith(compareBy({ it.second }, { it.first }))
                .first()

            val moves = mutableListOf<Pair<Int, Int>>()

            val revQueue = ArrayDeque(listOf(Triple(nearest.first, nearest.second, minDistance)))
            while (revQueue.isNotEmpty()) {
              val (x, y, step) = revQueue.poll()
              if (step == 1) {
                moves += (x to y)
              } else {
                MOVES
                    .map { x + it.first to y + it.second }
                    .filter { (cx, cy) -> currMap[cy][cx] != -1 }
                    .filter { (cx, cy) -> currMap[cy][cx] == step - 1 }
                    .forEach { (cx, cy) -> revQueue.offer(Triple(cx, cy, step - 1)) }
              }
            }

            val move = moves
                .sortedWith(compareBy({ it.second }, { it.first }))
                .first()

            character.x = move.first
            character.y = move.second
          }

          val canHit = MOVES.map { character.x + it.first to character.y + it.second }

          val targetsCanHit = targets.filter { (it.x to it.y) in canHit }

          if (targetsCanHit.isNotEmpty()) {
            val target = targetsCanHit
                .sortedWith(compareBy({ it.hit }, { it.y }, { it.x }))
                .first()

            target.hit -= character.attack
          }
        }
      }
      return@first false
    }
  }

  data class Character(
      var x: Int,
      var y: Int,
      val type: Char,
      var attack: Int,
      var hit: Int
  )
}

fun main() = SomeDay.mainify(Day15)
