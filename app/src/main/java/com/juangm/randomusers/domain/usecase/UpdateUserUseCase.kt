package com.juangm.randomusers.domain.usecase

import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.base.CompletableUseCase
import com.juangm.randomusers.domain.models.User
import io.reactivex.Completable

class UpdateUserUseCase(private val usersRepository: UsersRepository): CompletableUseCase<Unit, User>() {

    override fun useCaseExecution(params: User): Completable = usersRepository.updateUser(params)
}