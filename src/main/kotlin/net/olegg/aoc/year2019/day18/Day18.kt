package net.olegg.aoc.year2019.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2019.DayOf2019
import java.util.ArrayDeque
import java.util.BitSet
import java.util.PriorityQueue

/**
 * See [Year 2019, Day 18](https://adventofcode.com/2019/day/18)
 */
object Day18 : DayOf2019(18) {
  override fun first(data: String): Any? {
    val map = data
        .trim()
        .lines()
        .map { it.toList() }

    val start = map.find('@') ?: throw IllegalStateException()
    val keys = (('a'..'z') + '@')
        .map { it to (map.find(it) ?: throw IllegalStateException()) }
        .toMap()
    val routes = mutableMapOf<String, Pair<Int, BitSet>>()

    keys.entries.forEach { (key, position) ->
      val queue = ArrayDeque(listOf(Triple(position, 0, BitSet(26))))
      val visited = mutableSetOf(position)

      while (queue.isNotEmpty()) {
        val (curr, steps, doors) = queue.pop()
        Neighbors4.map { it.step + curr }
            .filter { map[it.y][it.x] != '#' }
            .filter { it !in visited }
            .forEach { next ->
              visited += next
              when (val char = map[next.y][next.x]) {
                '.' -> {
                  queue += Triple(next, steps + 1, doors)
                }
                in 'a'..'z' -> {
                  routes["$key$char"] = steps + 1 to doors
                  queue += Triple(next, steps + 1, doors)
                }
                in 'A'..'Z' -> {
                  val newDoors = doors.get(0, 26).apply { set(char - 'A') }
                  queue += Triple(next, steps + 1, newDoors)
                }
              }
            }
      }
    }

    val queue = PriorityQueue<Config>(1000, compareBy({ -it.keys.cardinality() }, { it.steps }))
    queue.add(Config(start))
    val visited = mutableMapOf<Pair<Vector2D, BitSet>, Int>()
    var best = Int.MAX_VALUE

    while (queue.isNotEmpty()) {
      val config = queue.poll()
      if (config.steps >= best) {
        continue
      }

      ('a'..'z')
          .asSequence()
          .filterNot { config.keys[it - 'a'] }
          .filter { next ->
            val routeKey = "${config.char}$next"
            val doors = routes[routeKey]?.second ?: throw IllegalStateException()
            return@filter (0 until 26).none { doors[it] && !config.keys[it] }
          }
          .map { next ->
            val routeKey = "${config.char}$next"
            val route = routes[routeKey] ?: throw IllegalStateException()
            return@map Config(
                position = keys[next] ?: throw IllegalStateException(),
                char = next,
                steps = config.steps + route.first,
                keys = config.keys.get(0, 26).apply { set(next - 'a') }
            )
          }
          .filter { it.steps < best }
          .filter { it.steps < visited.getOrDefault(it.position to it.keys, Int.MAX_VALUE) }
          .forEach {
            visited[it.position to it.keys] = it.steps
            if (it.keys.cardinality() == 26) {
              best = minOf(best, it.steps)
              queue.removeIf { config -> config.steps >= best }
            } else {
              queue.offer(it)
            }
          }
    }

    return best
  }

  private fun <T> List<List<T>>.find(value: T): Vector2D? {
    forEachIndexed { y, row ->
      row.forEachIndexed { x, curr ->
        if (curr == value) {
          return Vector2D(x, y)
        }
      }
    }
    return null
  }

  data class Config(
      val position: Vector2D,
      val char: Char = '@',
      val steps: Int = 0,
      val keys: BitSet = BitSet(26)
  )
}

fun main() = SomeDay.mainify(Day18)
