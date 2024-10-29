package com.yobel.lecturadeliveryapp.di

import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.endpoints.MethodsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor() : OkHttpClient{

        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .addInterceptor(interceptor)


        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(clientBuilder : OkHttpClient): MethodsApi = Retrofit.Builder()
        .baseUrl("https://yscm-webservices.yobelscm.biz/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientBuilder)
        .build()
        .create()
}