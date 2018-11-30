package net.olegg.adventofcode.year2016.day4

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/4">Year 2016, Day 4</a>
 */
class Day4 : DayOf2016(4) {
    companion object {
        val ROOM_PATTERN = "^(.+)-(\\d+)\\[(.+)\\]$".toRegex()
    }

    override fun first(data: String): Any? {
        val rooms = data
                .trim()
                .lines()
                .mapNotNull { line ->
                    ROOM_PATTERN.matchEntire(line)?.let { match ->
                        val (name, id, checksum) = match.destructured
                        Triple(name.replace("-", ""), id.toInt(), checksum)
                    }
                }

        return rooms.filter { room ->
            room.third == room.first
                    .groupBy { it }
                    .mapValues { it.value.size }
                    .toList()
                    .sortedWith(compareBy({ -it.second }, { it.first }))
                    .take(5)
                    .map { it.first }
                    .joinToString(separator = "")
        }.sumBy { it.second }
    }

    override fun second(data: String): Any? {
        val rooms = data
                .trim()
                .lines()
                .mapNotNull { line ->
                    ROOM_PATTERN.matchEntire(line)?.let { match ->
                        val (name, id, _) = match.destructured
                        Pair(name, id.toInt())
                    }
                }

        val a = 'a'.toInt()

        return rooms
                .map { (name, id) ->
                    name.map { char ->
                        if (char == '-') ' ' else ((char.toInt() - a + id) % 26 + a).toChar()
                    }
                    .joinToString(separator = "") to id
                }
                .joinToString(separator = "\n")
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day4::class)
