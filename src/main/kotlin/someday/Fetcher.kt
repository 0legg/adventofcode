package someday

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import java.lang.reflect.Type

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
                .addConverterFactory(object : Converter.Factory() {
                    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?) = Converter<ResponseBody, String> { it.string() }
                })
                .build()
                .create(Fetcher::class.java)
    }
}