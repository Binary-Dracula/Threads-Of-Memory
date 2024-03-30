package com.binary.memory.module.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.binary.memory.R
import com.binary.memory.constants.Constants
import com.binary.memory.databinding.ListItemTaskBinding
import com.binary.memory.model.Task

class TaskListAdapter(private val tasks: MutableList<Task>) :
    RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var onItemClickListener: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemTaskBinding>(
            layoutInflater,
            R.layout.list_item_task,
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun updateTasks(newTasks: List<Task>) {
        val size = tasks.size
        tasks.clear()
        notifyItemRangeRemoved(0, size)
        if (newTasks.isNotEmpty()) {
            tasks.addAll(newTasks)
            notifyItemRangeChanged(0, newTasks.size)
        }
    }

    inner class TaskViewHolder(private val binding: ListItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.root.tag = task
            // click
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(it.tag as Task)
            }
            binding.priority.text = Constants.getPriorityString(binding.root.context, task.priority)
            binding.task = task
            binding.executePendingBindings()
        }
    }
}