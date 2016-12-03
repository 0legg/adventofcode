package year2015.day1

import someday.SomeDay
import utils.scan

/**
 * Created by olegg on 12/18/15.
 */
class Day1: SomeDay(1) {
    val floors = data.map { 1 - 2 * (it.minus('(')) }

    override fun first(): String {
        return floors.sum().toString()
    }

    override fun second(): String {
        return (floors.scan(0) { acc, value -> acc + value }.indexOfFirst { it < 0 } + 1).toString()
    }
}

fun main(args: Array<String>) {
    val day = Day1()
    println(day.first())
    println(day.second())
}