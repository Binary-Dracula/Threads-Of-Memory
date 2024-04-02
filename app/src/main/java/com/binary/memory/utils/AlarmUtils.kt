package com.binary.memory.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.binary.memory.base.DraculaApplication
import com.binary.memory.broadcast.AlarmReceiver
import com.binary.memory.model.Task

class AlarmUtils {

    private val requestCode = 100

    fun setAlarm(triggerTimeMillis: Long, task: Task?) {
        try {
            val alarmManager = DraculaApplication.getAppContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(DraculaApplication.getAppContext(), AlarmReceiver::class.java)
            if (task != null) {
                val bundle = Bundle()
                bundle.putParcelable("task", task)
                intent.putExtras(bundle)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                DraculaApplication.getAppContext(),
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
            }

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}