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

    fun updateUser(user: User) {
        Timber.i("Executing UpdateUserUseCase...")
        updateUserUseCase.execute(
            {
                Timber.i("UpdateUserUseCase completed")
                _favorite.value = user.favorite
            },
            { throwable ->  Timber.e(throwable) },
            user)
    }

    override fun onCleared() {
        super.onCleared()
        updateUserUseCase.dispose()
    }
}