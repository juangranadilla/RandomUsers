package com.juangm.randomusers.presentation.ui.common

import com.juangm.randomusers.domain.models.User

interface FavoriteUserItemInteractions: UserItemInteractions {
    fun removeUserFromFavorites(user: User)
}