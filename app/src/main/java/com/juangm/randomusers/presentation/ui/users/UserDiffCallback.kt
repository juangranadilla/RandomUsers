package com.juangm.randomusers.presentation.ui.users

import androidx.recyclerview.widget.DiffUtil
import com.juangm.randomusers.domain.models.UserModel

class UserDiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }
}