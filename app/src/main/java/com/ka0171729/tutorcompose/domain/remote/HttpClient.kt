package com.ka0171729.tutorcompose.domain.remote

import com.ka0171729.tutorcompose.BuildConfig
import com.ka0171729.tutorcompose.datastore.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient(private val dataStoreManager: DataStoreManager) {
    private var retrofit: Retrofit? = null
    private var apiService: ApiService? = null

    init {
        runBlocking {
            val token = dataStoreManager.tokenFlow.first()
            buildRetrofitClient(token)
        }
    }

    fun getApi(): ApiService? {
        if (apiService == null) {
            apiService = retrofit?.create(ApiService::class.java)
        }
        return apiService
    }

    private fun buildRetrofitClient(token: String?) {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        token?.let {
            clientBuilder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
        }

        retrofit = Retrofit.Builder()
            .baseUrl("${BuildConfig.BASE_URL}/api/")
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}