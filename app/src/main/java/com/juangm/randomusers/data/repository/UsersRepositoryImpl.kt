package com.juangm.randomusers.data.repository

import com.juangm.randomusers.data.mapper.mapRemoteUser
import com.juangm.randomusers.domain.models.User
import io.reactivex.Single

class UsersRepositoryImpl(private val usersService: UsersService): UsersRepository {

    override fun getUserList(page: Int, number: Int): Single<List<User>> {
        return usersService.getRandomUsers(page, number).map { response ->
            response.results.map { user -> mapRemoteUser(user) }
        }
    }
}