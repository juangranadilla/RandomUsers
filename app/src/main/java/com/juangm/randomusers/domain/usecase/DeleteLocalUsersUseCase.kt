package com.juangm.randomusers.domain.usecase

import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.base.CompletableUseCase
import io.reactivex.Completable

class DeleteLocalUsersUseCase(private val usersRepository: UsersRepository): CompletableUseCase<Unit, Unit>() {

    override fun useCaseExecution(params: Unit): Completable = usersRepository.deleteLocalUsers()
}