package com.app.loyality.common.network.repos

import com.app.loyality.common.network.api.ApiService
import com.app.loyality.common.persistence.UserDataStore

class MainRepository(
    private val dataStore: UserDataStore,
    private val apiHelper: ApiService
) {

    private suspend fun token(): String = dataStore.getAuthToken() ?: ""


    suspend fun fetchAnyApiData1() = apiHelper.fetchAnyApiData1(token())

    suspend fun fetchAnyApiData2() = apiHelper.fetchAnyApiData2(token())
}