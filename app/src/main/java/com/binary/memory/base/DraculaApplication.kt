package com.binary.memory.base

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.binary.memory.constants.Constants
import com.binary.memory.database.DraculaRoom
import com.binary.memory.repository.FlashcardRepository
import com.binary.memory.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DraculaApplication : Application() {

    companion object {
        private lateinit var instance: DraculaApplication

        // 提供一个静态方法来获取全局的 Context
        fun getAppContext(): Context {
            return instance.applicationContext
        }
    }

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {
        DraculaRoom.getDatabase(this, applicationScope)
    }
    val taskRepository by lazy {
        TaskRepository(database.taskDao())
    }

    val flashcardRepository by lazy {
        FlashcardRepository(database.flashcardDao())
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        createNotificationChannel()
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
        notificationManager.createNotificationChannel(channel)
    }

}