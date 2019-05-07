package com.juangm.randomusers.data.repository

import com.juangm.randomusers.data.dto.ResponseDto
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_SEED = 8173

interface UsersService {

    @GET(" ")
    fun getRandomUsers(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: Int = DEFAULT_SEED
    ): Single<ResponseDto>
}