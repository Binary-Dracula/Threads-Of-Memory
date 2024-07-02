package com.binary.memory.utils

import com.binary.memory.constants.EnumDifficulty
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

object DateUtils {

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

    /**
     * 根据DifficultyLevel判定下次提醒时间
     */
    fun getNextRemindDateByDifficultyLevel(difficultyLevel: EnumDifficulty): Long {
        var triggerTimeMillis: Long = 0
        val oneDay = 60 * 60 * 24 * 1000L
        when (difficultyLevel.ordinal) {
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

    /**
     * 获取5分钟后的时间戳
     */
    fun getTimestampAfter5Minutes(): Long {
        return System.currentTimeMillis() + 5 * 60 * 1000
    }

    /**
     * 指定年月日，获取0时时间戳
     */
    fun getTimestampByDate(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    /**
     * 指定年月日，获取字符串 年-月-日
     */
    fun getDateStringByDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    /**
     * 给定小时和分钟，24小时制，获取毫秒
     */
    fun getMillisByTime(hour: Int, minute: Int): Long {
        return hour * 60 * 60 * 1000L + minute * 60 * 1000L
    }


    /**
     * 指定小时和分钟，获取小时:分钟
     */
    fun getTimeStringByTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(calendar.time)
    }

    /**
     * 时间戳获取年月日
     */
    fun getDateByTimestamp(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    /**
     * 根据毫秒获取小时分钟，24小时制
     */
    fun getTimeStringByMillis(millis: Long): String {
        val hour = millis / 1000 / 60 / 60
        val minute = (millis - hour * 1000 * 60 * 60) / 1000 / 60
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
    }
}