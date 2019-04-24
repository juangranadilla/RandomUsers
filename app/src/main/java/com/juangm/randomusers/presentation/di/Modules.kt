package com.juangm.randomusers.presentation.di

import com.juangm.randomusers.BuildConfig
import com.juangm.randomusers.data.mapper.UserRemoteMapper
import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.data.repository.UsersRepositoryImpl
import com.juangm.randomusers.data.repository.UsersService
import com.juangm.randomusers.domain.datasource.UsersDataSource
import com.juangm.randomusers.domain.datasource.UsersDataSourceFactory
import com.juangm.randomusers.presentation.ui.users.UsersViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideUsersService(get()) }
}

val dataModule = module {
    single { UsersRepositoryImpl(get(), UserRemoteMapper()) as UsersRepository }
}

val domainModule = module {
    single { UsersDataSource(get()) }
    single { UsersDataSourceFactory(get()) }
}

val viewModelModule = module {
    viewModel { UsersViewModel(get()) }
}

const val BASE_URL: String = "https://api.randomuser.me/"

fun provideOkHttpClient() : OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        })
        .build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
}

fun provideUsersService(retrofit: Retrofit): UsersService {
    return retrofit.create(UsersService::class.java)
}