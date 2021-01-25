package com.udacity.asteroidradar.core.extensions

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udacity.asteroidradar.R
import kotlinx.android.synthetic.main.custom_toast.*
import kotlinx.android.synthetic.main.custom_toast.view.*

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

fun Fragment.inflateFragment(
    layoutResId: Int,
    container: ViewGroup?,
    inflater: LayoutInflater,
    attachToRoot: Boolean = false,
    shouldLoadWIthAnimation: Boolean = true
): View {
    val rootView = inflater.inflate(layoutResId, container, attachToRoot)
    if (shouldLoadWIthAnimation) {
        loadFragmentAnimation(rootView,
            AnimationType.FADE_IN
        )
    }
    return inflater.inflate(layoutResId, container, attachToRoot)
}

fun Fragment.loadFragmentAnimation(view: View, animationType: AnimationType) {
    view.startAnimation(
        AnimationUtils.loadAnimation(
            context,
            defineAnimation(
                animationType
            )
        )
    )
}

fun defineAnimation(animationType: AnimationType): Int {
    return when (animationType) {
        AnimationType.FADE_IN -> R.anim.fade_in_animation
        AnimationType.FADE_OUT -> R.anim.fade_out_animation
    }
}

enum class AnimationType {
    FADE_IN,
    FADE_OUT
}


fun Fragment.showAppToast(text: String = "", type: ToastType = ToastType.SUCCESS) {

    val inflater = layoutInflater

    val layout: ViewGroup = inflater.inflate(
        R.layout.custom_toast,
        customToastContainer,
        false
    ) as ViewGroup

    val imageView: ImageView = layout.findViewById(R.id.toastImage)
    layout.customText.text = text

    when(type) {
        ToastType.SUCCESS ->  imageView.setImageResource(R.drawable.ic_success)
        ToastType.INFO ->     imageView.setImageResource(R.drawable.ic_info)
        ToastType.WARNING ->  imageView.setImageResource(R.drawable.ic_warning)
        ToastType.ERROR ->    imageView.setImageResource(R.drawable.ic_error)
    }

    with(Toast(this.context)) {
        setGravity(Gravity.BOTTOM, 0, 100)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}

enum class ToastType() {
    SUCCESS,
    INFO,
    ERROR,
    WARNING
}