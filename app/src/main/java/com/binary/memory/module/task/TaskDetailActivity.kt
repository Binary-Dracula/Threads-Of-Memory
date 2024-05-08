package com.binary.memory.module.task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.base.DraculaApplication
import com.binary.memory.databinding.ActivityTaskDetailBinding
import com.binary.memory.entity.DifficultyLevel
import com.binary.memory.viewmodel.TaskViewModel
import com.binary.memory.viewmodel.TaskViewModelFactory

class TaskDetailActivity : DraculaActivity<ActivityTaskDetailBinding>() {

    private val viewModel by viewModels<TaskViewModel> {
        TaskViewModelFactory(application, (application as DraculaApplication).taskRepository)
    }

    override fun layoutId(): Int {
        return R.layout.activity_task_detail
    }

    override fun initView() {
        initToolbar(viewBinding.toolbar.toolbar, true, R.string.task_detail)

        viewBinding.difficultyLevel.init(viewModel.difficultyLevelList, ::onDifficultyLevelSelected)

        viewBinding.complete.setOnClickListener(this)
        viewBinding.deleteTask.setOnClickListener(this)
    }

    override fun initObserver() {
        viewModel.task.observe(this) {
            if (it == null) return@observe
            viewBinding.task = it
        }
        viewModel.complete.observe(this) {

        }
        viewModel.done.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    override fun initData() {
        super.initData()
        getExtra<Int>("taskId") {
            if (it == null) return
            viewModel.getTaskById(it)
        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.complete -> {
                viewModel.completeTask()
            }

            R.id.delete_task -> {
                viewModel.deleteTask()
            }
        }
    }

    private fun onDifficultyLevelSelected(difficultyLevel: DifficultyLevel) {
        viewModel.setDifficultyLevel(difficultyLevel)
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