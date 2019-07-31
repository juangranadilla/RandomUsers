package com.juangm.randomusers.presentation.di

import android.content.Context
import androidx.paging.RxPagedListBuilder
import androidx.room.Room
import com.juangm.randomusers.BuildConfig
import com.juangm.randomusers.data.constants.RepositoryConstants
import com.juangm.randomusers.domain.repository.UsersRepository
import com.juangm.randomusers.data.repository.UsersRepositoryImpl
import com.juangm.randomusers.data.source.UsersBoundaryCallback
import com.juangm.randomusers.data.source.local.UsersLocalSource
import com.juangm.randomusers.data.source.local.UsersLocalSourceImpl
import com.juangm.randomusers.data.source.local.room.UsersDatabase
import com.juangm.randomusers.data.source.remote.UsersRemoteSource
import com.juangm.randomusers.data.source.remote.UsersRemoteSourceImpl
import com.juangm.randomusers.data.source.remote.retrofit.UsersService
import com.juangm.randomusers.domain.models.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL: String = "https://api.randomuser.me/"
const val DATABASE_NAME = "random_users_database"

val dataModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideUsersService(get()) }
    single { provideUsersDatabase(androidContext()) }
    single { UsersLocalSourceImpl(get()) as UsersLocalSource }
    single { UsersRemoteSourceImpl(get()) as UsersRemoteSource }
    single { UsersBoundaryCallback(get(), get()) }
    single { provideRxPagedListBuilder(get(), get()) }
    single { UsersRepositoryImpl(get(), get()) as UsersRepository }
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    })
    .build()

fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(client)
    .build()

fun provideUsersService(retrofit: Retrofit): UsersService = retrofit.create(UsersService::class.java)

fun provideUsersDatabase(context: Context) = Room.databaseBuilder(
    context,
    UsersDatabase::class.java,
    DATABASE_NAME).build()

fun provideRxPagedListBuilder(
    usersLocalSource: UsersLocalSource,
    usersBoundaryCallback: UsersBoundaryCallback
): RxPagedListBuilder<Int, User> =
    RxPagedListBuilder(usersLocalSource.getUsersFromDatabase(), RepositoryConstants.DEFAULT_PAGE_SIZE)
        .setBoundaryCallback(usersBoundaryCallback)