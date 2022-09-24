package net.olegg.aoc.year2018.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 15](https://adventofcode.com/2018/day/15)
 */
object Day15 : DayOf2018(15) {
  override fun first(data: String): Any? {
    val map = data
      .trim()
      .lines()
      .map { it.toList() }

    var characters = map
      .flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          if (c in listOf('E', 'G')) Character(Vector2D(x, y), c, 3, 200) else null
        }
      }
      .toMutableList()

    val walls = map
      .map { row ->
        row.map { if (it == '#') -1 else Int.MAX_VALUE }
      }

    (0..1_000_000_000).forEach { round ->
      characters = characters
        .filter { it.hit > 0 }
        .sortedWith(compareBy({ it.pos.y }, { it.pos.x }))
        .toMutableList()

      for (character in characters) {
        if (character.hit <= 0) {
          continue
        }

        if (characters.none { it.hit > 0 && it.type != character.type }) {
          return round * characters.filter { it.hit > 0 && it.type == character.type }.sumOf { it.hit }
        }

        val targets = characters.filter { it.hit > 0 && it.type != character.type }
        val currMap = walls.map { it.toMutableList() }

        characters
          .filter { it.hit > 0 }
          .forEach { other -> currMap[other.pos] = -1 }

        currMap[character.pos] = Int.MAX_VALUE

        val adjacent = targets
          .flatMap { target ->
            Neighbors4.map { target.pos + it.step }
          }
          .distinct()
          .filter { cell -> currMap[cell] != -1 }

        if (adjacent.isEmpty()) {
          continue
        }

        val queue = ArrayDeque(listOf(character.pos to 0))
        while (queue.isNotEmpty()) {
          val (pos, step) = queue.removeFirst()
          if (currMap[pos]!! > step) {
            currMap[pos] = step
            queue += Neighbors4
              .map { pos + it.step }
              .filter { currMap[it] != -1 }
              .filter { currMap[it]!! > step + 1 }
              .map { it to step + 1 }
          }
        }

        val minDistance = adjacent.minOf { currMap[it] ?: Int.MAX_VALUE }

        if (minDistance == Int.MAX_VALUE) {
          continue
        }

        if (minDistance > 0) {
          val nearest = adjacent
            .filter { currMap[it] == minDistance }
            .sortedWith(compareBy({ it.y }, { it.x }))
            .first()

          val moves = mutableListOf<Vector2D>()

          val revQueue = ArrayDeque(listOf(nearest to minDistance))
          while (revQueue.isNotEmpty()) {
            val (pos, step) = revQueue.removeFirst()
            if (step == 1) {
              moves += pos
            } else {
              revQueue += Neighbors4
                .map { pos + it.step }
                .filter { currMap[it] != -1 }
                .filter { currMap[it] == step - 1 }
                .map { (it to step - 1) }
            }
          }

          val move = moves.minWithOrNull(compareBy({ it.y }, { it.x }))

          move?.let {
            character.pos = move
          }
        }

        val canHit = Neighbors4.map { character.pos + it.step }

        val target = targets.filter { it.pos in canHit }
          .minWithOrNull(compareBy({ it.hit }, { it.pos.y }, { it.pos.x }))

        target?.let {
          it.hit -= character.attack
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
              'E' -> Character(Vector2D(x, y), c, elvenHit, 200)
              'G' -> Character(Vector2D(x, y), c, 3, 200)
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
          .sortedWith(compareBy({ it.pos.y }, { it.pos.x }))
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
              return round * characters.filter { it.hit > 0 && it.type == character.type }.sumOf { it.hit }
            }
          }

          val targets = characters.filter { it.hit > 0 && it.type != character.type }
          val currMap = walls.map { it.toMutableList() }

          characters
            .filter { it.hit > 0 }
            .forEach { other -> currMap[other.pos] = -1 }

          currMap[character.pos] = Int.MAX_VALUE

          val adjacent = targets
            .flatMap { target ->
              Neighbors4.map { target.pos + it.step }
            }
            .distinct()
            .filter { currMap[it] != -1 }

          if (adjacent.isEmpty()) {
            continue
          }

          val queue = ArrayDeque(listOf(character.pos to 0))
          while (queue.isNotEmpty()) {
            val (pos, step) = queue.removeFirst()
            if (currMap[pos]!! > step) {
              currMap[pos] = step
              queue += Neighbors4
                .map { pos + it.step }
                .filter { currMap[it] != -1 }
                .filter { currMap[it]!! > step + 1 }
                .map { (it to step + 1) }
            }
          }

          val minDistance = adjacent.minOf { currMap[it] ?: Int.MAX_VALUE }

          if (minDistance == Int.MAX_VALUE) {
            continue
          }

          if (minDistance > 0) {
            val nearest = adjacent
              .filter { currMap[it] == minDistance }
              .sortedWith(compareBy({ it.y }, { it.x }))
              .first()

            val moves = mutableListOf<Vector2D>()

            val revQueue = ArrayDeque(listOf(nearest to minDistance))
            while (revQueue.isNotEmpty()) {
              val (pos, step) = revQueue.removeFirst()
              if (step == 1) {
                moves += pos
              } else {
                revQueue += Neighbors4
                  .map { pos + it.step }
                  .filter { currMap[it] != -1 }
                  .filter { currMap[it] == step - 1 }
                  .map { (it to step - 1) }
              }
            }

            val move = moves.minWithOrNull(compareBy({ it.y }, { it.x }))

            move?.let {
              character.pos = it
            }
          }

          val canHit = Neighbors4.map { character.pos + it.step }

          val target = targets.filter { it.pos in canHit }
            .minWithOrNull(compareBy({ it.hit }, { it.pos.y }, { it.pos.x }))

          target?.let {
            it.hit -= character.attack
          }
        }
      }
      return@first false
    }
  }

  data class Character(
    var pos: Vector2D,
    val type: Char,
    var attack: Int,
    var hit: Int
  )
}

fun main() = SomeDay.mainify(Day15)
