package com.juangm.randomusers.data.mapper

import com.juangm.randomusers.data.dto.UserDto
import com.juangm.randomusers.domain.models.User

val mapRemoteUser: (UserDto) -> User = { user ->
    User(
        user.login.uuid,
        user.name.first,
        user.name.last,
        user.email,
        user.picture.thumbnail,
        user.picture.medium,
        user.picture.large,
        user.phone,
        user.gender,
        user.location.street,
        user.location.city,
        user.location.state,
        user.registered.date)
}