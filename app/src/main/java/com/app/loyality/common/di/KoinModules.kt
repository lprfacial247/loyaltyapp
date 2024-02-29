package com.app.loyality.common.di

import com.app.loyality.common.network.api.RetrofitClient
import com.app.loyality.common.network.repos.MainRepository
import com.app.loyality.common.persistence.UserDataStore
import com.app.loyality.common.utils.Utils
import com.app.loyality.features.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { Utils(androidApplication()) }

    single { UserDataStore(androidApplication()) }

    single {
        RetrofitClient.getApiClientWithChucker(androidContext())
    }

    single { MainRepository(get(), get()) }

    // ViewModels
    viewModel<HomeViewModel>()
}