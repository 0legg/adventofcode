/**
 * Created by olegg on 12/18/15.
 */
class Day1: SomeDay(1) {
    override fun first(): String {
        return data.map { 1 - 2 * (it.minus('(')) }.sum().toString()
    }
}

fun main(args: Array<String>) {
    print(Day1().first())
}