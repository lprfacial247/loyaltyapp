package com.app.loyality.common.network.api


import com.app.loyality.features.barScanner.UserInfoResponse
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

    @FormUrlEncoded
    @POST("api-loyalty-get-customer-information")
    fun checkCustomerInfo(
        @Field("user_index") userId: Int,
        @Field("option_index") optionIndex: Int,
        @Field("barcode") barcode: String,
        @Field("card_id") cardId: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String
    ): Call<UserInfoResponse>


}