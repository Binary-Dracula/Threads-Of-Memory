package com.binary.memory.module.task

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.binary.memory.R
import com.binary.memory.base.DraculaApplication
import com.binary.memory.base.DraculaFragment
import com.binary.memory.databinding.FragmentTaskListBinding
import com.binary.memory.module.task.adapter.TaskListAdapter
import com.binary.memory.viewmodel.TaskViewModel
import com.binary.memory.viewmodel.TaskViewModelFactory
import kotlinx.coroutines.launch

class TaskListFragment private constructor() : DraculaFragment<FragmentTaskListBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_task_list

    private val viewModel by viewModels<TaskViewModel> {
        val application = requireActivity().application as DraculaApplication
        TaskViewModelFactory(application, application.taskRepository)
    }

    // 声明TaskListAdapter
    private val adapter by lazy {
        TaskListAdapter(mutableListOf())
    }

    override fun initView() {
        viewBinding.taskList.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.taskList.adapter = adapter
        adapter.onItemClickListener = { task ->
            // 跳转到TaskDetailActivity
            TaskDetailActivity.start(requireContext(), task.id)
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.getTaskList().collect {
                adapter.setItems(
                    it,
                    areItemsTheSame = { oldTask, newTask ->
                        oldTask.id == newTask.id
                    },
                    areContentsTheSame = { oldTask, newTask ->
                        oldTask.title == newTask.title && oldTask.description == newTask.description
                    }
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskListFragment()
    }
}