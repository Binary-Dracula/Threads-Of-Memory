package com.binary.memory.utils

import com.binary.memory.entity.DifficultyLevel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

object DateUtils {

    /**
     * 获取当前日期
     */
    fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year-$month-$day"
    }

    /**
     * 获取当前时间
     */
    fun getCurrentDateTime(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        return "$year-$month-$day $hour:$minute:$second"
    }

    /**
     * 获取当前日期零点时间戳
     */
    fun getCurrentDateZeroTimestamp(): Long {
        val now = LocalDateTime.now()
        val startOfDay = now.with(now.toLocalTime().truncatedTo(ChronoUnit.DAYS))
        return startOfDay.toInstant(ZoneOffset.UTC).toEpochMilli()
    }


    /**
     * 获取当前时间戳
     */
    fun getCurrentTimestamp(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }

    fun getTriggerTimeMillis(dateString: String, timeString: String): Long {
        // 将日期字符串和时间字符串转换为对应的日期和时间对象
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val dateTime = "$dateString $timeString"
        // 处理日期字符串转换失败的情况
        val date = dateTimeFormat.parse(dateTime) ?: return 0

        // 创建 Calendar 对象并设置日期和时间
        val calendar = Calendar.getInstance().apply {
            time = date
        }

        // 返回触发时间的毫秒数
        return calendar.timeInMillis
    }

    fun dateTimestampToString(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}"
    }

    fun timeTimestampToString(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
    }

    /**
     * 根据DifficultyLevel判定下次提醒时间
     */
    fun getNextRemindDateByDifficultyLevel(difficultyLevel: DifficultyLevel?): Long {
        var triggerTimeMillis: Long = 0
        val oneDay = 60 * 60 * 24 * 1000L
        when (difficultyLevel?.level) {
            0 -> triggerTimeMillis = getCurrentDateZeroTimestamp() + oneDay * 1
            1 -> triggerTimeMillis = getCurrentDateZeroTimestamp() + oneDay * 2
            2 -> triggerTimeMillis = getCurrentDateZeroTimestamp() + oneDay * 3
        }
        return triggerTimeMillis
    }

    /**
     * 获取晚上8点的毫秒
     */
    fun get20ClockMillis(): Long {
        return 60 * 60 * 20 * 1000L
    }

}