package com.juangm.randomusers.presentation.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.usecase.UpdateUserUseCase
import timber.log.Timber

class UserDetailViewModel(private val updateUserUseCase: UpdateUserUseCase): ViewModel() {

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean>
        get() = _favorite

    private val _favoriteUsersButtonEnabled = MutableLiveData<Boolean>()
    val favoriteUsersButtonEnabled: LiveData<Boolean>
        get() = _favoriteUsersButtonEnabled

    init {
        _favoriteUsersButtonEnabled.value = true
    }

    fun updateUser(user: User) {
        Timber.i("Executing UpdateUserUseCase...")
        _favoriteUsersButtonEnabled.value = false
        updateUserUseCase.execute(
            {
                Timber.i("UpdateUserUseCase completed")
                _favorite.value = user.favorite
                _favoriteUsersButtonEnabled.value = true
            },
            { throwable ->
                Timber.e(throwable)
                _favoriteUsersButtonEnabled.value = true
            },
            user)
    }

    override fun onCleared() {
        super.onCleared()
        updateUserUseCase.dispose()
        Timber.i("Use cases disposed")
    }
}