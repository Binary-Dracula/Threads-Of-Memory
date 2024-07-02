package com.binary.memory.constants

import android.content.Context
import com.binary.memory.R
import com.binary.memory.widgets.ViewOptionGroup

enum class EnumPriority : ViewOptionGroup.IOption {
    LOW {
        override fun getOptionString(context: Context): String =
            context.getString(R.string.priority_low)

        override fun getColor(context: Context): Int = android.R.color.holo_green_light
    },
    MEDIUM {
        override fun getOptionString(context: Context): String =
            context.getString(R.string.priority_medium)

        override fun getColor(context: Context): Int = android.R.color.holo_orange_light
    },
    HIGH {
        override fun getOptionString(context: Context): String =
            context.getString(R.string.priority_high)

        override fun getColor(context: Context): Int = android.R.color.holo_red_light
    };

    override fun getOptionIndex(): Int = ordinal
}