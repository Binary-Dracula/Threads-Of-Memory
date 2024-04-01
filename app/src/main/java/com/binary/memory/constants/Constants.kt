package com.binary.memory.constants

import android.content.Context
import com.binary.memory.R

object Constants {

    const val CHANNEL_ID = "alarm_channel_id"
    const val NOTIFICATION_ID = 1000

    const val PERMISSION_REQUEST_CODE = 2000

    val PERMISSIONS = listOf(
        android.Manifest.permission.SET_ALARM,
        android.Manifest.permission.WAKE_LOCK,
        android.Manifest.permission.SYSTEM_ALERT_WINDOW,
        android.Manifest.permission.SCHEDULE_EXACT_ALARM
    )

    enum class Priority {
        LOW, MEDIUM, HIGH;

        fun getOrdinal(): Int {
            return when (this) {
                LOW -> 0
                MEDIUM -> 1
                HIGH -> 2
            }
        }

        fun getPriorityString(context: Context): String {
            return when (this) {
                LOW -> context.getString(R.string.low)
                MEDIUM -> context.getString(R.string.medium)
                HIGH -> context.getString(R.string.high)
            }
        }
    }

    fun getPriorityString(context: Context, index: Int): String {
        return when (index) {
            0 -> context.getString(R.string.low)
            1 -> context.getString(R.string.medium)
            2 -> context.getString(R.string.high)
            else -> {
                context.getString(R.string.low)
            }
        }
    }
}