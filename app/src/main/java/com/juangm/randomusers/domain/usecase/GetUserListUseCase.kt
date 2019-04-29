package com.juangm.randomusers.domain.usecase

import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.base.SingleUseCase
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.models.UserListParams
import io.reactivex.Single

class GetUserListUseCase(private val usersRepository: UsersRepository): SingleUseCase<List<User>, UserListParams>() {

    override fun buildUseCase(params: UserListParams): Single<List<User>> {
        return usersRepository.getUserList(params.pageNumber, params.usersPerPage)
    }
}