package com.valentinhinov.ganandroidtest.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.valentinhinov.ganandroidtest.data.api.BreakingBadApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object ApiModule {

    @ExperimentalSerializationApi
    val instance = module {
        factory<BreakingBadApi> {
            Retrofit.Builder()
                .baseUrl("https://breakingbadapi.com/api/")
                .client(get())
                .addConverterFactory(
                    Json { ignoreUnknownKeys = true }
                        .asConverterFactory(MediaType.get("application/json"))
                )
                .build()
                .create(BreakingBadApi::class.java)
        }
        factory {
            OkHttpClient
                .Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
        }
    }
}