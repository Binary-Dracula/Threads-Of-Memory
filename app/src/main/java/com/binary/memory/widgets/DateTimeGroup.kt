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
import com.binary.memory.utils.DateUtils

class DateTimeGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: DateTimeGroupBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.date_time_group,
        this,
        true
    )

    var onDateSelectedListener: ((date: Long) -> Unit)? = null
    var onTimeSelectedListener: ((time: Long) -> Unit)? = null

    init {

        setupDateButton()
        setupTimeButton()
    }

    private fun setupDateButton() {
        binding.dateLayout.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    binding.dateText.text = DateUtils.getDateStringByDate(
                        selectedYear,
                        selectedMonth,
                        selectedDayOfMonth
                    )
                    onDateSelectedListener?.invoke(
                        DateUtils.getTimestampByDate(
                            selectedYear,
                            selectedMonth,
                            selectedDayOfMonth
                        )
                    )
                },
                year, month, dayOfMonth
            )
            datePickerDialog.show()
        }
    }

    private fun setupTimeButton() {
        binding.timeLayout.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                context,
                { _, selectedHour, selectedMinute ->
                    binding.timeText.text =
                        DateUtils.getTimeStringByTime(selectedHour, selectedMinute)
                    onTimeSelectedListener?.invoke(
                        DateUtils.getMillisByTime(
                            selectedHour,
                            selectedMinute
                        )
                    )
                },
                hour, minute, true
            )
            timePickerDialog.show()
        }
    }

}