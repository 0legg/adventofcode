package day14

import someday.SomeDay

/**
 * Created by olegg on 12/20/15.
 */
class Day14: SomeDay(14) {
    val pattern = ".*\\b(\\d+)\\b.*\\b(\\d+)\\b.*\\b(\\d+)\\b.*".toPattern()
    val speeds = data.lines().map {
        val matcher = pattern.matcher(it)
        matcher.matches()
        Triple(matcher.group(1).toInt(), matcher.group(2).toInt(), matcher.group(3).toInt())
    }

    fun getDistance(time: Int): Int {
        return speeds.map { ((time / (it.second + it.third)) * it.second +
                        (time % (it.second + it.third)).coerceAtMost(it.second))* it.first }.max() ?: 0
    }

    override fun first(): String {
        return getDistance(2503).toString()
    }
}

fun main(args: Array<String>) {
    val day = Day14()
    println(day.first())
}