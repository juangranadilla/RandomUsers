package com.juangm.randomusers.domain.usecases

import com.juangm.randomusers.data.model.User
import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val usersRepository: UsersRepository): SingleUseCase<List<User>, Unit>() {

    private val defaultNumberOfUsers = 20

    override fun buildUseCase(params: Unit): Single<List<User>> {
        return usersRepository.getUserList(defaultNumberOfUsers)
    }
}