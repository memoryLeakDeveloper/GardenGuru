package com.example.gardenguru.ui.customview

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window


class DialogHelper {

    private var dialog: AlertDialog? = null

    fun showDialog(view: View, cancelable: Boolean = true, cancelListener: DialogInterface.OnCancelListener? = null) {
        val builder = AlertDialog.Builder(view.context)
        builder.setView(view)

        dialog = builder.create()

        if (cancelListener != null) dialog!!.setOnCancelListener(cancelListener)

        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view)
        }
        with(dialog!!){
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(cancelable)

            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            show()
        }
    }

    fun hideDialog() {
        dialog?.dismiss()
    }
}