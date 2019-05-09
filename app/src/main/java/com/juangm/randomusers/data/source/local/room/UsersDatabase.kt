package com.juangm.randomusers.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juangm.randomusers.data.source.local.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun usersDao(): UsersDao
}