package com.binary.memory.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "flash_group")
data class FlashGroup(
    // 主键
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // 闪卡组标题
    val flashGroupTitle: String,
    // 闪卡组描述
    val flashGroupDescription: String? = "",
    // 创建的时间
    val createDate: String? = ""
) : Parcelable