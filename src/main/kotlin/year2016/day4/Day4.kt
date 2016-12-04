package year2016.day4

import someday.SomeDay
import year2016.DayOf2016
import kotlin.comparisons.compareBy

/**
 * Created by olegg on 12/4/16.
 */
class Day4 : DayOf2016(4) {
    val ROOM_PATTERN = "^(.+)-(\\d+)\\[(.+)\\]$".toPattern()
    override fun first(): String {
        val rooms = data.split("\n").map {
            ROOM_PATTERN.matcher(it).let {
                it.find()
                Triple(it.group(1).replace("-", ""), it.group(2).toInt(), it.group(3))
            }
        }

        return rooms.filter {
            it.third == it.first.toCharArray()
                    .groupBy { it }
                    .mapValues { it.value.size }
                    .toList()
                    .sortedWith(compareBy( { -it.second }, { it.first } ))
                    .take(5)
                    .map { it.first }
                    .joinToString(separator = "")
        }.sumBy { it.second }.toString()
    }
}

fun main(args: Array<String>) = SomeDay.mainify(Day4::class.java)