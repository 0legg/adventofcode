/**
 * Created by olegg on 12/18/15.
 */
abstract class SomeDay(val day: Int) {
    val data: String

    init {
        data = Fetcher.fetcher.fetchInput(day).execute().body()
    }

    open fun first(): String {
        throw UnsupportedOperationException()
    }

    open fun second(): String {
        throw UnsupportedOperationException()
    }
}