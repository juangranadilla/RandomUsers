package com.juangm.randomusers.presentation.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.juangm.randomusers.domain.models.User

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}