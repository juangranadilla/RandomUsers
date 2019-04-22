package com.juangm.randomusers.data.source.remote

import com.juangm.randomusers.data.model.User
import io.reactivex.Single

interface UsersRemoteSource {
    fun getRandomUsers(page: Int, results: Int): Single<List<User>>
}