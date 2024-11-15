package com.example.todolist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmReceiver", "Alarm triggered!")
        val taskName = intent.getStringExtra("taskName") ?: "Task"
        NotificationHelper.showTaskSavedNotification(context)
        Toast.makeText(context, "Alarm for $taskName triggered!", Toast.LENGTH_SHORT).show()
    }
}
