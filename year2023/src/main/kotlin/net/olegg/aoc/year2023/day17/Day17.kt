package net.olegg.aoc.year2023.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2023.DayOf2023
import java.util.PriorityQueue

/**
 * See [Year 2023, Day 17](https://adventofcode.com/2023/day/17)
 */
object Day17 : DayOf2023(17) {
  private val FINISH = Vector2D(
    x = matrix.last().lastIndex,
    y = matrix.lastIndex,
  )

  override fun first(): Any? {
    val seen = mutableSetOf<Config>()

    val queue = PriorityQueue<Pair<Config, Int>>(
      compareBy(
        { it.second },
        { -it.first.pos.manhattan() }
      )
    )
    queue.add(Config(Vector2D(), Directions.R, 0) to 0)

    while (queue.isNotEmpty()) {
      val (config, score) = queue.remove()
      if (config in seen) {
        continue
      }
      seen += config
      if (config.pos == FINISH) {
        return score
      }

      val forward = config.pos + config.dir.step
      if (config.count < 3 && matrix.fit(forward)) {
        queue.add(
          Config(
            pos = forward,
            dir = config.dir,
            count = config.count + 1,
          ) to (score + matrix[forward]!!.digitToInt())
        )
      }

      listOfNotNull(Directions.CW[config.dir], Directions.CCW[config.dir])
        .filter { matrix.fit(config.pos + it.step) }
        .forEach {
          val next = config.pos + it.step
          queue.add(
            Config(
              pos = next,
              dir = it,
              count = 1,
            ) to (score + matrix[next]!!.digitToInt())
          )
        }
    }

    return null
  }

  data class Config(
    val pos: Vector2D,
    val dir: Directions,
    val count: Int,
  )
}

fun main() = SomeDay.mainify(Day17)
