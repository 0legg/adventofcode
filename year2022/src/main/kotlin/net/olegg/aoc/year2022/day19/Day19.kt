package net.olegg.aoc.year2022.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector4D
import net.olegg.aoc.year2022.DayOf2022
import java.util.PriorityQueue

/**
 * See [Year 2022, Day 19](https://adventofcode.com/2022/day/19)
 */
object Day19 : DayOf2022(19) {
  private val template = ("Blueprint (\\d+): Each ore robot costs (\\d+) ore\\. " +
    "Each clay robot costs (\\d+) ore\\. " +
    "Each obsidian robot costs (\\d+) ore and (\\d+) clay\\. " +
    "Each geode robot costs (\\d+) ore and (\\d+) obsidian\\.").toRegex()
  override fun first(): Any? {
    val blueprints = lines
      .mapNotNull { template.find(it) }
      .map {
        val (number, oreOre, clayOre, obsidianOre, obsidianClay, geodeOre, geodeObsidian) = it.destructured

        Blueprint(
          number = number.toInt(),
          configs = listOf(
            Robot(
              cost = Rocks(
                ore = oreOre.toInt(),
              ).vector,
              produce = Rocks(
                ore = 1,
              ).vector,
            ),
            Robot(
              cost = Rocks(
                ore = clayOre.toInt(),
              ).vector,
              produce = Rocks(
                clay = 1,
              ).vector,
            ),
            Robot(
              cost = Rocks(
                ore = obsidianOre.toInt(),
                clay = obsidianClay.toInt(),
              ).vector,
              produce = Rocks(
                obsidian = 1,
              ).vector,
            ),
            Robot(
              cost = Rocks(
                ore = geodeOre.toInt(),
                obsidian = geodeObsidian.toInt(),
              ).vector,
              produce = Rocks(
                geode = 1,
              ).vector,
            ),
          ),
        )
      }

    return blueprints.sumOf { blueprint ->
      val initialState = State(
        robots = Vector4D(1, 0, 0, 0),
        resources = Vector4D(0, 0, 0, 0),
        time = 24,
      )

      val queue = PriorityQueue<State>(
        compareBy(
          { -it.robots.manhattan() },
          { -it.resources.w },
          { it.time },
        )
      ).apply {
        this += initialState
      }
      val seen = mutableSetOf<State>()
      var best = 0
      var iter = 0L

      while (queue.isNotEmpty()) {
        val curr = queue.remove()
        if (iter++ % 1000000L == 0L) {
          println("${queue.size}, ${curr.time}, $best")
        }
        if (curr in seen) {
          continue
        }
        seen += curr

        val newResources = curr.resources + curr.robots
        if (curr.time == 1) {
          best = maxOf(best, newResources.w)
        } else {
          val newState = State(
            robots = curr.robots,
            resources = newResources,
            time = curr.time - 1,
          )

          if (newState !in seen) {
            val possibleBest = newState.resources.w +
              newState.robots.w * newState.time +
              newState.time * (newState.time - 1) / 2
            if (possibleBest > best) {
              queue.add(newState)
            }
          }
        }

        blueprint.configs.forEach { config ->
          val buildCost = config.cost
          val afterBuild = curr.resources - buildCost
          if (afterBuild.toList().all { it >= 0 }) {
            if (curr.time == 1) {
              best = maxOf(best, afterBuild.w)
            } else {
              val newState = State(
                robots = curr.robots + config.produce,
                resources = afterBuild + curr.robots,
                time = curr.time - 1,
              )
              if (newState !in seen) {
                val possibleBest = newState.resources.w +
                  newState.robots.w * newState.time +
                  newState.time * (newState.time - 1) / 2
                if (possibleBest > best) {
                  queue.add(newState)
                }
              }
            }
          }
        }
      }

      best * blueprint.number
    }
  }

  data class Rocks(
    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val geode: Int = 0,
  ) {
    val list = listOf(ore, clay, obsidian, geode)
    val vector = Vector4D(ore, clay, obsidian, geode)
  }

  data class Robot(
    val cost: Vector4D,
    val produce: Vector4D,
  )

  data class Blueprint(
    val number: Int,
    val configs: List<Robot>,
  )

  data class State(
    val robots: Vector4D,
    val resources: Vector4D,
    val time: Int,
  )
}

fun main() = SomeDay.mainify(Day19)
