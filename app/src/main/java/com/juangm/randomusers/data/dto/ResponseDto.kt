package com.juangm.randomusers.data.dto

data class ResponseDto(
    val results: List<UserDto>,
    val info: InfoDto
)