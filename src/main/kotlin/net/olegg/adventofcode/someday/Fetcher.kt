package net.olegg.adventofcode.someday

import net.olegg.adventofcode.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by olegg on 12/18/15.
 */
interface Fetcher {
    @Headers("Cookie: session=${BuildConfig.COOKIE}")
    @GET("{year}/day/{day}/input")
    fun fetchInput(@Path("year") year: Int, @Path("day") day: Int): Call<String>

    companion object {
        val fetcher = Retrofit.Builder()
                .baseUrl("http://adventofcode.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(Fetcher::class.java)
    }
}