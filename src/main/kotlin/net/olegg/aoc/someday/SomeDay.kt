package net.olegg.aoc.someday

import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Abstract class representing solution for [day]s problem in specified [year].
 */
open class SomeDay(val year: Int, val day: Int) {
  open val localData: String? = null
  val data: String by lazy {
    localData ?: runBlocking { Fetcher.fetchInput(year, day).dropLastWhile { it == '\n' } }
  }
  val lines: List<String> by lazy { data.lines() }
  val matrix: List<List<Char>> by lazy { lines.map { it.toList() } }

  open fun first(): Any? = null

  open fun second(): Any? = null

  companion object {
    fun mainify(someday: SomeDay) {
      with(someday) {
        println("Year $year, day $day")
        measureTimeMillis {
          println("First: ${first()?.toString() ?: "unsolved"}")
        }.run {
          println("Time: ${this}ms")
        }
        measureTimeMillis {
          println("Second: ${second()?.toString() ?: "unsolved"}")
        }.run {
          println("Time: ${this}ms")
        }
      }
    }
  }
}
