package net.olegg.aoc.someday

import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Abstract class representing solution for [day]s problem in specified [year].
 */
open class SomeDay(val year: Int, val day: Int) {
  open val localData: String? = null
  val data: String by lazy {
    localData ?: runBlocking { Fetcher.fetchInput(year, day) }
  }

  open fun first(data: String): Any? = null

  open fun second(data: String): Any? = null

  companion object {
    fun mainify(someday: SomeDay) {
      with(someday) {
        println("Year $year, day $day")
        measureTimeMillis {
          println("First: ${first(data)?.toString() ?: "unsolved"}")
        }.run {
          println("Time: ${this}ms")
        }
        measureTimeMillis {
          println("Second: ${second(data)?.toString() ?: "unsolved"}")
        }.run {
          println("Time: ${this}ms")
        }
      }
    }
  }
}
