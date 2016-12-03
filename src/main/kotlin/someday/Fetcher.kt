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
    @Headers("Cookie: session=53616c7465645f5f310b83cb3f4bda451297cb2eea755f9c4c2c96d60464a7a7bdc7b60e9ca1eb6e313df542931107bb")
    @GET("{year}/day/{day}/input")
    fun fetchInput(@Path("year") year: Int, @Path("day") day: Int): Call<String>

    companion object {
        val fetcher  = Retrofit.Builder()
                .baseUrl("http://adventofcode.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(Fetcher::class.java)
    }
}