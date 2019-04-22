package com.juangm.randomusers.data.repository

import com.juangm.randomusers.data.model.User
import com.juangm.randomusers.data.source.remote.UsersRemoteSource
import io.reactivex.Single

class UsersRepositoryImpl constructor(private val remoteSource: UsersRemoteSource): UsersRepository {

    override fun getUserList(number: Int): Single<List<User>> {
        return remoteSource.getRandomUsers(number)
    }
}