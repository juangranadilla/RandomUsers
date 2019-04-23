package com.juangm.randomusers.presentation.di.module

import com.juangm.randomusers.domain.datasource.UsersDataSource
import com.juangm.randomusers.domain.datasource.UsersDataSourceFactory
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    internal fun usersDataSourceFactory(dataSource: UsersDataSource): UsersDataSourceFactory {
        return UsersDataSourceFactory(dataSource)
    }
}