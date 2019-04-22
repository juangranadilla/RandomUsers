package com.juangm.randomusers.presentation.ui.users

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.UserModel
import com.juangm.randomusers.presentation.ui.extensions.circleImage
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(private val userClickInterface: UserClickInterface) :
    ListAdapter<UserModel, UsersAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), userClickInterface)
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(user: UserModel, userClickInterface: UserClickInterface) {
            itemView.user_name.text = user.name
            itemView.user_email.text = user.email
            itemView.user_address.text = user.street + ", " + user.city + ", " + user.state
            itemView.user_phone.text = user.phone
            itemView.user_image.circleImage(user.largePicture, 2f, Color.GRAY)

            itemView.user_image.setOnClickListener {
                userClickInterface.showUserDetail(user)
            }
        }
    }
}