package com.example.skindiseaseapp.di

import android.content.Context
import androidx.room.Room
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SKIN_APP_DATABASE
import com.example.skindiseaseapp.data.local.dao.NotificationsDao
import com.example.skindiseaseapp.data.local.SkinDiseaseAppDatabase
import com.example.skindiseaseapp.data.repositories.INotificationsRepository
import com.example.skindiseaseapp.data.repositories.ISkinAppRepository
import com.example.skindiseaseapp.data.repositories.impl.NotificationRepositoryImpl
import com.example.skindiseaseapp.data.repository.SkinAppRepositoryImpl
import com.oguzdogdu.walliescompose.data.di.Dispatcher
import com.oguzdogdu.walliescompose.data.di.SkinAppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAlarmDatabase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        SkinDiseaseAppDatabase::class.java,
        SKIN_APP_DATABASE,
    ).build()

    @Singleton
    @Provides
    fun provideAlarmDao(skinDiseaseAppDatabase: SkinDiseaseAppDatabase): NotificationsDao {
        return skinDiseaseAppDatabase.getAlarmDao()
    }

    @Singleton
    @Provides
    fun providesNotificationsRepo(notificationsDao: NotificationsDao): INotificationsRepository {
        return NotificationRepositoryImpl(notificationsDao)
    }

    @Singleton
    @Provides
    fun provideSkinAppRepo(@Dispatcher(SkinAppDispatchers.IO) ioDispatcher: CoroutineDispatcher): ISkinAppRepository = SkinAppRepositoryImpl(ioDispatcher)
//    @Singleton
//    @Provides
//    fun provideSkinAppRepo(): ISkinAppRepository = SkinAppRepositoryImpl()
}
