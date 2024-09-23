package com.example.skindiseaseapp.ui.screens.schedules.notifications

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SKIN_APP_NOTIFICATION_CHANNEL_ID
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SKIN_APP_NOTIFICATION_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class SkinAppNotification @Inject constructor(
//    @ApplicationContext private val context: Context,
//    private val notificationManager: NotificationManager,
//) {
//    fun sendReminderNotification(title: String?) {
//        val notification = NotificationCompat.Builder(context, SKIN_APP_NOTIFICATION_CHANNEL_ID)
//            .setContentText(context.getString(R.string.app_name))
//            .setContentTitle(title)
//            .setSmallIcon(R.mipmap.ic_launcher_round)
//            .setLargeIcon(
//                BitmapFactory.decodeResource(
//                    context.resources,
//                    R.drawable.ic_launcher_background
//                )
//            )
//            .setPriority(NotificationManager.IMPORTANCE_HIGH)
//            .setStyle(
//                NotificationCompat.BigTextStyle()
//                    .bigText("It's time for $title")
//            )
//            .setStyle(
//                NotificationCompat.BigPictureStyle()
//                    .bigPicture(context.bitmapFromResource(R.drawable.ic_launcher_background))
//            )
//            .setAutoCancel(true)
//            .build()
//
//        notificationManager.notify(SKIN_APP_NOTIFICATION_ID, notification)
//    }
//
//    private fun Context.bitmapFromResource(
//        @DrawableRes resId: Int,
//    ) = BitmapFactory.decodeResource(
//        resources,
//        resId
//    )
//
//}