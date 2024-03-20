package com.binary.memory.module.task

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binary.memory.R
import com.binary.memory.base.DraculaApplication
import com.binary.memory.base.DraculaFragment
import com.binary.memory.databinding.FragmentTaskListBinding
import com.binary.memory.module.task.adapter.TaskListAdapter
import com.binary.memory.viewmodel.TaskViewModel
import com.binary.memory.viewmodel.TaskViewModelFactory

class TaskListFragment : DraculaFragment<FragmentTaskListBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_task_list

    private val viewModel by viewModels<TaskViewModel> {
        val application = requireActivity().application as DraculaApplication
        TaskViewModelFactory(application.taskRepository)
    }

    // 声明TaskListAdapter
    private val adapter by lazy {
        TaskListAdapter(mutableListOf())
    }

    override fun initView() {
        view.taskList.layoutManager = LinearLayoutManager(requireContext())
        view.taskList.adapter = adapter
        adapter.onItemClickListener = { task ->
            // 跳转到TaskDetailActivity
            TaskDetailActivity.start(requireContext(), task.id)
        }
    }

    override fun initObserver() {
        viewModel.taskList.observe(viewLifecycleOwner) {
            adapter.updateTasks(it)
        }
    }

    override fun initData() {
        viewModel.getAllTasks()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskListFragment()
    }
}