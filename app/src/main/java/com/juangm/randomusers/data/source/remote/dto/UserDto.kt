package com.juangm.randomusers.data.source.remote.dto

data class UserDto(
    val gender: String? = null,
    val name: NameDto? = null,
    val location: LocationDto? = null,
    val email: String? = null,
    val login: LoginDto? = null,
    val dob: DobDto? = null,
    val registered: RegisteredDto? = null,
    val phone: String? = null,
    val cell: String? = null,
    val picture: PictureDto? = null,
    val nat: String? = null
)