package me.beingpresent.dbtaware

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

class NotificationUtils {


    fun setNotification(timeInMilliSeconds: Long, activity: Activity) {

        if (timeInMilliSeconds > 0) { //TODO WHY IS THIS NEEDED
            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, ReminderReceiver::class.java) // AlarmReceiver1 = broadcast receiver

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)


            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMilliSeconds


            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }
    }
}