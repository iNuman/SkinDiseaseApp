package com.example.skindiseaseapp.di

import android.content.Context
import com.example.skindiseaseapp.data.repositories.IAppSettingsRepository
import com.example.skindiseaseapp.data.repositories.impl.UserAuthenticationRepositoryImpl
import com.example.skindiseaseapp.data.repository.AppSettingsRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.oguzdogdu.walliescompose.data.repository.IUserAuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): IUserAuthenticationRepository {
        return UserAuthenticationRepositoryImpl(auth, firestore)
    }

    @Singleton
    @Provides
    fun providesDatastoreRepo(
        @ApplicationContext context: Context
    ): IAppSettingsRepository = AppSettingsRepositoryImpl(context)

}
