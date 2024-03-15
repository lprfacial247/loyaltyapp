package com.app.loyality.common.network.api


import com.app.loyality.features.login.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("api-login")
    fun login(
        @Field("device_token") deviceToken: String,
        @Field("pincode") pincode: String
    ): Call<LoginResponse>


}