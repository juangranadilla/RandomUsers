package com.juangm.randomusers.domain.usecase

import androidx.paging.PagedList
import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.base.ObservableUseCase
import com.juangm.randomusers.domain.models.User
import io.reactivex.Observable

class GetUserListUseCase(private val usersRepository: UsersRepository): ObservableUseCase<PagedList<User>, Unit>() {

    override fun buildUseCase(params: Unit): Observable<PagedList<User>> = usersRepository.getUserList()
}