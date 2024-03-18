package com.binary.memory.utils

import java.util.Calendar

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

}