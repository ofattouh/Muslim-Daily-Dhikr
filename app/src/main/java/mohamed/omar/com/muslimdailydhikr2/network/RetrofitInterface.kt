package mohamed.omar.com.muslimdailydhikr2.network

import retrofit2.Call
import retrofit2.http.*


interface RetrofitInterface {

    // http://www.mocky.io/v2/5ba3066a2f00006e008d2ede
    // fun getAllPost(): Call<ApiObject>
    // fun getAllPost() = Call<List<ApiObject()>>

    //@GET("/v1/timings/currentDate?latitude=43.653908&longitude=-79.384293&method=2")
    @GET("/v1/timings/currentDate")
    fun getLatitudeLongtitude(@Query("latitude") latitude: Double,
           @Query("longitude") longitude: Double,
           @Query("method") method: Int): Call<ApiObject>

    //@GET("/v1/timingsByCity?city=Toronto&country=Canada&method=2")
    @GET("/v1/timingsByCity")
    fun getCityCountry(@Query("city") city: String,
          @Query("country") country: String,
          @Query("method") method: Int): Call<ApiObject>

}
