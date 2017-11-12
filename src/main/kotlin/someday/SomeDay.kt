package someday

/**
 * Created by olegg on 12/18/15.
 */
abstract class SomeDay(year: Int, val day: Int) {
    val data: String

    init {
        data = Fetcher.Companion.fetcher.fetchInput(year, day).execute().body()?.trim() ?: ""
    }

    open fun first(): String {
        throw UnsupportedOperationException()
    }

    open fun second(): String {
        throw UnsupportedOperationException()
    }

    companion object {
        fun mainify(clazz: Class<out SomeDay>) {
            val day = clazz.newInstance()
            println(day.first())
            println(day.second())
        }
    }
}