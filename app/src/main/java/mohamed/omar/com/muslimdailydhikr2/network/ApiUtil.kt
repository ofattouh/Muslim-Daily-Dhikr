package mohamed.omar.com.muslimdailydhikr2.network

object ApiUtil {

    //private val BASE_URL = "http://www.mocky.io/"
    private val BASE_URL = "http://api.aladhan.com/"

    fun getServiceClass(): RetrofitInterface {
        return RetrofitAPI.getRetrofit(BASE_URL).create(RetrofitInterface::class.java)
    }

}