package com.example.todolist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.time.LocalTime

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskName = intent.getStringExtra("TASK_NAME") ?: "Unknown Task"
        Log.d("AlarmReceiver", "Alarm triggered at ${LocalTime.now()}. Time to complete your task: '$taskName'!")
        NotificationHelper.showTaskSavedNotification(context)
        Toast.makeText(context, "Alarm for $taskName triggered!", Toast.LENGTH_SHORT).show()
    }
}
