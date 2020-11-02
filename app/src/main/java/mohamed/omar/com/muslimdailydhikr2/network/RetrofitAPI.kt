package mohamed.omar.com.muslimdailydhikr2.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {

    // https://aladhan.com/prayer-times-api
    // https://www.mocky.io/
    // https://inducesmile.com/android/android-retrofit-2-with-json-api-example/
    // https://github.com/HenryChigbo/Retrofit2

    fun getRetrofit(url: String): Retrofit {

        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

    }

}
