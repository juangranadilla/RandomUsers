package com.juangm.randomusers.presentation.ui.utils

import android.graphics.Color
import android.widget.ImageView
import com.juangm.randomusers.R
import com.juangm.randomusers.presentation.ui.extensions.circleImage

fun setUserImage(userImageView: ImageView, gender: String, pictureUrl: String) {
    when(gender) {
        "male" -> userImageView
            .circleImage(pictureUrl, 2f, Color.GRAY, R.drawable.ic_default_user_image_male)
        "female" -> userImageView
            .circleImage(pictureUrl, 2f, Color.GRAY, R.drawable.ic_default_user_image_female)
    }
}