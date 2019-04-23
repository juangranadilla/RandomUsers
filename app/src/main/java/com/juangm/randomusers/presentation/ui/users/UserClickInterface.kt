package com.juangm.randomusers.presentation.ui.users

import com.juangm.randomusers.domain.models.User

interface UserClickInterface {
    fun showUserDetail(user: User)
}