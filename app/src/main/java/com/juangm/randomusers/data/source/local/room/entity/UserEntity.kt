package com.juangm.randomusers.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "random_users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val smallPicture: String,
    val normalPicture: String,
    val largePicture: String,
    val phone: String,
    val gender: String,
    val street: String,
    val city: String,
    val state: String,
    val registered: String,
    var favorite: Boolean = false
)