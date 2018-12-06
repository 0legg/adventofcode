package net.olegg.adventofcode.year2016.day11

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016
import java.util.BitSet

/**
 * @see <a href="http://adventofcode.com/2016/day/11">Year 2016, Day 11</a>
 */
class Day11 : DayOf2016(11) {
    val generatorPattern = "(\\w+) generator".toRegex()
    val microchipPattern = "(\\w+)-compatible microchip".toRegex()

    override fun first(data: String): Any? {
        val initial = data.lines().filter { it.isNotBlank() }.map {
            generatorPattern.findAll(it).map { it.value[0] }.toSet() to microchipPattern.findAll(it).map { it.value[0] }.toSet()
        }

        return countSteps(initial)
    }

    override fun second(data: String): Any? {
        val initial = data.lines().filter { it.isNotBlank() }.map {
            generatorPattern.findAll(it).map { it.value[0] }.toSet() to microchipPattern.findAll(it).map { it.value[0] }.toSet()
        }
                .mapIndexed { i, floor ->
                    when (i) {
                        0 -> (floor.first + setOf('e', 'd') to floor.second + setOf('e', 'd'))
                        else -> floor
                    }
                }

        return countSteps(initial)
    }

    fun countSteps(input: List<Pair<Set<Char>, Set<Char>>>): Int {
        val types = input.flatMap { it.first + it.second }.distinct().sorted()
        val bits = (types.size * 2 + 1) * 2
        val initial = compress(input, 0, types)
        val all = (1 shl bits) - 1
        val queue = listOf(initial to 0).toMutableList()
        val known = BitSet(1 shl bits)
        known.set(initial)

        do {
            val (floors, elevator, steps) = decompress(queue.removeAt(0), types)
            val genMoves = floors[elevator].first.flatMap { a -> floors[elevator].first.map { b -> setOf(a, b) } }.distinct() + listOf(emptySet())
            val chipMoves = floors[elevator].second.flatMap { a -> floors[elevator].second.map { b -> setOf(a, b) } }.distinct() + listOf(emptySet())
            val moves = genMoves.flatMap { gen -> chipMoves.map { chip -> gen to chip } }
                    .filter { it.first.size + it.second.size in 1..2 }
                    .filterNot { it.first.size == 1 && it.second.size == 1 && it.first.first() != it.second.first() }
            val nextFloors = listOf(elevator - 1, elevator + 1).filter { it in 0..3 }

            nextFloors.flatMap { next ->
                moves.map { move ->
                    floors.mapIndexed { i, floor ->
                        when (i) {
                            elevator -> (floor.first - move.first) to (floor.second - move.second)
                            next -> (floor.first + move.first) to (floor.second + move.second)
                            else -> floor
                        }
                    } to next
                }
            }
                    .distinctBy { it.first }
                    .filterNot {
                        it.first.any {
                            it.first.isNotEmpty() && it.second.isNotEmpty() && (it.second - it.first).isNotEmpty()
                        }
                    }
                    .map { compress(it.first, it.second, types) }
                    .filterNot { known.get(it) }
                    .forEach {
                        known.set(it)
                        queue.add(it to steps + 1)
                    }
        } while (!known.get(all))

        return queue.first { it.first == all }.second
    }

    fun compress(state: List<Pair<Set<Char>, Set<Char>>>, elevator: Int, types: List<Char>): Day11State {
        val floors = IntArray(types.size * 2 + 1)
        floors[0] = elevator
        state.forEachIndexed { floor, pair ->
            pair.first.forEach {
                floors[types.indexOf(it) * 2 + 1] = floor
            }
            pair.second.forEach {
                floors[types.indexOf(it) * 2 + 2] = floor
            }
        }

        return floors.foldIndexed(0) { index: Int, acc: Int, i: Int -> acc or (i shl (index * 2)) }
    }

    fun decompress(compressed: Pair<Day11State, Int>, types: List<Char>): Triple<List<Pair<Set<Char>, Set<Char>>>, Int, Int> {
        val decompressed = listOf(
                mutableSetOf<Char>() to mutableSetOf<Char>(),
                mutableSetOf<Char>() to mutableSetOf<Char>(),
                mutableSetOf<Char>() to mutableSetOf<Char>(),
                mutableSetOf<Char>() to mutableSetOf<Char>()
        )

        types.forEachIndexed { index, char ->
            decompressed[compressed.first[index * 2 + 1]].first += char
            decompressed[compressed.first[index * 2 + 2]].second += char
        }

        return Triple(
                decompressed,
                compressed.first[0],
                compressed.second
        )
    }
}

typealias Day11State = Int
operator fun Day11State.get(index: Int) = (this shr (index * 2)) and 3

fun main(args: Array<String>) = SomeDay.mainify(Day11::class)
