package com.example.skindiseaseapp.data.repositories

import com.yassineabou.clock.data.model.NotificationsDataClass
import kotlinx.coroutines.flow.Flow

interface INotificationsRepository {
    suspend fun insert(notificationsDataClass: NotificationsDataClass)
    suspend fun delete(notificationsDataClass: NotificationsDataClass)
    suspend fun update(notificationsDataClass: NotificationsDataClass)
    suspend fun clear()
    fun getAlarmsList(): Flow<List<NotificationsDataClass>>
    suspend fun getAlarmById(id: Int): NotificationsDataClass?
    suspend fun getLastId(): Int?
    fun getAlarmByTime(hour: String, minute: String, recurring: Boolean): Flow<NotificationsDataClass?>
}