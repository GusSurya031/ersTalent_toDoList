import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todolist.AlarmReceiver
import java.util.Calendar

class AlarmHelper {

    companion object {
        fun setTaskAlarm(context: Context, selectedHour: Int, selectedMinute: Int) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)  // Set jam
            calendar.set(Calendar.MINUTE, selectedMinute)    // Set menit
            calendar.set(Calendar.SECOND, 0)                 // Set detik ke 0

            val triggerTime: Long = calendar.timeInMillis  // Waktu dalam milliseconds

            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                selectedHour * 60 + selectedMinute,  // Unique requestCode
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Gunakan setAlarmClock untuk menjadwalkan alarm
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmClockInfo = AlarmManager.AlarmClockInfo(triggerTime, pendingIntent)

            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
            Log.d("AlarmHelper", "Alarm set for: $triggerTime")
        }
    }
}

