package day1

import someday.SomeDay

/**
 * Created by olegg on 12/18/15.
 */
class Day1: SomeDay(1) {
    val floors = data.map { 1 - 2 * (it.minus('(')) }

    override fun first(): String {
        return floors.sum().toString()
    }

    override fun second(): String {
        return (floors.mapIndexed { pos, value -> floors.subList(0, pos).sum() + value }.indexOfFirst { it < 0 } + 1).toString()
    }
}

fun main(args: Array<String>) {
    val day = Day1()
    println(day.first())
    println(day.second())
}