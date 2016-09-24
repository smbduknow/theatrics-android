package me.smbduknow.theatrics.ui.misc

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import me.smbduknow.theatrics.ui.base.BaseDialog
import java.util.*

class DatepickerDialog : BaseDialog<Date>(), DatePickerDialog.OnDateSetListener {

    var date: Date = Date()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false

        val c = Calendar.getInstance()
        c.time = date
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        sendResult(c.time)
    }

}