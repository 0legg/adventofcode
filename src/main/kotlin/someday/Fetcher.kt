package someday

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
    @Headers("Cookie: session=53616c7465645f5f66a3274a71c532aad369ae139827e9b7e89c2f35d484c8acb8cd09e6f685e49c715e184cc7ff9355")
    @GET("day/{day}/input")
    fun fetchInput(@Path("day") day: Int): Call<String>

    companion object {
        val fetcher  = Retrofit.Builder()
                .baseUrl("http://adventofcode.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(Fetcher::class.java)
    }
}