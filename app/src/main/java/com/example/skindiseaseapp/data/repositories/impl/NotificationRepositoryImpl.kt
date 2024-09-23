package com.example.skindiseaseapp.data.repositories.impl

import com.example.skindiseaseapp.data.local.dao.NotificationsDao
import com.example.skindiseaseapp.data.repositories.INotificationsRepository
import com.yassineabou.clock.data.model.NotificationsDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class NotificationRepositoryImpl @Inject constructor(private val notificationsDao: NotificationsDao) : INotificationsRepository {

    override suspend fun insert(notificationsDataClass: NotificationsDataClass) =
        notificationsDao.insert(notificationsDataClass)

    override suspend fun delete(notificationsDataClass: NotificationsDataClass) =
        notificationsDao.delete(notificationsDataClass)

    override suspend fun update(notificationsDataClass: NotificationsDataClass) =
        notificationsDao.update(notificationsDataClass)

    override suspend fun clear() = notificationsDao.clear()

    override fun getAlarmsList(): Flow<List<NotificationsDataClass>> {
        return flow {
            notificationsDao.getAlarmsList().distinctUntilChanged().collect { emit(it) }
        }
    }

    override suspend fun getAlarmById(id: Int): NotificationsDataClass? {
        return notificationsDao.getAlarmById(id)
    }

    override suspend fun getLastId(): Int? {
        return notificationsDao.getLastId()
    }

    override fun getAlarmByTime(
        hour: String,
        minute: String,
        recurring: Boolean,
    ): Flow<NotificationsDataClass?> {
        return flow {
            notificationsDao.getAlarmByTime(hour, minute, recurring).distinctUntilChanged()
                .collect { emit(it) }
        }
    }
}
