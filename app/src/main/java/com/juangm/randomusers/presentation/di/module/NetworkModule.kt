package com.juangm.randomusers.presentation.di.module

import com.juangm.randomusers.BuildConfig
import com.juangm.randomusers.data.repository.UsersService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@Suppress("unused")
class NetworkModule {

    companion object {
        const val BASE_URL: String = "https://api.randomuser.me/"
    }

    @Provides
    internal fun usersService(retrofit: Retrofit): UsersService {
        return retrofit.create(UsersService::class.java)
    }

    @Provides
    internal fun retrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    @Provides
    internal fun getOkHttpClient() : OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }
}