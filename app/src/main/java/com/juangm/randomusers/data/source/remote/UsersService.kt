package com.juangm.randomusers.data.source.remote

import com.juangm.randomusers.data.source.dto.ResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {

    @GET(" ")
    fun getRandomUsers(@Query("results") results: Int): Single<ResponseDto>
}