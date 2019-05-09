package com.juangm.randomusers.data.source.remote

import com.juangm.randomusers.domain.models.User
import io.reactivex.Single

interface UsersRemoteSource {
    fun getUsersFromApi(page: Int): Single<List<User>>
}