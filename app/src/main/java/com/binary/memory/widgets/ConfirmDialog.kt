package com.binary.memory.widgets

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.binary.memory.R

class ConfirmDialog private constructor(){

    companion object{
        fun create(context: Context,
                   @StringRes title: Int,
                   @StringRes message: Int,
                   positiveCallback: () -> Unit,
                   negativeCallback: () -> Unit): ConfirmDialog {
            return ConfirmDialog().apply {
                this.context = context
                this.title = title
                this.message = message
                this.positiveCallback = positiveCallback
                this.negativeCallback = negativeCallback
            }
        }
    }

    private var alertDialog: AlertDialog? = null

    private lateinit var context: Context
    private var title: Int = 0
    private var message: Int = 0
    private lateinit var positiveCallback: () -> Unit
    private lateinit var negativeCallback: () -> Unit

    fun showDialog() {
        alertDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> positiveCallback.invoke() }
            .setNegativeButton(R.string.cancel) { _, _ -> negativeCallback.invoke() }
            .create()
        alertDialog?.show()
    }

    fun dismiss() {
        alertDialog?.dismiss()
    }

}