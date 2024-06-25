package com.binary.memory.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * 闪卡
 */
@Parcelize
@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // 正面内容
    var front: String,
    // 背面内容
    var back: String,
    // 创建时间
    var createdTime: Long,
    // 所属闪卡组
    var flashGroupId: Int,
    // 难度
    var difficultyLevel: Int,
) : Parcelable

