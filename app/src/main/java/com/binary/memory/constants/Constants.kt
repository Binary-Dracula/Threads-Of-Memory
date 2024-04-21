package com.binary.memory.constants

import android.os.Build
import androidx.annotation.RequiresApi

object Constants {

    const val CHANNEL_ID = "alarm_channel_id"
    const val NOTIFICATION_ID = 1000

    const val PERMISSION_NOTIFICATION_REQUEST_CODE = 2000

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val PERMISSION_NOTIFICATION = arrayOf(
        android.Manifest.permission.POST_NOTIFICATIONS
    )
}