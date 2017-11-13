package net.olegg.adventofcode.someday

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Created by olegg on 12/18/15.
 */
abstract class SomeDay(year: Int, val day: Int) {
    val data: String = Fetcher.Companion.fetcher.fetchInput(year, day).execute().body()?.trim() ?: ""

    open fun first(): String {
        throw UnsupportedOperationException()
    }

    open fun second(): String {
        throw UnsupportedOperationException()
    }

    companion object {
        fun mainify(clazz: KClass<out SomeDay>) {
            clazz.primaryConstructor?.call()?.apply {
                println(first())
                println(second())
            }
        }
    }
}