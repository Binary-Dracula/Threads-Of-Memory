package com.binary.memory.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * 任务
 */
@Parcelize
@Entity(tableName = "task_table")
data class Task(
    // 任务ID
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // 任务标题
    var title: String = "",
    // 任务描述
    var description: String = "",
    // 创建日期
    var createTimestamp: Long = 0,
    // 提醒日期
    var remindDate: Long = 0,
    // 提醒时间
    var remindTime: Long = 0,
    // 优先级字符串
    var priority: String = "",
    // 优先级index
    var priorityIndex: Int = 0,
    // 是否完成
    var isDone: Boolean = false
) : Parcelable