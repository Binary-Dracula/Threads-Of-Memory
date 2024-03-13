package com.binary.memory.base

import android.app.Application
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

}