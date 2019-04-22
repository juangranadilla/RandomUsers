package com.juangm.randomusers.presentation.ui.users

import com.juangm.randomusers.domain.models.UserModel

interface UserClickInterface {
    fun showUserDetail(user: UserModel)
}