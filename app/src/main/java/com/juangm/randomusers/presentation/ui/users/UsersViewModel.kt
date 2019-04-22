package com.juangm.randomusers.presentation.ui.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juangm.randomusers.data.model.User
import com.juangm.randomusers.domain.usecases.GetUserListUseCase
import com.juangm.randomusers.presentation.ui.models.UserModel
import javax.inject.Inject

class UserListViewModel @Inject constructor(private val getUserListUseCase: GetUserListUseCase): ViewModel() {

    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>>
        get() = _users

    private val TAG = UserListViewModel::class.qualifiedName

    fun getUserList() {
        getUserListUseCase.execute(
            {
                users -> _users.value = users.map { user -> mapUserToPresentationModel(user) }
            },
            {
                Log.e(TAG, "getUserList: onError... " + it.message)
            },
            Unit)
    }

    private fun mapUserToPresentationModel(user: User): UserModel {
        return UserModel(
            user.id,
            user.name,
            user.surname,
            user.email,
            user.smallPicture,
            user.normalPicture,
            user.largePicture,
            user.phone,
            user.gender,
            user.street,
            user.city,
            user.state,
            user.registered,
            user.favorite)
    }
}