package com.binary.memory.widgets

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.binary.memory.R
import com.binary.memory.databinding.DateTimeGroupBinding

class DateTimeGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: DateTimeGroupBinding

    var onDateSelectedListener: ((date: String) -> Unit)? = null
    var onTimeSelectedListener: ((time: String) -> Unit)? = null

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.date_time_group,
            this,
            true
        )

        setupDateButton()
        setupTimeButton()
    }

    private fun setupDateButton() {
        binding.dateText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val monthStr = String.format("%02d", selectedMonth + 1)
                    val dayOfMonthStr = String.format("%02d", selectedDayOfMonth)
                    val selectedDate = "$selectedYear/$monthStr/$dayOfMonthStr"
                    binding.dateText.text = selectedDate
                    onDateSelectedListener?.invoke(selectedDate)
                },
                year, month, dayOfMonth
            )
            datePickerDialog.show()
        }
    }

    private fun setupTimeButton() {
        binding.timeText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                context,
                { _, selectedHour, selectedMinute ->
                    val hourStr = String.format("%02d", selectedHour)
                    val minuteStr = String.format("%02d", selectedMinute)
                    val selectedTime = "$hourStr:$minuteStr"
                    binding.timeText.text = selectedTime
                    onTimeSelectedListener?.invoke(selectedTime)
                },
                hour, minute, true
            )
            timePickerDialog.show()
        }
    }

}