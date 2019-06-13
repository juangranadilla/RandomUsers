package com.juangm.randomusers.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.usecase.GetFavoriteUserListUseCase
import com.juangm.randomusers.domain.usecase.UpdateUserUseCase
import com.juangm.randomusers.presentation.ui.common.Event
import timber.log.Timber

class FavoriteUsersViewModel(
    private val getFavoriteUserListUseCase: GetFavoriteUserListUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>>
        get() = _favoriteUsers

    private val _updatedUserEvent = MutableLiveData<Event<User>>()
    val updateUserEvent: LiveData<Event<User>>
        get() = _updatedUserEvent

    fun getFavoriteUsers() {
        Timber.i("Executing GetFavoriteUserListUseCase...")
        getFavoriteUserListUseCase.execute(
            { favoriteUsers -> _favoriteUsers.value = favoriteUsers },
            { throwable ->  Timber.e(throwable) },
            Unit)
    }

    fun removeUserFromFavorites(user: User) {
        user.favorite = false
        updateUser(user)
    }

    fun addUserToFavorites(user: User) {
        user.favorite = true
        updateUser(user)
    }

    private fun updateUser(user: User) {
        Timber.i("Executing UpdateUserUseCase...")
        updateUserUseCase.execute(
            {
                Timber.i("UpdateUserUseCase completed. User favorite: ${user.favorite}")
                _updatedUserEvent.value = Event(user)
                getFavoriteUsers()
            },
            { throwable ->  Timber.e(throwable) },
            user)
    }

    override fun onCleared() {
        super.onCleared()
        getFavoriteUserListUseCase.dispose()
        updateUserUseCase.dispose()
        Timber.i("Use cases disposed")
    }
}