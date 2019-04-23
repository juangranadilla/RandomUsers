package com.juangm.randomusers.presentation.ui.users

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.presentation.ui.extensions.circleImage
import kotlinx.android.synthetic.main.item_user.view.*
import timber.log.Timber

class UsersAdapter(private val userClickInterface: UserClickInterface) :
    PagedListAdapter<User, UsersAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(inflater.inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Timber.d("Binding view holder at position $position")
        holder.bind(getItem(position), userClickInterface)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User?, userClickInterface: UserClickInterface) {
            itemView.user_name.text = user?.name
            itemView.user_email.text = user?.email
            itemView.user_address.text = user?.street + ", " + user?.city + ", " + user?.state
            itemView.user_phone.text = user?.phone
            itemView.user_image.circleImage(user?.largePicture, 2f, Color.GRAY)

            user?.let {
                itemView.setOnClickListener {
                    userClickInterface.showUserDetail(user)
                }
            }
        }
    }
}