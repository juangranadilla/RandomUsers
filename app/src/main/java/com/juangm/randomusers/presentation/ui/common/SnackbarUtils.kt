package com.juangm.randomusers.presentation.ui.common

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.juangm.randomusers.R
import timber.log.Timber

fun showSnackbar(
    view: View?,
    messageResource: Int,
    actionColor: Int,
    anchorViewResource: Int,
    actionResource: Int = R.string.snackbar_ok,
    action: (v: View) -> Unit = { Timber.i("Snackbar dismissed") }
) {
    view?.let {
        Snackbar.make(it, messageResource, Snackbar.LENGTH_LONG)
            .setAction(actionResource, action)
            .setActionTextColor(actionColor)
            .setAnchorView(anchorViewResource)
            .show()
    }
}