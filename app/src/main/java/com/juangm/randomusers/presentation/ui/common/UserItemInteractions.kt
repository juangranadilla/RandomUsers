package com.juangm.randomusers.presentation.ui.common

import android.widget.ImageView
import com.juangm.randomusers.domain.models.User

interface UserItemInteractions {
    fun showUserDetail(user: User, userImage: ImageView, position: Int)
}