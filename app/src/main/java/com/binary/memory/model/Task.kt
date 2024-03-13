package com.binary.memory.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 任务
 */
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String = "",
    var description: String = "",
    var date: String = ""
)