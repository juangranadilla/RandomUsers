package com.juangm.randomusers.data.repository

import com.juangm.randomusers.data.mapper.UserRemoteMapper
import com.juangm.randomusers.data.dto.ResponseDto
import com.juangm.randomusers.domain.models.User
import retrofit2.Call
import retrofit2.Response

class UsersRepositoryImpl constructor(
    private val usersService: UsersService,
    private val mapper: UserRemoteMapper
): UsersRepository {

    override fun getUserList(
        page: Int,
        number: Int,
        onSuccess: (users: List<User>) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        usersService.getRandomUsers(page, number).enqueue(object : retrofit2.Callback<ResponseDto> {
                override fun onFailure(call: Call<ResponseDto>?, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<ResponseDto>?, response: Response<ResponseDto>) {
                    if (response.isSuccessful) {
                        val users = response.body()?.results?.map { userFromApi -> mapper.fromApi(userFromApi) }
                        onSuccess(users ?: emptyList())
                    } else {
                        onError(Throwable(response.errorBody()?.string() ?: "Unknown error"))
                    }
                }
            })
    }
}