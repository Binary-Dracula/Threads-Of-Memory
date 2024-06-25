package com.binary.memory.module

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.databinding.ActivityMainBinding
import com.binary.memory.module.flashcard.FlashGroupListFragment
import com.binary.memory.module.task.AddTaskActivity
import com.binary.memory.module.task.TaskListFragment
import com.google.android.material.navigation.NavigationBarView


class MainActivity : DraculaActivity<ActivityMainBinding>(), View.OnClickListener,
    NavigationBarView.OnItemSelectedListener {

    private val taskListFragment = TaskListFragment.newInstance()
    private val flashGroupListFragment = FlashGroupListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        // 安装启动屏幕
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        // 设置启动屏幕的退出动画
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenViewProvider.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenViewProvider.view.height.toFloat()
            )
            slideUp.interpolator = DecelerateInterpolator()
            slideUp.duration = 500L

            slideUp.doOnEnd {
                splashScreenViewProvider.remove()
            }

            slideUp.start()
        }
    }

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
        if (menuItem.itemId == R.id.task_list) {
            supportFragmentManager.beginTransaction()
                .show(taskListFragment)
                .hide(flashGroupListFragment)
                .commit()
            viewBinding.addTask.show()
        } else {
            supportFragmentManager.beginTransaction()
                .show(flashGroupListFragment)
                .hide(taskListFragment)
                .commit()
            viewBinding.addTask.hide()
        }
        return true
    }
}