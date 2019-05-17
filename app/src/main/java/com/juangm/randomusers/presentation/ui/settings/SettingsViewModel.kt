package com.juangm.randomusers.presentation.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juangm.randomusers.domain.usecase.DeleteLocalUsersUseCase
import timber.log.Timber

class SettingsViewModel(private val deleteLocalUsersUseCase: DeleteLocalUsersUseCase): ViewModel() {

    private val _usersDeleted = MutableLiveData<Boolean>()
    val usersDeleted: LiveData<Boolean>
        get() = _usersDeleted

    init {
        _usersDeleted.value = false
    }

    fun deleteLocalUsers() {
        Timber.i("Executing DeleteLocalUsersUseCase...")
        deleteLocalUsersUseCase.execute(
            {
                Timber.i("DeleteLocalUsersUseCase completed!")
                _usersDeleted.value = true
            },
            { throwable -> Timber.e(throwable) },
            Unit
        )
    }

    override fun onCleared() {
        super.onCleared()
        deleteLocalUsersUseCase.dispose()
        Timber.i("Use cases disposed")
    }
}