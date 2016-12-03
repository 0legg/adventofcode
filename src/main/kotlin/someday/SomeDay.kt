package someday

/**
 * Created by olegg on 12/18/15.
 */
abstract class SomeDay(val day: Int) {
    val data: String

    init {
        data = Fetcher.Companion.fetcher.fetchInput(day).execute().body().trim()
    }

    open fun first(): String {
        throw UnsupportedOperationException()
    }

    open fun second(): String {
        throw UnsupportedOperationException()
    }
}