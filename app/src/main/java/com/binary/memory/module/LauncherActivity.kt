package com.binary.memory.module

import android.content.Intent
import android.os.CountDownTimer
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.databinding.ActivityLauncherBinding

class LauncherActivity : DraculaActivity<ActivityLauncherBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_launcher

    override fun initView() {
        super.initView()

        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
                finish()
            }
        }
    }

}