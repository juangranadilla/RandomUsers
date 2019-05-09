package com.juangm.randomusers.data.source.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juangm.randomusers.data.source.local.room.entity.UserEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM random_users")
    fun getUsers(): DataSource.Factory<Int, UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserEntity>)
}