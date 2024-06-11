package com.binary.memory.module.task.adapter

import com.binary.memory.R
import com.binary.memory.base.DraculaAdapter
import com.binary.memory.databinding.ListItemTaskBinding
import com.binary.memory.model.Task

class TaskListAdapter(
    tasks: MutableList<Task>
) : DraculaAdapter<Task, ListItemTaskBinding>(tasks) {

    override fun getLayoutResId(): Int {
        return R.layout.list_item_task
    }

    override fun onBindData(binding: ListItemTaskBinding, item: Task) {
        binding.task = item
    }
}