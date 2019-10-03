package com.juangm.randomusers.data.mapper

import com.juangm.randomusers.data.source.local.room.entity.UserEntity
import com.juangm.randomusers.data.source.remote.dto.UserDto
import com.juangm.randomusers.domain.models.User

val mapRemoteUserToDomain: (UserDto) -> User = { user ->
    User(
        user.login?.uuid ?: "",
        user.name?.first ?: "Unknown name",
        user.name?.last ?: "Unknown last name",
        user.email ?: "Unknown email",
        user.picture?.thumbnail ?: "",
        user.picture?.medium ?: "",
        user.picture?.large ?: "",
        user.phone ?: "Unknown phone",
        user.gender ?: "Unknown gender",
        user.location?.street?.name ?: "" + " " + user.location?.street?.number ?: "",
        user.location?.city ?: "Unknown city",
        user.location?.state ?: "Unknown state",
        user.location?.country ?: "Unknown country",
        user.registered?.date ?: "Unknown date")
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
        user.country,
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
        user.country,
        user.registered,
        user.favorite)
}