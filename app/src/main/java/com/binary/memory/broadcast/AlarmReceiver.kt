package com.binary.memory.broadcast

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.binary.memory.R
import com.binary.memory.constants.Constants
import com.binary.memory.model.Task

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // 显示通知
        showNotification(context, intent)
    }

    private fun showNotification(context: Context, intent: Intent) {
        val task: Task? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable("task", Task::class.java)
        } else {
            intent.extras?.getParcelable("task")
        }

        val notificationManager = NotificationManagerCompat.from(context)

        // 创建通知
        val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(task?.title ?: "")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // 发送通知
        if (
            ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build())
    }
}