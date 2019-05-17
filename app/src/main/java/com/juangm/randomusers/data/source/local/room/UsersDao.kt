package com.juangm.randomusers.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.juangm.randomusers.data.source.local.room.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UsersDao {

    @Query("SELECT COUNT() FROM random_users")
    fun getUsersCount(): Single<Int>

    @Query("SELECT * FROM random_users")
    fun getUsers(): DataSource.Factory<Int, UserEntity>

    @Query("SELECT * FROM random_users WHERE favorite = 1 ORDER BY name ASC")
    fun getFavoriteUsers(): Single<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Update
    fun update(user: UserEntity): Completable

    @Query("DELETE FROM random_users")
    fun deleteAllUsers(): Completable
}