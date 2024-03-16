package com.app.loyality.features.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("user_idx")
    val userIdx: Int = 0
)