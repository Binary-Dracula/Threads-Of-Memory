package com.binary.memory.constants

import android.content.Context
import com.binary.memory.R
import com.binary.memory.widgets.ViewOptionGroup

enum class EnumDifficulty : ViewOptionGroup.IOption {

    EASY {
        override fun getOptionString(context: Context): String =
            context.getString(R.string.difficulty_easy)

        override fun getColor(context: Context): Int = android.R.color.holo_green_light
    },
    MEDIUM {
        override fun getOptionString(context: Context): String =
            context.getString(R.string.difficulty_medium)

        override fun getColor(context: Context): Int = android.R.color.holo_orange_light
    },
    HARD {
        override fun getOptionString(context: Context): String =
            context.getString(R.string.difficulty_hard)

        override fun getColor(context: Context): Int = android.R.color.holo_red_light
    };

    override fun getOptionIndex(): Int = ordinal
}
