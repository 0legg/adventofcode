package net.olegg.aoc.someday

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cookies.ConstantCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.get
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding
import net.olegg.aoc.BuildConfig

/**
 * Network client.
 */
object Fetcher {
  private val client = HttpClient(CIO) {
    install(HttpCookies) {
      storage = ConstantCookiesStorage(
        Cookie(
          name = "session",
          value = BuildConfig.COOKIE,
          domain = ".adventofcode.com",
          encoding = CookieEncoding.RAW,
        )
      )
    }
  }

  suspend fun fetchInput(year: Int, day: Int): String {
    return client.get("https://adventofcode.com/$year/day/$day/input").body()
  }
}
