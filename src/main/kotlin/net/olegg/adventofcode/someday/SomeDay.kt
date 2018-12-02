package net.olegg.adventofcode.someday

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Abstract class representing solution for [day]s problem in specified [year].
 */
abstract class SomeDay(val year: Int, val day: Int) {
    val data: String = Fetcher.fetcher.fetchInput(year, day).execute().body() ?: ""

    open fun first(data: String): Any? {
        return null
    }

    open fun second(data: String): Any? {
        return null
    }

    companion object {
        fun mainify(clazz: KClass<out SomeDay>) {
            clazz.primaryConstructor?.call()?.apply {
                println("Year $year, day $day")
                println("First: ${first(data)?.toString() ?: "unsolved"}")
                println("Second: ${second(data)?.toString() ?: "unsolved"}")
            }
        }
    }
}