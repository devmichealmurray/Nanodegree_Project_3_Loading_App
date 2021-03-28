package com.udacity

import android.app.Application
import com.udacity.util.NotificationHelper

const val NOTIFICATION_CHANNEL_ID = "1"

class LoadApp: Application() {

    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createNotificationChannel(NOTIFICATION_CHANNEL_ID, this)
    }
}