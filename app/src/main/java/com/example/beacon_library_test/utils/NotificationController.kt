package com.example.beacon_library_test.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.example.beacon_library_test.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationController @Inject constructor(
    private val context: Context
) {

    var def_message = "Scanning for Beacons"

    val notificationBuilder = Notification.Builder(context).also {
        it.setSmallIcon(com.google.android.material.R.drawable.ic_clock_black_24dp)
    }

    val notificationManager =
        context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotificationForService(message: String? = null, isHighImportance: Boolean = false) =
        notificationBuilder.also {
            it.setContentTitle(message ?: def_message)

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
                else PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

            it.setContentIntent(pendingIntent)

            val channel = NotificationChannel(
                "Is Bacon Id",
                "is Beacon background",
                if (isHighImportance) NotificationManager.IMPORTANCE_HIGH else NotificationManager.IMPORTANCE_MIN
            )

            notificationManager.createNotificationChannel(channel)
            it.setChannelId(channel.id)
        }

    fun notify(mes: String, isImportant: Boolean) {
        with(NotificationManagerCompat.from(context)) {
            notify(
                if (isImportant) notificationIDHighImportance else notificationIDLowImportance,
                createNotificationForService(mes, isImportant).build()
            )
        }
    }

    companion object {
        const val notificationIDLowImportance = 345
        const val notificationIDHighImportance = 776
    }
}
