package com.binary.memory.utils

import com.binary.memory.constants.EnumDifficulty

object TaskScheduler {

    fun getNextTaskTime(currentTimeMillis: Long, difficultyLevel: EnumDifficulty): Long {
        val delay = when (difficultyLevel) {
            EnumDifficulty.HARD -> 1 * 24 * 60 * 60 * 1000L // 1 day for easy
            EnumDifficulty.MEDIUM -> 2 * 24 * 60 * 60 * 1000L // 2 days for medium
            EnumDifficulty.EASY -> 7 * 24 * 60 * 60 * 1000L // 7 days for hard
        }
        return currentTimeMillis + delay
    }

}