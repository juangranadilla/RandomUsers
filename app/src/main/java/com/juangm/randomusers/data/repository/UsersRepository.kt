package com.juangm.randomusers.data.repository

import com.juangm.randomusers.domain.models.User

interface UsersRepository {
    fun getUserList(page: Int,
                    number: Int,
                    onSuccess: (users: List<User>) -> Unit,
                    onError: (throwable: Throwable) -> Unit)
}