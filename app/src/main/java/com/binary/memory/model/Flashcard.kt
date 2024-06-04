package com.binary.memory.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 闪卡
 */
@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // 正面内容
    val front: String,
    // 背面内容
    val back: String,
    // 创建时间
    val createdTime: Long,
    // 所属闪卡组
    val flashGroupId: Int
)

