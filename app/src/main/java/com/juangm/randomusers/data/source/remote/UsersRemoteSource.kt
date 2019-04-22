package com.juangm.randomusers.data.source.remote

import com.juangm.randomusers.data.model.User
import io.reactivex.Single

interface UsersRemoteSource {
    fun getRandomUsers(results: Int): Single<List<User>>
}