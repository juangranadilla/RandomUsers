package com.juangm.randomusers.presentation.di

import com.juangm.randomusers.domain.datasource.UsersDataSource
import com.juangm.randomusers.domain.datasource.UsersDataSourceFactory
import com.juangm.randomusers.domain.usecase.GetUserListUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetUserListUseCase(get()) }
    single { UsersDataSource(get()) }
    single { UsersDataSourceFactory(get()) }
}