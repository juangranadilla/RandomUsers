package com.juangm.randomusers.presentation.di.module

import com.juangm.randomusers.data.mapper.UserRemoteMapper
import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.data.repository.UsersRepositoryImpl
import com.juangm.randomusers.data.repository.UsersService
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    internal fun usersRepository(usersService: UsersService, mapper: UserRemoteMapper): UsersRepository {
        return UsersRepositoryImpl(usersService, mapper)
    }
}