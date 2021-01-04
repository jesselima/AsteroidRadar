package com.udacity.asteroidradar.core.extensions

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udacity.asteroidradar.R

fun Fragment.showDialogWithActions(
    context: Context,
    title: String = resources.getString(R.string.attention),
    message: String = resources.getString(R.string.something_went_wrong),
    positiveButtonText: String = resources.getString(R.string.label_ok),
    positiveButtonAction: (() -> Unit?)? = null,
    negativeButtonText: String = resources.getString(R.string.label_cancel),
    negativeButtonAction: (() -> Unit?)? = null
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { _, _ ->
            positiveButtonAction?.invoke()
        }
        .setNegativeButton(negativeButtonText) { _, _ ->
            negativeButtonAction?.invoke()
        }
        .show()
}

fun Fragment.showDialog(
    context: Context,
    title: String = resources.getString(R.string.attention),
    message: String = resources.getString(R.string.something_went_wrong),
    positiveButtonText: String = resources.getString(R.string.label_ok),
    positiveButtonAction: (() -> Unit?)? = null,
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { _, _ ->
            positiveButtonAction?.invoke()
        }
        .show()
}