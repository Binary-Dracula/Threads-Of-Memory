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
import com.vmadalin.easypermissions.EasyPermissions

class AddTaskActivity : DraculaActivity<ActivityAddTaskBinding>(),
    EasyPermissions.PermissionCallbacks {

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
                insertTask()
            }
        }
    }

    private fun insertTask() {
        if (EasyPermissions.hasPermissions(this, *Constants.PERMISSIONS.toTypedArray())) {
            viewModel.insertTask(
                viewBinding.taskTitle.editText?.text?.toString(),
                viewBinding.taskDescription.editText?.text?.toString()
            )
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.permission_rationale),
            Constants.PERMISSION_REQUEST_CODE,
            *Constants.PERMISSIONS.toTypedArray()
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            // 权限被拒绝，可以向用户解释为什么需要这个权限，或者采取其他措施
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                // 如果权限被永久拒绝，可以提示用户打开应用设置界面手动授权权限
            } else {
                // 如果权限被临时拒绝，可以显示一个对话框，提示用户再次请求权限
                // 或者提供其他的解决方案
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            // 权限被授予，执行插入任务的操作
            insertTask()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


}