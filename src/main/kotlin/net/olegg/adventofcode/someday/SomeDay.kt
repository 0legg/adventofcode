package net.olegg.adventofcode.someday

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Abstract class representing solution for [day]s problem in specified [year].
 */
abstract class SomeDay(year: Int, val day: Int) {
    val data: String = Fetcher.fetcher.fetchInput(year, day).execute().body() ?: ""

    open fun first(data: String): String {
        throw UnsupportedOperationException()
    }

    open fun second(data: String): String {
        throw UnsupportedOperationException()
    }

    companion object {
        fun mainify(clazz: KClass<out SomeDay>) {
            clazz.primaryConstructor?.call()?.apply {
                println(first(data))
                println(second(data))
            }
        }
    }
}