package com.binary.memory.module.task

import android.os.Build
import android.view.View
import androidx.activity.viewModels
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.base.DraculaApplication
import com.binary.memory.constants.Constants
import com.binary.memory.databinding.ActivityAddTaskBinding
import com.binary.memory.entity.Priority
import com.binary.memory.viewmodel.TaskViewModel
import com.binary.memory.viewmodel.TaskViewModelFactory
import com.vmadalin.easypermissions.EasyPermissions

class AddTaskActivity : DraculaActivity<ActivityAddTaskBinding>() {

    private val viewModel by viewModels<TaskViewModel> {
        TaskViewModelFactory(application, (application as DraculaApplication).taskRepository)
    }

    override fun layoutId(): Int {
        return R.layout.activity_add_task
    }

    override fun initView() {
        super.initView()
        initToolbar(findViewById(R.id.toolbar), true, getString(R.string.add_task))
        addClickListener(viewBinding.addTask)

        viewBinding.priorityGroup.init(viewModel.priorityList) { priority ->
            viewModel.setPriority(priority as Priority)
        }

        viewBinding.dateTimeGroup.onDateSelectedListener = { date ->
            viewModel.setNotifyDate(date)
        }
        viewBinding.dateTimeGroup.onTimeSelectedListener = { time ->
            viewModel.setNotifyTime(time)
        }

        requestPermissionPostNotification()
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
                insertTask()
            }
        }
    }

    private fun requestPermissionPostNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !EasyPermissions.hasPermissions(this, *Constants.PERMISSION_NOTIFICATION)
        ) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_rationale),
                Constants.PERMISSION_NOTIFICATION_REQUEST_CODE,
                *Constants.PERMISSION_NOTIFICATION
            )
        }
    }

    private fun insertTask() {
        viewModel.insertTask(
            viewBinding.taskTitle.editText?.text?.toString(),
            viewBinding.taskDescription.editText?.text?.toString()
        )
    }

}