package day11

import someday.SomeDay
import utils.series

/**
 * Created by olegg on 12/19/15.
 */
class Day11: SomeDay(11) {
    fun passwordList(password: String) = sequence(password) {
        it.toList().foldRight(Pair("", true)) { value, acc ->
            Pair(
                    (if (!acc.second) value else when (value) {
                        'z' -> 'a'
                        else -> value + 1
                    }) + acc.first,
                    acc.second && value == 'z'
            )
        }.first
    }

    fun password(password: String): String {
        return passwordList(password).drop(1)
                .filterNot { string -> "iol".any { string.contains(it) } }
                .filter { string -> ('a'..'x').map { String(charArrayOf(it, it + 1, it + 2)) }.any { string.contains(it) } }
                .filter { it.toList().series().filter { it.size > 1 }.flatMap { it }.joinToString(separator = "").length > 3 }
                .first()
    }

    override fun first(): String {
        return password(data)
    }

    override fun second(): String {
        return password(password(data))
    }
}

fun main(args: Array<String>) {
    val day = Day11()
    println(day.first())
    println(day.second())
}