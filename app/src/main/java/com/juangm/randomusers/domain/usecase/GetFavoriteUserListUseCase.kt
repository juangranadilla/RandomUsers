package com.juangm.randomusers.domain.usecase

import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.base.SingleUseCase
import com.juangm.randomusers.domain.models.User
import io.reactivex.Single

class GetFavoriteUserListUseCase(private val usersRepository: UsersRepository): SingleUseCase<List<User>, Unit>() {

    override fun useCaseExecution(params: Unit): Single<List<User>> = usersRepository.getFavoriteUserList()
}