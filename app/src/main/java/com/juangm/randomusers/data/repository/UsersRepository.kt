package com.juangm.randomusers.data.repository

import androidx.paging.PagedList
import com.juangm.randomusers.domain.models.User
import io.reactivex.Observable

interface UsersRepository {
    fun getUserList(): Observable<PagedList<User>>
}