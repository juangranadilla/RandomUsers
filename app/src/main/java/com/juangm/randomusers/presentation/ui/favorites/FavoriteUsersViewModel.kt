package com.juangm.randomusers.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.usecase.GetFavoriteUserListUseCase
import com.juangm.randomusers.domain.usecase.UpdateUserUseCase
import timber.log.Timber

class FavoriteUsersViewModel(
    private val getFavoriteUserListUseCase: GetFavoriteUserListUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>>
        get() = _favoriteUsers

    fun getFavoriteUsers() {
        Timber.i("Executing GetFavoriteUserListUseCase...")
        getFavoriteUserListUseCase.execute(
            { favoriteUsers -> _favoriteUsers.value = favoriteUsers },
            { throwable ->  Timber.e(throwable) },
            Unit)
    }

    fun updateUser(user: User) {
        user.favorite = !user.favorite
        Timber.i("Executing UpdateUserUseCase...")
        updateUserUseCase.execute(
            {
                Timber.i("UpdateUserUseCase completed")
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