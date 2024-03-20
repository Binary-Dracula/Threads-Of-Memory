package com.binary.memory.module.task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.base.DraculaApplication
import com.binary.memory.databinding.ActivityTaskDetailBinding
import com.binary.memory.viewmodel.TaskViewModel
import com.binary.memory.viewmodel.TaskViewModelFactory

class TaskDetailActivity : DraculaActivity<ActivityTaskDetailBinding>() {

    private val viewModel by viewModels<TaskViewModel> {
        TaskViewModelFactory((application as DraculaApplication).taskRepository)
    }

    override fun layoutId(): Int {
        return R.layout.activity_task_detail
    }

    override fun initView() {
        initToolbar(viewBinding.toolbar.toolbar, true, R.string.task_detail)
    }

    override fun initObserver() {
        viewModel.task.observe(this) {
            viewBinding.task = it
        }
    }

    override fun initData() {
        super.initData()
        getExtra<Int>("taskId") {
            if (it == null) return
            viewModel.getTaskById(it)
        }
    }


    companion object {
        fun start(context: Context, taskId: Int) {
            val bundle = Bundle()
            bundle.putInt("taskId", taskId)
            val intent = Intent(context, TaskDetailActivity::class.java).apply {
                putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }
}