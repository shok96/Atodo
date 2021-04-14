package ru.andlancer.todofl.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.andlancer.todofl.MainActivity
import ru.andlancer.todofl.R

import javax.inject.Inject

class BuildNotifications @Inject constructor(private val application: Context) {


    val NOTIFICATION_ID = 1

    init {
        createNotificationChannel()
    }

    fun createNotification(title: String, text: String) {

        val intent = Intent(application, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(application, 0, intent, 0)

        val builder = NotificationCompat.Builder(application, "CHANNEL_CODE")
            .setSmallIcon(R.mipmap.icon)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(text)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            //.setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(application)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test_channel"
            val descriptionText = "this is my test channel"
            val importance = IMPORTANCE_DEFAULT

            val channel = NotificationChannel("CHANNEL_CODE", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

