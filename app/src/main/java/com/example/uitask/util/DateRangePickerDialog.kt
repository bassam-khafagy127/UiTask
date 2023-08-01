package com.example.uitask.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.uitask.databinding.DialogDateRangePickerBinding
import java.util.Calendar

class DateRangePickerDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogDateRangePickerBinding
    private var startDate: Calendar? = null
    private var endDate: Calendar? = null
    private var onDateRangeSelectedListener: OnDateRangeSelectedListener? = null

    interface OnDateRangeSelectedListener {
        fun onDateRangeSelected(startDate: Calendar, endDate: Calendar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDateRangePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeDateRangePicker()

        binding.btnApply.setOnClickListener {
            onDateRangeSelectedListener?.let {
                it.onDateRangeSelected(
                    startDate ?: Calendar.getInstance(),
                    endDate ?: Calendar.getInstance()
                )
            }
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initializeDateRangePicker() {
        // Initialize the date range picker here (e.g., set default dates or date range limits)
        // You can use any date picker library or implement your custom date range picker.
    }

    fun setOnDateRangeSelectedListener(listener: OnDateRangeSelectedListener) {
        this.onDateRangeSelectedListener = listener
    }

    fun setSelectedDates(startDate: Calendar?, endDate: Calendar?) {
        this.startDate = startDate
        this.endDate = endDate
    }
}
