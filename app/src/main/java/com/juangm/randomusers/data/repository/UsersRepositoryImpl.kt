package com.juangm.randomusers.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.juangm.randomusers.data.constants.RepositoryConstants
import com.juangm.randomusers.data.source.UsersBoundaryCallback
import com.juangm.randomusers.data.source.local.UsersLocalSource
import com.juangm.randomusers.data.source.remote.UsersRemoteSource
import com.juangm.randomusers.domain.models.User
import io.reactivex.Observable
import timber.log.Timber

class UsersRepositoryImpl(
    private val usersLocalSource: UsersLocalSource,
    private val usersRemoteSource: UsersRemoteSource
): UsersRepository {

    override fun getUserList(): Observable<PagedList<User>> {
        return RxPagedListBuilder(usersLocalSource.getUsersFromDatabase(), RepositoryConstants.DEFAULT_PAGE_SIZE)
            .setBoundaryCallback(UsersBoundaryCallback(usersLocalSource, usersRemoteSource))
            .buildObservable()
            .doOnNext { users ->
                Timber.i("${users.size} users returned")
            }
    }
}