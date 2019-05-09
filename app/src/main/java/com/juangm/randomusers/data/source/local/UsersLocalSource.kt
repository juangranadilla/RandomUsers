package com.juangm.randomusers.data.source.local

import androidx.paging.DataSource
import com.juangm.randomusers.data.source.local.room.entity.UserEntity
import com.juangm.randomusers.domain.models.User
import io.reactivex.Single

interface UsersLocalSource {
    fun getUsersCountFromDatabase(): Single<Int>
    fun getUsersFromDatabase(): DataSource.Factory<Int, User>
    fun saveUsersInDatabase(users: List<UserEntity>)
}