package com.juangm.randomusers.data.repository

import com.juangm.randomusers.domain.models.User
import io.reactivex.Single

interface UsersRepository {
    fun getUserList(page: Int, number: Int): Single<List<User>>
}