package com.example.skindiseaseapp.ui.screens.schedules.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SKIN_APP_NOTIFICATION_ID
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SKIN_APP_NOTIFICATION_TITLE_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class ScheduleNotification @Inject constructor(
//    @ApplicationContext private val context: Context,
//    private val alarmManager: AlarmManager,
//) {
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    fun scheduleNotification(
//        year: Int,
//        month: Int,
//        day: Int,
//        hour: Int,
//        minute: Int,
//        timePickerState: TimePickerState,
//        datePickerState: DatePickerState,
//        title: String
//    ) {
//        val intent = Intent(context.applicationContext, NotificationBroadcastReceiver::class.java)
//        intent.putExtra(SKIN_APP_NOTIFICATION_TITLE_KEY, title)
//        val pendingIntent = PendingIntent.getBroadcast(
//            context.applicationContext,
//            SKIN_APP_NOTIFICATION_ID,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val selectedDate = Calendar.getInstance().apply {
//            timeInMillis = datePickerState.selectedDateMillis!!
//        }
//
//        val year = selectedDate.get(Calendar.YEAR)
//        val month = selectedDate.get(Calendar.MONTH)
//        val day = selectedDate.get(Calendar.DAY_OF_MONTH)
//
//        val calendar = Calendar.getInstance()
//        calendar.set(year, month, day, timePickerState.hour, timePickerState.minute)

//        val calendar = Calendar.getInstance().apply {
//            set(year, month, day, hour, minute)
//        }
//
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            pendingIntent
//        )
//
//        Toast.makeText(context, "Reminder set!!", Toast.LENGTH_SHORT).show()
//    }
//
//}