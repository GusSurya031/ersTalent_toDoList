package com.example.todolist

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHelper {

    companion object {
        // Fungsi untuk menampilkan notifikasi
        fun showTaskSavedNotification(context: Context) {
            val channelId = "task_notification_channel"
            val channelName = "Task Notifications"

            // Membuat notification channel untuk Android 8.0 dan lebih tinggi
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
                )
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            // Membuat notifikasi
            val notification: Notification = NotificationCompat.Builder(context, channelId)
                .setContentTitle("Task Saved")
                .setContentText("Your task has been saved successfully.")
                .setSmallIcon(R.drawable.baseline_notifications_24)  // Ganti dengan ikon Anda
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            // Menampilkan notifikasi
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)  // ID notifikasi adalah 1
        }
    }
}
