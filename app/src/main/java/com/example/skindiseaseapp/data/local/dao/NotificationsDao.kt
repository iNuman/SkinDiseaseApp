package com.example.skindiseaseapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yassineabou.clock.data.model.NotificationsDataClass
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notificationsDataClass: NotificationsDataClass)

    @Delete
    suspend fun delete(notificationsDataClass: NotificationsDataClass)

    @Update
    suspend fun update(notificationsDataClass: NotificationsDataClass)

    @Query("DELETE FROM alarms_list_table")
    suspend fun clear()

    @Query("SELECT * FROM alarms_list_table ORDER BY id DESC")
    fun getAlarmsList(): Flow<List<NotificationsDataClass>>

    @Query("SELECT * FROM alarms_list_table WHERE id=:id")
    suspend fun getAlarmById(id: Int): NotificationsDataClass?

    @Query("SELECT id FROM alarms_list_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastId(): Int?

    @Query("SELECT * FROM alarms_list_table WHERE hour=:hour AND minute=:minute AND isRecurring=:recurring")
    fun getAlarmByTime(hour: String, minute: String, recurring: Boolean): Flow<NotificationsDataClass?>
}
