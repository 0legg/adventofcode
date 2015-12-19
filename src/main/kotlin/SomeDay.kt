/**
 * Created by olegg on 12/18/15.
 */
abstract class SomeDay(val day: Int) {
    val data: String

    init {
        data = Fetcher.fetcher.fetchInput(day).execute().body()
    }

    fun first(): String {
        throw UnsupportedOperationException()
    }

    fun second(): String {
        throw UnsupportedOperationException()
    }
}