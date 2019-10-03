package com.juangm.randomusers.data.source.remote.dto

data class LocationDto(
    val street: StreetDto? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val postcode: String? = null
)