package com.juangm.randomusers.data.repository

import com.juangm.randomusers.data.dto.ResponseDto
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {

    @GET(" ")
    fun getRandomUsers(@Query("page") page: Int, @Query("results") results: Int): Single<ResponseDto>
}