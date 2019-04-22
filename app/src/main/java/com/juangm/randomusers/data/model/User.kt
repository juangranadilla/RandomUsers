package com.juangm.randomusers.data.model

data class User(
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
    val favorite: Boolean = false
)