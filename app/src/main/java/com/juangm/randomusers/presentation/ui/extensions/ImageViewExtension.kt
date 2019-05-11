package com.juangm.randomusers.presentation.ui.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.juangm.randomusers.R

fun <T> ImageView.circleImage(uri: T, borderSize: Float, borderColor: Int, placeHolder: Int) {
    Glide.with(context)
        .asBitmap()
        .placeholder(placeHolder)
        .load(uri)
        .apply(RequestOptions.circleCropTransform())
        .into(object : BitmapImageViewTarget(this) {
            override fun setResource(resource: Bitmap?) {
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(
                    context.resources,
                    if (borderSize > 0) {
                        resource?.addBorder(borderSize, borderColor)
                    } else {
                        resource
                    }
                )
                circularBitmapDrawable.isCircular = true
                setImageDrawable(circularBitmapDrawable)
            }
        })
}