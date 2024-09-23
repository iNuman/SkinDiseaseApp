package com.example.skindiseaseapp.core.utils.helper

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
