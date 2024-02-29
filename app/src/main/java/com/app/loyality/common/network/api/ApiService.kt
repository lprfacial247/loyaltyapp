package com.app.loyality.common.network.api

import com.app.loyality.features.home.FeedResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("api/endPoint")
    suspend fun fetchAnyApiData1(
        @Header("Authorization") token: String): FeedResponse

    @GET("api/endPoint")
    suspend fun fetchAnyApiData2(
        @Header("Authorization") token: String): FeedResponse
}