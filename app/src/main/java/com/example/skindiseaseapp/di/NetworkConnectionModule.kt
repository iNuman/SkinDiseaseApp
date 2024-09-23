package com.example.skindiseaseapp.di

import com.example.skindiseaseapp.core.utils.helper.ConnectivityManagerNetworkMonitor
import com.example.skindiseaseapp.core.utils.helper.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkConnectionModule {

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
