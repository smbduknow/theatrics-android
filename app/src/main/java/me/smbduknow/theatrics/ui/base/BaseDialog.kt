package me.smbduknow.theatrics.ui.base

import android.support.v4.app.DialogFragment

abstract class BaseDialog<RESULT> : DialogFragment() {

    var resultListener: ((result: RESULT) -> Unit)? = null

    protected fun sendResult(result: RESULT) {
        resultListener?.invoke(result)
        dismiss()
    }

    override fun onDestroy() {
        resultListener = null
        super.onDestroy()
    }
}