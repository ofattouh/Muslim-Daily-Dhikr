package mohamed.omar.com.muslimdailydhikr2.network

import com.google.gson.annotations.SerializedName;

class ApiObject {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("status")
    var status: String = ""

    @SerializedName("data")
    var data : Any = ""

    fun getApiCode(): String {
        return code
    }

    fun getApiStatus(): String {
        return status
    }

    fun getApiData(): Any {
        return data
    }

}