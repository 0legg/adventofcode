package day8

import someday.SomeDay

/**
 * Created by olegg on 12/19/15.
 */
class Day8: SomeDay(8) {
    val strings = data.lines()
    override fun first(): String {
        return (strings.sumBy { it.length } - strings.sumBy {
            it
                    .replace("^\"".toRegex(), "")
                    .replace("\"$".toRegex(), "")
                    .replace("\\\"", "#")
                    .replace("\\\\", "#")
                    .replace("\\\\x[0-9a-f]{2}".toRegex(), "#")
                    .length
        }).toString()
    }
}

fun main(args: Array<String>) {
    val day = Day8()
    println(day.first())
}