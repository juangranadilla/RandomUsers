package com.juangm.randomusers.presentation.ui.userdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.presentation.ui.extensions.circleImage
import com.juangm.randomusers.presentation.ui.utils.setUserImage
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.item_user.view.*

class UserDetailFragment : Fragment() {

    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = args.user
        bindUserData(user)
    }

    private fun bindUserData(user: User) {
        user_name.text = getString(R.string.user_name_content, user.name, user.surname)
        user_email.text = user.email
        user_gender.text = user.gender
        user_address.text = getString(R.string.user_address_content, user.street, user.city, user.state)
        user_registered.text = user.registered
        setUserImage(user_image, user.gender, user.largePicture)
    }
}
