package day5

import someday.SomeDay
import utils.scan

/**
 * Created by olegg on 12/18/15.
 */
class Day5: SomeDay(5) {
    val strings = data.split('\n')
    override fun first(): String {
        return strings.filter { it.count { it in "aeiou" } >= 3 }
                .filterNot { string -> listOf("ab", "cd", "pq", "xy").any { string.contains(it) } }
                .filter { it.toList().scan(Pair(' ', ' ')) { acc, value -> Pair(acc.second, value) }.any { it.first == it.second } }
                .size.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day5()
    println(day.first())
}