package net.olegg.aoc.year2021.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2021.DayOf2021
import java.util.PriorityQueue
import kotlin.math.abs

/**
 * See [Year 2021, Day 23](https://adventofcode.com/2021/day/23)
 */
object Day23 : DayOf2021(23) {
  private val cost = mapOf(
    'A' to 1,
    'B' to 10,
    'C' to 100,
    'D' to 1000,
  )

  override fun first(data: String): Any? {
    return solve(data.trim().lines())
  }

  override fun second(data: String): Any? {
    val base = data.trim().lines()
    val insert = """
      |  #D#C#B#A#
      |  #D#B#A#C#
    """.trimMargin().lines()

    return solve(
      base.take(3) + insert + base.drop(3)
    )
  }

  private fun solve(data: List<String>): Int {
    val points = data
      .flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          if (c in 'A'..'D' || c == '.') Vector2D(x, y) to c else null
        }
      }

    val rawStacks = points.filter { it.second in 'A'..'D' }
      .groupBy { it.first.x }
      .toList()
      .zip('A'..'D') { (x, points), char ->
        Triple(char, x, points)
      }

    val depth = rawStacks.first().third.size

    val stacks = rawStacks.map {
      Node(
        x = it.second,
        char = it.first,
      )
    }

    val startStacks = stacks.zip(rawStacks) { stack, rawStack ->
      stack to rawStack.third.map { it.second }
    }.toMap()

    val entries = stacks.map { stack -> stack.x }

    val spots = points.filter { it.second == '.' }
      .map { it.first.x }
      .filterNot { it in entries }
      .map { Node(it) }

    val exitLinks = stacks.associateWith { stack ->
      spots.map { spot ->
        val range = when {
          stack.x < spot.x -> (stack.x + 1)..(spot.x - 1)
          stack.x > spot.x -> (spot.x + 1)..(stack.x - 1)
          else -> IntRange.EMPTY
        }
        Link(
          to = spot,
          blocking = spots.filter { it.x in range },
          costX = abs(stack.x - spot.x),
        )
      }
    }

    val enterFromSpotLinks = spots.associateWith { spot ->
      stacks.map { stack ->
        val range = when {
          spot.x < stack.x -> (spot.x + 1)..(stack.x - 1)
          spot.x > stack.x -> (stack.x + 1)..(spot.x - 1)
          else -> IntRange.EMPTY
        }
        Link(
          to = stack,
          blocking = spots.filter { it.x in range },
          costX = abs(spot.x - stack.x),
        )
      }
    }

    val enterFromStackLinks = stacks.associateWith { fromStack ->
      stacks.filterNot { it == fromStack }
        .map { stack ->
          val range = when {
            fromStack.x < stack.x -> (fromStack.x + 1)..(stack.x - 1)
            fromStack.x > stack.x -> (stack.x + 1)..(fromStack.x - 1)
            else -> IntRange.EMPTY
          }
          Link(
            to = stack,
            blocking = spots.filter { it.x in range },
            costX = abs(fromStack.x - stack.x),
          )
        }
    }

    val startWorld = World(
      spots = emptyMap(),
      stacks = startStacks,
    )

    val queue = PriorityQueue<Triple<World, Int, Int>>(1_000_000, compareBy { it.second + it.third })
    queue += Triple(startWorld, 0, Int.MAX_VALUE)
    val seen = mutableSetOf<World>()

    while (queue.isNotEmpty()) {
      val (world, score, _) = queue.remove()
      if (world in seen) {
        continue
      } else {
        seen += world
      }

      if (world.spots.isEmpty() && world.stacks.all { (stack, chars) -> chars.all { it == stack.char } }) {
        return score
      }

      val canFill = world.stacks
        .filter { it.value.all { char -> char == it.key.char } }
        .filter { it.value.size < depth }

      val canExit = world.stacks
        .filter { it.value.isNotEmpty() }
        .filter { it.key !in canFill }
        .filter { it.value.any { char -> char != it.key.char } }

      val exiting = canExit.flatMap { (stack, stackData) ->
        val targets = exitLinks[stack].orEmpty()
          .filter { it.to !in world.spots }
          .filter { it.blocking.none { blocking -> blocking in world.spots } }

        val costY = (depth - stackData.size + 1)

        targets.map { target ->
          World(
            spots = world.spots + (target.to to stackData.first()),
            stacks = world.stacks.mapValues { (key, value) ->
              if (key == stack) value.drop(1) else value
            }
          ) to score + (target.costX + costY) * cost.getValue(stackData.first())
        }
      }

      val enteringFromStacks = canExit.flatMap { (stack, stackData) ->
        val targets = enterFromStackLinks[stack].orEmpty()
          .filter { it.to in canFill }
          .filter { it.to.char == stackData.first() }
          .filter { it.blocking.none { blocking -> blocking in world.spots } }

        val costFromY = (depth - stackData.size + 1)

        targets.map { target ->
          val costToY = (depth - world.stacks[target.to].orEmpty().size)

          World(
            spots = world.spots,
            stacks = world.stacks.mapValues { (key, value) ->
              when (key) {
                stack -> value.drop(1)
                target.to -> stackData.take(1) + value
                else -> value
              }
            }
          ) to score + (target.costX + costFromY + costToY) * cost.getValue(stackData.first())
        }
      }

      val enteringFromSpots = world.spots.flatMap { (spot, char) ->
        val targets = enterFromSpotLinks[spot].orEmpty()
          .filter { it.to in canFill }
          .filter { it.to.char == char }
          .filter { it.blocking.none { blocking -> blocking in world.spots } }

        targets.map { target ->
          val costToY = (depth - world.stacks[target.to].orEmpty().size)

          World(
            spots = world.spots - spot,
            stacks = world.stacks.mapValues { (key, value) ->
              when (key) {
                target.to -> listOf(char) + value
                else -> value
              }
            }
          ) to score + (target.costX + costToY) * cost.getValue(char)
        }
      }

      val toAdd = (exiting + enteringFromStacks + enteringFromSpots)
        .filter { it.first !in seen }
        .map { (world, score) ->
          val spotCosts = world.spots.map { (node, char) ->
            ((stacks.first { it.char == char }.x - node.x) + depth) * cost.getValue(char)
          }.sum()
          val stackCosts = world.stacks.map { (node, stack) ->
            stack.filter { it != node.char }.sumOf { cost.getValue(it) } * 2 * depth
          }.sum()
          Triple(world, score, (spotCosts + stackCosts) / 4)
        }

      queue.addAll(toAdd)
    }

    return 0
  }

  data class World(
    val spots: Map<Node, Char>,
    val stacks: Map<Node, List<Char>>,
  )

  data class Link(
    val to: Node,
    val blocking: List<Node>,
    val costX: Int,
  )

  data class Node(
    val x: Int,
    val char: Char = '.',
  )
}

fun main() = SomeDay.mainify(Day23)
