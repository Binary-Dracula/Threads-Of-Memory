package com.binary.memory.entity

import com.binary.memory.widgets.ViewOptionGroup

class DifficultyLevel(
    val level: Int,
    val name: String
) : ViewOptionGroup.IOption {
    override fun getOptionString(): String {
        return name
    }

    override fun getOptionIndex(): Int {
        return level
    }
}