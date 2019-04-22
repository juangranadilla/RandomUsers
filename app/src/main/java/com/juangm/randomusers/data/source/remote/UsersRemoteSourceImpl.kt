package com.juangm.randomusers.data.source.remote

import com.juangm.randomusers.data.model.User
import io.reactivex.Single
import javax.inject.Inject

class UsersRemoteSourceImpl @Inject constructor(private val service: UsersService) : UsersRemoteSource {

    override fun getRandomUsers(results: Int): Single<List<User>> {
        return service.getRandomUsers(results).map { it.results.map {user ->
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
        } }
    }
}