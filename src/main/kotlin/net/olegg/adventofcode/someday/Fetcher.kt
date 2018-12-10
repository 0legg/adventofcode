package net.olegg.adventofcode.someday

import net.olegg.adventofcode.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Network interface.
 */
interface Fetcher {
  @Headers("Cookie: session=${BuildConfig.COOKIE}")
  @GET("{year}/day/{day}/input")
  fun fetchInput(@Path("year") year: Int, @Path("day") day: Int): Call<String>

  companion object {
    private val cacheDir = Files.createDirectories(
        Paths.get(System.getProperty("java.io.tmpdir"), "adventofcode"))
    private val okhttp = OkHttpClient.Builder()
        .cache(Cache(cacheDir.toFile(), 100 * 1024 * 1024))
        .build()
    val fetcher = Retrofit.Builder()
        .client(okhttp)
        .baseUrl("https://adventofcode.com")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(Fetcher::class.java)
  }
}
