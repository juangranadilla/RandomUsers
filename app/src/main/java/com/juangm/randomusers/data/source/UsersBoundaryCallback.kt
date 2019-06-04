package com.juangm.randomusers.data.source

import androidx.paging.PagedList
import com.juangm.randomusers.data.constants.RepositoryConstants.DEFAULT_PAGE_SIZE
import com.juangm.randomusers.data.mapper.mapDomainUserToLocal
import com.juangm.randomusers.data.source.local.UsersLocalSource
import com.juangm.randomusers.data.source.local.room.entity.UserEntity
import com.juangm.randomusers.data.source.remote.UsersRemoteSource
import com.juangm.randomusers.domain.models.User
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UsersBoundaryCallback(
    private val usersLocalSource: UsersLocalSource,
    private val usersRemoteSource: UsersRemoteSource
): PagedList.BoundaryCallback<User>() {

    private var nextPage = 1
    private var isRequestRunning = false
    private val disposables = CompositeDisposable()

    override fun onZeroItemsLoaded() {
        Timber.i("onZeroItemsLoaded")
        getUsersFromApiAndSaveInDatabase()
    }

    override fun onItemAtEndLoaded(itemAtEnd: User) {
        Timber.i("onItemAtEndLoaded")
        getUsersFromApiAndSaveInDatabase()
    }

    private fun getUsersFromApiAndSaveInDatabase() {
        if(isRequestRunning) return

        isRequestRunning = true
        val disposable = usersLocalSource.getUsersCountFromDatabase()
            .flatMap { usersCount -> getUsersFromApi(usersCount) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doFinally { isRequestRunning = false }
            .subscribe(
                { Timber.i("$nextPage page retrieved from API and users saved in database successfully") },
                { it.printStackTrace() }
            )
        disposables.add(disposable)
    }

    private fun getUsersFromApi(usersCount: Int): Single<List<UserEntity>> {
        if(usersCount != 0)
            nextPage = (usersCount / DEFAULT_PAGE_SIZE) + 1
        else
            nextPage = 1

        return usersRemoteSource.getUsersFromApi(nextPage)
            .map { users -> users.map(mapDomainUserToLocal) }
            .doOnSuccess { users -> saveUsersInDatabase(users) }
    }

    private fun saveUsersInDatabase(users: List<UserEntity>) {
        if(users.isNotEmpty()) {
            val disposable = usersLocalSource.saveUsersInDatabase(users)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnComplete {
                    Timber.i("${users.size} users saved in database")
                }
                .subscribe()
            disposables.add(disposable)
        }
    }
}