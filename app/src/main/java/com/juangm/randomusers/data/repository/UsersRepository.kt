package com.juangm.randomusers.data.repository

import com.juangm.randomusers.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UsersRepository {
    fun getUserList(number: Int): Single<List<User>>
}