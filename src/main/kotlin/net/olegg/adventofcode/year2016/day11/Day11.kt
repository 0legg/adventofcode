package net.olegg.adventofcode.year2016.day11

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/11">Year 2016, Day 11</a>
 */
class Day11 : DayOf2016(11) {
    val generatorPattern = "(\\w+) generator".toRegex()
    val microchipPattern = "(\\w+)-compatible microchip".toRegex()

    override fun first(data: String): String {
        val initial = data.lines().filter { it.isNotBlank() }.map {
            generatorPattern.findAll(it).map { it.value[0] }.toSet() to microchipPattern.findAll(it).map { it.value[0] }.toSet()
        }

        val all = initial.fold(Pair(setOf<Char>(), setOf<Char>())) { accum, floor ->
            (accum.first + floor.first) to (accum.second + floor.second)
        }
        val queue = listOf(Triple(initial, 0, 0)).toMutableList()
        val known = mutableSetOf(initial to 0)

        do {
            val (floors, elevator, steps) = queue.removeAt(0)
            val genMoves = floors[elevator].first.flatMap { a -> floors[elevator].first.map { b -> setOf(a, b) } }.distinct() + listOf(emptySet())
            val chipMoves = floors[elevator].second.flatMap { a -> floors[elevator].second.map { b -> setOf(a, b) } }.distinct() + listOf(emptySet())
            val moves = genMoves.flatMap { gen -> chipMoves.map { chip -> gen to chip } }
                    .filter { it.first.size + it.second.size in 1..2 }
                    .filterNot { it.first.size == 1 && it.second.size == 1 && it.first.first() != it.second.first() }
            val nextFloors = listOf(elevator - 1, elevator + 1).filter { it in floors.indices }

            val after = nextFloors.flatMap { next ->
                moves.map { move ->
                    Triple(
                            floors.mapIndexed { i, floor ->
                                when (i) {
                                    elevator -> (floor.first - move.first) to (floor.second - move.second)
                                    next -> (floor.first + move.first) to (floor.second + move.second)
                                    else -> floor
                                }
                            },
                            next,
                            steps + 1
                    )
                }
            }
                    .distinctBy { it.first }
                    .filterNot { it.first.any {
                        return@any it.first.isNotEmpty() && it.second.isNotEmpty() && (it.second - it.first).isNotEmpty()
                    } }
                    .filterNot { known.contains(it.first to it.second) }

            known.addAll(after.map { it.first to it.second })
            queue.addAll(after)
            if (queue.first().third != steps) {
                println("step ${queue.first().third}, known ${known.size}, queue ${queue.size}")
            }
            val found = after.find { it.first.last() == all } != null
        } while (!found)

        return queue.first { it.first.last() == all }.third.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day11::class)
