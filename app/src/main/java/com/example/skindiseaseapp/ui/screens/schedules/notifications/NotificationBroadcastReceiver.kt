package com.example.skindiseaseapp.ui.screens.schedules.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SKIN_APP_NOTIFICATION_TITLE_KEY
//import com.example.skindiseaseapp.di.NotificationEntryPoint
import dagger.hilt.android.EntryPointAccessors

class NotificationBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        val hiltEntryPoint = context?.let {
//            EntryPointAccessors.fromApplication(it, NotificationEntryPoint::class.java)
//        }
//        val reminderNotification = hiltEntryPoint?.getReminderNotification()
//        val title: String? = intent?.getStringExtra(SKIN_APP_NOTIFICATION_TITLE_KEY)
//        reminderNotification?.sendReminderNotification(title)
    }
}