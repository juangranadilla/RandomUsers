package com.juangm.randomusers.data.repository

import com.juangm.randomusers.data.mapper.UserRemoteMapper
import com.juangm.randomusers.data.dto.ResponseDto
import com.juangm.randomusers.domain.models.User
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

class UsersRepositoryImpl(
    private val usersService: UsersService,
    private val mapper: UserRemoteMapper
): UsersRepository {

    override fun getUserList(page: Int, number: Int): Single<List<User>> {
        return usersService.getRandomUsers(page, number).map { response ->
            response.results.map { user -> mapper.fromApi(user) }
        }
    }
}