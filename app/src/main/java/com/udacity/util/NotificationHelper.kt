package com.udacity.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.udacity.NOTIFICATION_CHANNEL_ID
import com.udacity.R
import com.udacity.ui.DetailActivity

object NotificationHelper {

    fun createNotificationChannel(channelId: String, context: Context) {

        val notificationChannel = NotificationChannel(
            channelId,
            "Load App",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationChannel.apply {
            setShowBadge(true)
            description = "Loading App Download Notification"
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }


    fun sendNotification(context: Context, fileName: String, status: String) {
        val notificationIntent = Intent(context, DetailActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("FILE_NAME", fileName)
            putExtra("STATUS", status)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = context.let {
            NotificationCompat.Builder(it, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Load App")
                .setContentText("Your Download Has Completed")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .addAction(
                    R.drawable.ic_open,context.getString(R.string.view_details),
                    pendingIntent
                )
                .build()
        }

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(1, notification)
    }
}