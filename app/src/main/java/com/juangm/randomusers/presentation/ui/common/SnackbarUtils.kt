package com.juangm.randomusers.presentation.ui.common

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.juangm.randomusers.R
import timber.log.Timber

fun showSnackbar(view: View?, stringResource: Int, actionColor: Int, anchorViewResource: Int) {
    view?.let {
        Snackbar.make(it, stringResource, Snackbar.LENGTH_LONG)
            .setAction(R.string.snackbar_ok) { Timber.i("Snackbar dismissed") }
            .setActionTextColor(actionColor)
            .setAnchorView(anchorViewResource)
            .show()
    }
}