package com.juangm.randomusers.data.dto

data class UserDto(
    val gender: String,
    val name: NameDto,
    val location: LocationDto,
    val email: String,
    val login: LoginDto,
    val dob: DobDto,
    val registered: RegisteredDto,
    val phone: String,
    val cell: String,
    val picture: PictureDto,
    val nat: String 
)