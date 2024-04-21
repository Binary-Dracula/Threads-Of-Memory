package com.binary.memory.entity

import com.binary.memory.widgets.ViewOptionGroup

class Priority(
    private val index: Int,
    private val content: String
) : ViewOptionGroup.IOption {
    override fun getOptionString(): String {
        return content
    }

    override fun getOptionIndex(): Int {
        return index
    }

}