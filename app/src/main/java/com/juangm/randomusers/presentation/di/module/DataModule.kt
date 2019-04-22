package com.juangm.randomusers.presentation.di.module

import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.data.repository.UsersRepositoryImpl
import com.juangm.randomusers.data.source.remote.UsersRemoteSourceImpl
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    internal fun usersRepository(remoteSource: UsersRemoteSourceImpl): UsersRepository {
        return UsersRepositoryImpl(remoteSource)
    }
}