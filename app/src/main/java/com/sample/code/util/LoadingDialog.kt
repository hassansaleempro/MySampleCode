package com.sample.code.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.sample.code.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoadingDialog @Inject constructor(
    @ApplicationContext private val context: Context) {

    private var dialog: Dialog? = null

    fun showLoadingDialog() {
        if (dialog == null) {
            dialog = Dialog(context)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
            dialog?.setContentView(R.layout.custom_loading_dialog_layout)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog?.show()
    }

    fun dismissLoadingDialog() {
        dialog?.dismiss()
    }
}