package com.juangm.randomusers.domain.models

import java.io.Serializable

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
    val country: String,
    val registered: String,
    var favorite: Boolean = false
): Serializable