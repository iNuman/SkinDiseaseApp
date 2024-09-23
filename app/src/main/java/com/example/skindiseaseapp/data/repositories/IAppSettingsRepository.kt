package com.example.skindiseaseapp.data.repositories

import kotlinx.coroutines.flow.Flow

interface IAppSettingsRepository {
    suspend fun putThemeStrings(key:String, value:String)
    suspend fun getThemeStrings(key: String): Flow<String?>
}