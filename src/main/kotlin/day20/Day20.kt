package day20

import someday.SomeDay

/**
 * Created by olegg on 21/12/15.
 */
class Day20: SomeDay(20) {
    val max = data.toInt()

    override fun first(): String {
        return sequence(1) { it + 1 }.first { house ->
            (1..Math.sqrt(house.toDouble()).toInt()).sumBy { if (house % it == 0) (it + house / it) * 10 else 0 } >= max
        }.toString()
    }
}

fun main(args: Array<String>) {
    val day = Day20()
    println(day.first())
}