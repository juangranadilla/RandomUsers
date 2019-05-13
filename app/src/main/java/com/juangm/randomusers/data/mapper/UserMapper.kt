package com.juangm.randomusers.data.mapper

import com.juangm.randomusers.data.source.local.room.entity.UserEntity
import com.juangm.randomusers.data.source.remote.dto.UserDto
import com.juangm.randomusers.domain.models.User

val mapRemoteUserToDomain: (UserDto) -> User = { user ->
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

val mapLocalUserToDomain: (UserEntity) -> User = { user ->
    User(
        user.id,
        user.name,
        user.surname,
        user.email,
        user.smallPicture,
        user.normalPicture,
        user.largePicture,
        user.phone,
        user.gender,
        user.street,
        user.city,
        user.state,
        user.registered,
        user.favorite)
}

val mapDomainUserToLocal: (User) -> UserEntity = { user ->
    UserEntity(
        user.id,
        user.name,
        user.surname,
        user.email,
        user.smallPicture,
        user.normalPicture,
        user.largePicture,
        user.phone,
        user.gender,
        user.street,
        user.city,
        user.state,
        user.registered,
        user.favorite)
}