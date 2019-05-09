package com.juangm.randomusers.data.source.remote

import com.juangm.randomusers.data.mapper.mapRemoteUserToDomain
import com.juangm.randomusers.data.source.remote.retrofit.UsersService
import com.juangm.randomusers.domain.models.User
import io.reactivex.Single

class UsersRemoteSourceImpl(private val usersService: UsersService): UsersRemoteSource {

    override fun getUsersFromApi(page: Int): Single<List<User>> {
        return usersService.getUsers(page).map { ApiResponse ->
            ApiResponse.results.map { user -> mapRemoteUserToDomain(user) }
        }
    }
}