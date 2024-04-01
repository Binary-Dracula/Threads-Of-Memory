package com.binary.memory.utils

import java.text.SimpleDateFormat
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
     * 获取当前时间戳
     */
    fun getCurrentMilliSecond(): Long {
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

}