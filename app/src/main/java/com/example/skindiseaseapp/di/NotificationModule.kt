package com.example.skindiseaseapp.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
//import com.example.skindiseaseapp.ui.screens.schedules.notifications.SkinAppNotification
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object NotificationModule {

//    @Provides
//    @Singleton
//    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
//        return context.getSystemService(NotificationManager::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
//        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    }
//}

//@EntryPoint
//@InstallIn(SingletonComponent::class)
//interface NotificationEntryPoint {
//    fun getReminderNotification(): SkinAppNotification
//}