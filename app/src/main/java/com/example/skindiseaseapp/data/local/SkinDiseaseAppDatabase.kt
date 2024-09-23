package com.example.skindiseaseapp.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skindiseaseapp.data.local.dao.NotificationsDao
import com.yassineabou.clock.data.model.NotificationsDataClass

@Database(entities = [NotificationsDataClass::class], version = 1, exportSchema = false)
abstract class SkinDiseaseAppDatabase : RoomDatabase() {
    abstract fun getAlarmDao(): NotificationsDao
}
