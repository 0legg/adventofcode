package net.olegg.aoc.year2022.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 8](https://adventofcode.com/2022/day/8)
 */
object Day8 : DayOf2022(8) {
  override fun first(): Any? {
    val trees = matrix.map { line ->
      line.map {
        Tree(
          height = it.digitToInt()
        )
      }
    }

    trees.forEach { row ->
      row.fold(-1) { height, tree ->
        tree.left = tree.height > height
        maxOf(tree.height, height)
      }

      row.asReversed().fold(-1) { height, tree ->
        tree.right = tree.height > height
        maxOf(tree.height, height)
      }
    }

    trees.first().indices.forEach { column ->
      trees.indices.fold(-1) { height, row ->
        val tree = trees[row][column]
        tree.up = tree.height > height
        maxOf(tree.height, height)
      }
    }

    trees.first().indices.forEach { column ->
      trees.indices.reversed().fold(-1) { height, row ->
        val tree = trees[row][column]
        tree.down = tree.height > height
        maxOf(tree.height, height)
      }
    }

    return trees.sumOf { row ->
      row.count { it.visible }
    }
  }

  override fun second(): Any? {
    val trees = matrix.map { line ->
      line.map {
        Tree(
          height = it.digitToInt()
        )
      }
    }

    val scenicScores = trees.mapIndexed { y, row ->
      row.mapIndexed { x, tree ->
        val start = Vector2D(x, y)

        Neighbors4
          .map { dir ->
            val line = generateSequence(start) { it + dir.step }
              .drop(1)
              .takeWhile { trees.fit(it) }
              .mapNotNull { trees[it] }
              .toList()
            val distance = line.indexOfFirst { it.height >= tree.height }
            if (distance == -1) line.size else distance + 1
          }
          .map { it.toLong() }
          .reduce(Long::times)
      }
    }

    return scenicScores.maxOf { it.max() }
  }

  data class Tree(
    val height: Int,
    var left: Boolean = false,
    var right: Boolean = false,
    var up: Boolean = false,
    var down: Boolean = false,
  ) {
    val visible: Boolean
      get() = left || right || up || down
  }
}

fun main() = SomeDay.mainify(Day8)
