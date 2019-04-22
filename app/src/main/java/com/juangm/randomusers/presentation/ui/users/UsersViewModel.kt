package com.juangm.randomusers.presentation.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juangm.randomusers.data.model.User
import com.juangm.randomusers.domain.usecases.GetUserListUseCase
import com.juangm.randomusers.domain.models.UserModel
import timber.log.Timber
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val getUserListUseCase: GetUserListUseCase): ViewModel() {

    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>>
        get() = _users

    fun getUserList() {
        getUserListUseCase.execute(
            { users -> _users.value = users },
            { error -> Timber.e(error) },
            Unit)
    }
}