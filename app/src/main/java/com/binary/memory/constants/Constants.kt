package com.binary.memory.constants

import android.content.Context
import com.binary.memory.R

object Constants {

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