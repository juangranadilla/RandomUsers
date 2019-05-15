package com.juangm.randomusers.presentation.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.presentation.ui.common.UserDiffCallback
import com.juangm.randomusers.presentation.ui.common.UserItemInteractions
import com.juangm.randomusers.presentation.ui.common.setUserImage
import kotlinx.android.synthetic.main.item_user.view.*
import timber.log.Timber

class FavoriteUsersAdapter(private val userItemInteractions: UserItemInteractions) :
    ListAdapter<User, FavoriteUsersAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(inflater.inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Timber.d("Binding view holder at position $position")
        val user = getItem(position)
        user?.let { holder.bind(user, userItemInteractions) }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User, userItemInteractions: UserItemInteractions) {
            itemView.user_name.text = user.name
            itemView.user_email.text = user.email
            itemView.user_address.text = itemView.context
                .getString(R.string.user_address_content, user.street, user.city, user.state)
            itemView.user_phone.text = user.phone

            setUserImage(
                itemView.user_image,
                user.gender,
                user.largePicture
            )

            itemView.setOnClickListener {
                Timber.i("User ${user.id} clicked in position $adapterPosition")
                userItemInteractions.showUserDetail(user)
            }
        }
    }
}