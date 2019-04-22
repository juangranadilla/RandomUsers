package com.juangm.randomusers.domain.usecases

import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.base.SingleUseCase
import com.juangm.randomusers.domain.models.UserModel
import io.reactivex.Single
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val usersRepository: UsersRepository): SingleUseCase<List<UserModel>, Unit>() {

    private val defaultNumberOfUsers = 20

    override fun buildUseCase(params: Unit): Single<List<UserModel>> {
        return usersRepository.getUserList(defaultNumberOfUsers).map { users ->
            users.map { user -> UserModel(
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
        }
    }
}