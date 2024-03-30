package com.binary.memory.module.task

import android.view.View
import androidx.activity.viewModels
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.base.DraculaApplication
import com.binary.memory.constants.Constants
import com.binary.memory.databinding.ActivityAddTaskBinding
import com.binary.memory.viewmodel.TaskViewModel
import com.binary.memory.viewmodel.TaskViewModelFactory

class AddTaskActivity : DraculaActivity<ActivityAddTaskBinding>() {

    private val viewModel by viewModels<TaskViewModel> {
        TaskViewModelFactory((application as DraculaApplication).taskRepository)
    }

    override fun layoutId(): Int {
        return R.layout.activity_add_task
    }

    override fun initView() {
        super.initView()
        initToolbar(viewBinding.toolbar.toolbar, true, R.string.add_task)
        addClickListener(viewBinding.addTask)

        viewBinding.priorityGroup.setPriority(
            listOf(
                Constants.Priority.LOW,
                Constants.Priority.MEDIUM,
                Constants.Priority.HIGH
            )
        )
        viewBinding.priorityGroup.setOnPriorityClickListener { priority ->
            viewModel.setPriority(priority)
        }
        viewBinding.dateTimeGroup.onDateSelectedListener = { date ->
            viewModel.setNotifyDate(date)
        }
        viewBinding.dateTimeGroup.onTimeSelectedListener = { time ->
            viewModel.setNotifyTime(time)
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.done.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.add_task -> {
                viewModel.insertTask(
                    viewBinding.taskTitle.editText?.text?.toString(),
                    viewBinding.taskDescription.editText?.text?.toString()
                )
            }
        }
    }
}