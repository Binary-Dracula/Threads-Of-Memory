package com.binary.memory.base

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.binary.memory.constants.Constants
import com.binary.memory.database.DraculaRoom
import com.binary.memory.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DraculaApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {
        DraculaRoom.getDatabase(this, applicationScope)
    }
    val taskRepository by lazy {
        TaskRepository(database.taskDao())
    }

    override fun onCreate() {
        super.onCreate()
    }

    private fun createNotificationChannel() {
        val name = "Task Notification"
        val descriptionText = "Task Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}