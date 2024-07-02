package com.binary.memory.module.task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.base.DraculaApplication
import com.binary.memory.constants.EnumDifficulty
import com.binary.memory.databinding.ActivityTaskDetailBinding
import com.binary.memory.viewmodel.TaskViewModel
import com.binary.memory.viewmodel.TaskViewModelFactory
import com.binary.memory.widgets.ConfirmDialog

class TaskDetailActivity : DraculaActivity<ActivityTaskDetailBinding>() {

    private var deleteTaskConfirmDialog: ConfirmDialog? = null
    private var completeTaskConfirmDialog: ConfirmDialog? = null

    private val viewModel by viewModels<TaskViewModel> {
        TaskViewModelFactory(application, (application as DraculaApplication).taskRepository)
    }

    override fun layoutId(): Int {
        return R.layout.activity_task_detail
    }

    override fun initView() {
        initToolbar(findViewById(R.id.toolbar), true, getString(R.string.task_detail))

        viewBinding.difficultyLevel.init(viewModel.difficultyLevelList, ::onDifficultyLevelSelected)

        viewBinding.complete.setOnClickListener(this)
        viewBinding.deleteTask.setOnClickListener(this)
    }

    override fun initObserver() {
        viewModel.task.observe(this) {
            if (it == null) return@observe
            viewBinding.task = it
            viewBinding.taskViewModel = viewModel
        }
        viewModel.complete.observe(this) {
            if (it) finish()
        }
        viewModel.done.observe(this) {
            if (it) finish()
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
                completeTaskConfirmDialog = ConfirmDialog.create(
                    this,
                    R.string.warning,
                    R.string.complete_task,
                    positiveCallback = {
                        viewModel.completeTask()
                    },
                    negativeCallback = {}
                )
            }

            R.id.delete_task -> {
                deleteTaskConfirmDialog = ConfirmDialog.create(
                    this,
                    R.string.warning,
                    R.string.delete_task,
                    positiveCallback = {
                        viewModel.deleteTask()
                    },
                    negativeCallback = {}
                )
            }
        }
    }

    private fun onDifficultyLevelSelected(difficultyLevel: EnumDifficulty) {
        viewModel.setDifficultyLevel(difficultyLevel)
    }

    override fun onDestroy() {
        completeTaskConfirmDialog?.dismiss()
        deleteTaskConfirmDialog?.dismiss()
        super.onDestroy()
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