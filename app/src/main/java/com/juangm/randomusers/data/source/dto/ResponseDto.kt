package com.juangm.randomusers.data.source.dto

data class ResponseDto(
    val results: List<UserDto>,
    val info: InfoDto
)