package com.example.skindiseaseapp.ui.screens.trackers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object TrackersScreenNavigationRoute

fun NavController.navigateToTrackersScreen(navOptions: NavOptions? = null, ) {
    navigate(route = TrackersScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.trackersScreen(onTrackerClick: (String?) -> Unit) {
    composable<TrackersScreenNavigationRoute> {
        TrackerScreenRoute(onTrackerClick = {
            onTrackerClick.invoke(it)
        })
    }
}