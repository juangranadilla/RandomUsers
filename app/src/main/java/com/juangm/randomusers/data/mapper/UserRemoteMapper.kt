package com.juangm.randomusers.data.mapper

import com.juangm.randomusers.data.dto.UserDto
import com.juangm.randomusers.domain.models.User
import javax.inject.Inject

class UserRemoteMapper @Inject constructor() {
    fun fromApi(from: UserDto) = User(
        from.login.uuid,
        from.name.first,
        from.name.last,
        from.email,
        from.picture.thumbnail,
        from.picture.medium,
        from.picture.large,
        from.phone,
        from.gender,
        from.location.street,
        from.location.city,
        from.location.state,
        from.registered.date)
}