package com.binary.memory.module

import android.content.Intent
import android.view.View
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.databinding.ActivityMainBinding
import com.binary.memory.module.task.AddTaskActivity
import com.binary.memory.module.task.TaskListFragment

class MainActivity : DraculaActivity<ActivityMainBinding>(), View.OnClickListener {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        viewBinding.addTask.setOnClickListener(this)

        addTaskListFragment()
    }

    override fun initObserver() {
        super.initObserver()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_task -> {
                startActivity(Intent(this, AddTaskActivity::class.java))
            }
        }
    }

    private fun addTaskListFragment() {
        val fragment = TaskListFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, fragment)
        transaction.commit()
    }

}