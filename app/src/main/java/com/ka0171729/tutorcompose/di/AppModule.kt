package com.ka0171729.tutorcompose.di

import com.ka0171729.tutorcompose.domain.remote.HttpClient
import com.ka0171729.tutorcompose.datastore.DataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { DataStoreManager(androidContext()) }
    single { HttpClient(get()) }
    single { get<HttpClient>().getApi() ?: throw IllegalStateException("API not initialized") }
}