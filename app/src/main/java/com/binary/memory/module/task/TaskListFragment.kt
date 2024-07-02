package com.binary.memory.module.task

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binary.memory.R
import com.binary.memory.base.DraculaApplication
import com.binary.memory.base.DraculaFragment
import com.binary.memory.constants.EnumTaskSort
import com.binary.memory.databinding.FragmentTaskListBinding
import com.binary.memory.module.task.adapter.TaskListAdapter
import com.binary.memory.viewmodel.TaskViewModel
import com.binary.memory.viewmodel.TaskViewModelFactory

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
        viewBinding.toolbar.apply {
            setTitle(R.string.task_list)
            setMenu(R.menu.menu_task_list)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem) {
                    R.id.menu_sort_task_by_create_date -> {
                        viewModel.getTaskListBySort(EnumTaskSort.CREATE_DATE)
                    }

                    R.id.menu_sort_task_by_remind_time -> {
                        viewModel.getTaskListBySort(EnumTaskSort.REMIND_TIME)
                    }

                    R.id.menu_sort_task_by_priority -> {
                        viewModel.getTaskListBySort(EnumTaskSort.PRIORITY)
                    }
                }
                true
            }
        }
        viewBinding.taskList.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.taskList.adapter = adapter
        adapter.onItemClickListener = { task ->
            // 跳转到TaskDetailActivity
            TaskDetailActivity.start(requireContext(), task.id)
        }
    }

    override fun initObserver() {
        viewModel.taskList.observe(viewLifecycleOwner) {
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

    override fun initData() {
        super.initData()
        viewModel.getTaskListBySort(EnumTaskSort.CREATE_DATE)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskListFragment()
    }
}