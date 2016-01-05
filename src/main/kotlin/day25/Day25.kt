package day25

import someday.SomeDay

/**
 * Created by olegg on 1/4/16.
 */
class Day25: SomeDay(25) {
    override fun first(): String {
        val matcher = ".*\\b(\\d+)\\b.*\\b(\\d+)\\b".toPattern().matcher(data)
        if (matcher.find()) {
            val row = matcher.group(1).toInt()
            val column = matcher.group(2).toInt()
            return sequence(Triple(1, 1, 20151125L)) {
                Triple(
                        if (it.first != 1) it.first - 1 else it.second + 1,
                        if (it.first != 1) it.second + 1 else 1,
                        (it.third * 252533L) % 33554393L
                )
            }.first {
                it.first == row && it.second == column
            }.third.toString()
        }
        return super.first()
    }
}

fun main(args: Array<String>) {
    val day = Day25()
    println(day.first())
}