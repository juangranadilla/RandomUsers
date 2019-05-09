package com.juangm.randomusers.data.source.remote.dto

data class ResponseDto(
    val results: List<UserDto>,
    val info: InfoDto
)