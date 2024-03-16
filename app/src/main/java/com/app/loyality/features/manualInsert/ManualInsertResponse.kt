package com.app.loyality.features.manualInsert


import com.google.gson.annotations.SerializedName

data class ManualInsertResponse(
    @SerializedName("status")
    val status: String
)