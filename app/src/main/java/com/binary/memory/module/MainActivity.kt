package com.binary.memory.module

import android.content.Intent
import android.view.MenuItem
import android.view.View
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.databinding.ActivityMainBinding
import com.binary.memory.module.flashcard.FlashGroupListFragment
import com.binary.memory.module.task.AddTaskActivity
import com.binary.memory.module.task.TaskListFragment
import com.google.android.material.navigation.NavigationBarView


class MainActivity : DraculaActivity<ActivityMainBinding>(), View.OnClickListener,
    NavigationBarView.OnItemSelectedListener {

    private val taskListFragment = TaskListFragment()
    private val flashGroupListFragment = FlashGroupListFragment()

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, taskListFragment)
            .add(R.id.fragment_container, flashGroupListFragment)
            .hide(flashGroupListFragment)
            .commit()
        viewBinding.addTask.setOnClickListener(this)
        viewBinding.bottomNavigation.setOnItemSelectedListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_task -> {
                startActivity(Intent(this, AddTaskActivity::class.java))
            }
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.task_list)
            supportFragmentManager.beginTransaction()
                .show(taskListFragment)
                .hide(flashGroupListFragment)
                .commit()
        else
            supportFragmentManager.beginTransaction()
                .show(flashGroupListFragment)
                .hide(taskListFragment)
                .commit()

        return true
    }
}