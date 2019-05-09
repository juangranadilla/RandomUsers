package com.juangm.randomusers.data.source

import androidx.paging.PagedList
import com.juangm.randomusers.data.constants.RepositoryConstants.DEFAULT_PAGE_SIZE
import com.juangm.randomusers.data.mapper.mapDomainUserToLocal
import com.juangm.randomusers.data.source.local.UsersLocalSource
import com.juangm.randomusers.data.source.remote.UsersRemoteSource
import com.juangm.randomusers.domain.models.User
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UsersBoundaryCallback(
    private val usersLocalSource: UsersLocalSource,
    private val usersRemoteSource: UsersRemoteSource
): PagedList.BoundaryCallback<User>() {

    private var nextPage = 1
    private var isRequestRunning = false

    override fun onZeroItemsLoaded() {
        Timber.i("onZeroItemsLoaded")
        getRemoteUsersAndSaveInDatabase()
    }

    override fun onItemAtEndLoaded(itemAtEnd: User) {
        Timber.i("onItemAtEndLoaded")
        getRemoteUsersAndSaveInDatabase()
    }

    private fun getRemoteUsersAndSaveInDatabase() {
        if(isRequestRunning) return

        isRequestRunning = true
        val disposable = usersLocalSource.getUsersCountFromDatabase()
            .flatMap { usersCount ->
                if(usersCount != 0)
                    nextPage = (usersCount / DEFAULT_PAGE_SIZE) + 1
                usersRemoteSource.getUsersFromApi(nextPage)
                    .map { users -> users.map(mapDomainUserToLocal) }
                    .doOnSuccess { users ->
                        if(users.isNotEmpty()) {
                            usersLocalSource.saveUsersInDatabase(users)
                            Timber.i("Users saved in database")
                        }
                    }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .ignoreElement()
            .doFinally { isRequestRunning = false }
            .subscribe(
                { Timber.i("New users retrieved from API and saved in database") },
                { it.printStackTrace() }
            )
    }
}