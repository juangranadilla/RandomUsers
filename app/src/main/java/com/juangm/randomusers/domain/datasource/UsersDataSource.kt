package com.juangm.randomusers.domain.datasource

import androidx.paging.PageKeyedDataSource
import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.models.UserListParams
import com.juangm.randomusers.domain.usecase.GetUserListUseCase
import timber.log.Timber

class UsersDataSource(private val userListUseCase: GetUserListUseCase): PageKeyedDataSource<Int, User>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        userListUseCase.execute(
            { users -> callback.onResult(users, 1, 2) },
            { error -> Timber.e(error) },
            UserListParams(1, params.requestedLoadSize))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        userListUseCase.execute(
            { users -> callback.onResult(users, params.key + 1) },
            { error -> Timber.e(error) },
            UserListParams(1, params.requestedLoadSize))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        //Not needed, as we load data in one direction, from our initial load
    }
}