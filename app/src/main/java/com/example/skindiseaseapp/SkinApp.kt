package com.example.skindiseaseapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.request.CachePolicy
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SKIN_APP_NOTIFICATION_CHANNEL_ID
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SKIN_APP_NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@Stable
@HiltAndroidApp
class SkinApp: Application(), ImageLoaderFactory {
    val theme = mutableStateOf("")
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .networkObserverEnabled(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache(
                DiskCache.Builder().directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(50 * 1024 * 1024).build()
            )
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                SKIN_APP_NOTIFICATION_CHANNEL_ID,
                SKIN_APP_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}