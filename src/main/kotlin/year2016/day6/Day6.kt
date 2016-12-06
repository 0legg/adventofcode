package year2016.day6

import someday.SomeDay
import year2016.DayOf2016

/**
 * Created by olegg on 12/5/16.
 */
class Day6 : DayOf2016(6) {
    override fun first(): String {
        return data.split("\n")
                .flatMap { it.toCharArray().mapIndexed { i, c -> i to c  } }
                .groupBy { it.first }
                .mapValues { it.value.map { it.second } }
                .mapValues { it.value.groupBy { it } }
                .mapValues { it.value.mapValues { it.value.size } }
                .mapValues { it.value.maxBy { it.value }?.key ?: '?' }
                .toList()
                .sortedBy { it.first }
                .map { it.second }
                .joinToString(separator = "")
    }

    override fun second(): String {
        return data.split("\n")
                .flatMap { it.toCharArray().mapIndexed { i, c -> i to c  } }
                .groupBy { it.first }
                .mapValues { it.value.map { it.second } }
                .mapValues { it.value.groupBy { it } }
                .mapValues { it.value.mapValues { it.value.size } }
                .mapValues { it.value.minBy { it.value }?.key ?: '?' }
                .toList()
                .sortedBy { it.first }
                .map { it.second }
                .joinToString(separator = "")
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day6::class.java)