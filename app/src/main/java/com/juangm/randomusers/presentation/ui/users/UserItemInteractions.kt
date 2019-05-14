package com.juangm.randomusers.presentation.ui.users

import com.juangm.randomusers.domain.models.User

interface UserItemInteractions {
    fun showUserDetail(user: User)
}