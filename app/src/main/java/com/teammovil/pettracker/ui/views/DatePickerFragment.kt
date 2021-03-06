package com.teammovil.pettracker.ui.views

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.teammovil.pettracker.R
import java.util.*

class DatePickerFragment(val idCaller: Int) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var listener: DatePickerFragmentListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val date = getString(R.string.string_format_date, year, month+1, day)
        listener?.saveDate(date, idCaller)
        this.dismiss()
    }

    interface DatePickerFragmentListener {
        fun saveDate (date: String, idCaller: Int)
    }
}