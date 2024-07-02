package com.binary.memory.module.task.adapter

import com.binary.memory.R
import com.binary.memory.base.DraculaAdapter
import com.binary.memory.constants.EnumPriority
import com.binary.memory.databinding.ListItemTaskBinding
import com.binary.memory.model.Task
import com.binary.memory.utils.DateUtils

class TaskListAdapter(
    tasks: MutableList<Task>
) : DraculaAdapter<Task, ListItemTaskBinding>(tasks) {

    override fun getLayoutResId(): Int {
        return R.layout.list_item_task
    }

    override fun onBindData(binding: ListItemTaskBinding, item: Task) {
        binding.priority.setImageResource(
            EnumPriority.valueOf(item.priority).getColor(binding.root.context)
        )
        binding.task = item
        binding.dateUtils = DateUtils
    }
}