package net.olegg.aoc.year2019.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2019.DayOf2019
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

    val keys = (('a'..'z') + '@')
        .map { it to (map.find(it) ?: throw IllegalStateException()) }
        .toMap()
    val routes = mutableMapOf<Char, MutableMap<Char, Pair<Int, BitSet>>>()

    keys.entries.forEach { (key, position) ->
      val queue = ArrayDeque(listOf(Triple(position, 0, BitSet(26))))
      val visited = mutableSetOf(position)

      while (queue.isNotEmpty()) {
        val (curr, steps, doors) = queue.removeFirst()
        Neighbors4.map { it.step + curr }
            .filter { map[it] != '#' }
            .filter { it !in visited }
            .forEach { next ->
              visited += next
              when (val char = map[next]!!) {
                '.', '#' -> {
                  queue += Triple(next, steps + 1, doors)
                }
                in 'a'..'z' -> {
                  routes.getOrPut(key) { mutableMapOf() }[char] = steps + 1 to doors
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
    queue.add(Config())
    val visited = mutableMapOf<Pair<Char, BitSet>, Int>()
    var best = Int.MAX_VALUE

    while (queue.isNotEmpty()) {
      val config = queue.poll()
      if (config.steps >= best) {
        continue
      }

      routes[config.char]
          .orEmpty()
          .asSequence()
          .filterNot { config.keys[it.key - 'a'] }
          .filter { (_, route) ->
            val doors = route.second
            return@filter (0 until 26).none { doors[it] && !config.keys[it] }
          }
          .map { (next, route) ->
            return@map Config(
                char = next,
                steps = config.steps + route.first,
                keys = config.keys.get(0, 26).apply { set(next - 'a') }
            )
          }
          .filter { it.steps < best }
          .filter { it.steps < visited.getOrDefault(it.char to it.keys, Int.MAX_VALUE) }
          .forEach {
            visited[it.char to it.keys] = it.steps
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

  override fun second(data: String): Any? {
    val map = data
        .trim()
        .lines()
        .map { it.toMutableList() }
    val start = map.find('@') ?: throw IllegalStateException()
    (-1..1).forEach { y ->
      (-1..1).forEach { x ->
        map[start + Vector2D(x, y)] = '#'
      }
    }

    map[start + Vector2D(-1, -1)] = '@'
    map[start + Vector2D(-1, 1)] = '$'
    map[start + Vector2D(1, -1)] = '%'
    map[start + Vector2D(1, 1)] = '&'
    val bots = "@$%&".toList()

    val keys = (('a'..'z') + bots)
        .map { it to (map.find(it) ?: throw IllegalStateException()) }
        .toMap()
    val routes = mutableMapOf<Char, MutableMap<Char, Pair<Int, BitSet>>>()

    keys.entries.forEach { (key, position) ->
      val queue = ArrayDeque(listOf(Triple(position, 0, BitSet(26))))
      val visited = mutableSetOf(position)

      while (queue.isNotEmpty()) {
        val (curr, steps, doors) = queue.removeFirst()
        Neighbors4.map { it.step + curr }
            .filter { map[it] != '#' }
            .filter { it !in visited }
            .forEach { next ->
              visited += next
              when (val char = map[next]!!) {
                '.', in bots -> {
                  queue += Triple(next, steps + 1, doors)
                }
                in 'a'..'z' -> {
                  routes.getOrPut(key) { mutableMapOf() }[char] = steps + 1 to doors
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

    val queue = PriorityQueue<MultiConfig>(1000, compareBy({ -it.keys.cardinality() }, { it.steps }))
    val visited = mutableMapOf<Pair<String, BitSet>, Int>()
    queue.add(MultiConfig())
    var best = Int.MAX_VALUE

    while (queue.isNotEmpty()) {
      val config = queue.poll()
      if (config.steps >= best) {
        continue
      }

      config.bots
          .toList()
          .flatMap { key -> routes[key].orEmpty().map { Triple(key, it.key, it.value) } }
          .asSequence()
          .filterNot { config.keys[it.second - 'a'] }
          .filter { (_, _, route) ->
            val doors = route.second
            return@filter (0 until 26).none { doors[it] && !config.keys[it] }
          }
          .map { (curr, next, route) ->
            return@map MultiConfig(
                bots = config.bots.replace(curr, next),
                steps = config.steps + route.first,
                keys = config.keys.get(0, 26).apply { set(next - 'a') }
            )
          }
          .filter { it.steps < best }
          .filter { it.steps < visited.getOrDefault(it.bots to it.keys, Int.MAX_VALUE) }
          .forEach {
            visited[it.bots to it.keys] = it.steps
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

  data class Config(
      val char: Char = '@',
      val steps: Int = 0,
      val keys: BitSet = BitSet(26)
  )

  data class MultiConfig(
      val bots: String = "@$%&",
      val steps: Int = 0,
      val keys: BitSet = BitSet(26)
  )
}

fun main() = SomeDay.mainify(Day18)
