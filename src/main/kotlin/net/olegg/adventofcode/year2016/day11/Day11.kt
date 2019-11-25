package net.olegg.adventofcode.year2016.day11

import java.util.ArrayDeque
import java.util.BitSet
import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * See [Year 2016, Day 11](https://adventofcode.com/2016/day/11)
 */
class Day11 : DayOf2016(11) {
  companion object {
    private val GEN_PATTERN = "(\\w+) generator".toRegex()
    private val CHIP_PATTERN = "(\\w+)-compatible microchip".toRegex()
  }

  override fun first(data: String): Any? {
    val initial = data
        .trim()
        .lines()
        .map { floor ->
          Pair(
              GEN_PATTERN.findAll(floor).map { it.value[0] }.toSet(),
              CHIP_PATTERN.findAll(floor).map { it.value[0] }.toSet()
          )
        }

    return countSteps(initial)
  }

  override fun second(data: String): Any? {
    val initial = data
        .trim()
        .lines()
        .map { floor ->
          Pair(
              GEN_PATTERN.findAll(floor).map { it.value[0] }.toSet(),
              CHIP_PATTERN.findAll(floor).map { it.value[0] }.toSet()
          )
        }

    val fixed = initial.mapIndexed { i, floor ->
      when (i) {
        0 -> (floor.first + setOf('e', 'd') to floor.second + setOf('e', 'd'))
        else -> floor
      }
    }

    return countSteps(fixed)
  }

  private fun countSteps(input: List<Pair<Set<Char>, Set<Char>>>): Int {
    val types = input.flatMap { it.first + it.second }.distinct().sorted()
    val indexed = input.map { (gen, chip) ->
      Pair(gen.map { types.indexOf(it) }.toSet(), chip.map { types.indexOf(it) }.toSet())
    }
    val bits = (types.size * 2 + 1) * 2
    val initial = compress(indexed, 0, types.size)
    val all = (1 shl bits) - 1
    val queue = ArrayDeque(listOf(initial to 0))
    val known = BitSet(1 shl bits)
    known.set(initial)

    val elevatorMoves = (0..3).map { curr -> listOf(curr - 1, curr + 1).filter { next -> next in 0..3 } }

    do {
      val (floors, elevator, steps) = decompress(queue.poll(), types.size)
      val (gens, chips) = floors[elevator]
      val allMoves = gens.flatMap { a -> gens.map { b -> setOf(a, b) to emptySet<Int>() } } +
          chips.flatMap { a -> chips.map { b -> emptySet<Int>() to setOf(a, b) } } +
          gens.intersect(chips).map { setOf(it) to setOf(it) }
      val moves = allMoves.distinct()
      val nextFloors = elevatorMoves[elevator]

      nextFloors.flatMap { next ->
        moves.map { (genMove, chipMove) ->
          floors.toMutableList().also { fl ->
            fl[elevator] = fl[elevator].copy(fl[elevator].first - genMove, fl[elevator].second - chipMove)
            fl[next] = fl[next].copy(fl[next].first + genMove, fl[next].second + chipMove)
          } to next
        }
      }
          .distinctBy { it.first }
          .filterNot { (floors, _) ->
            floors.any { (gens, chips) ->
              gens.isNotEmpty() && (chips - gens).isNotEmpty()
            }
          }
          .map { compress(it.first, it.second, types.size) }
          .filterNot { state -> known.get(state) }
          .forEach { state ->
            known.set(state)
            queue.offer(state to steps + 1)
          }
    } while (!known.get(all))

    return queue.first { it.first == all }.second
  }

  fun compress(state: List<Pair<Set<Int>, Set<Int>>>, elevator: Int, types: Int): Day11State {
    val data = IntArray(types * 2 + 1)
    data[0] = elevator
    state.forEachIndexed { floor, pair ->
      pair.first.forEach { data[it * 2 + 1] = floor }
      pair.second.forEach { data[it * 2 + 2] = floor }
    }

    return data.foldIndexed(0) { index: Int, acc: Int, i: Int -> acc or (i shl (index * 2)) }
  }

  fun decompress(compressed: Pair<Day11State, Int>, types: Int): Triple<List<Pair<Set<Int>, Set<Int>>>, Int, Int> {
    val decompressed = List(4) { mutableSetOf<Int>() to mutableSetOf<Int>() }
    val elevator = compressed.first and 3
    (0 until types).forEach { index ->
      decompressed[compressed.first[index * 2 + 1]].first += index
      decompressed[compressed.first[index * 2 + 2]].second += index
    }

    return Triple(
        decompressed,
        elevator,
        compressed.second
    )
  }
}

typealias Day11State = Int

operator fun Day11State.get(index: Int) = (this shr (index * 2)) and 3

fun main() = SomeDay.mainify(Day11::class)
