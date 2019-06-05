package com.juangm.randomusers.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.juangm.randomusers.data.mapper.mapDomainUserToLocal
import com.juangm.randomusers.data.source.local.UsersLocalSource
import com.juangm.randomusers.domain.models.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class UsersRepositoryImpl(
    private val usersLocalSource: UsersLocalSource,
    private val pagedListBuilder: RxPagedListBuilder<Int, User>
): UsersRepository {

    override fun getUserList(): Observable<PagedList<User>> = pagedListBuilder.buildObservable()

    override fun getFavoriteUserList(): Single<List<User>> = usersLocalSource.getFavoriteUsersFromDatabase()

    override fun updateUser(user: User): Completable = usersLocalSource.updateUser(mapDomainUserToLocal(user))

    override fun deleteLocalUsers(): Completable = usersLocalSource.deleteLocalUsers()
}