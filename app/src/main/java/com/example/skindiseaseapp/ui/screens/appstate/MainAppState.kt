package com.example.skindiseaseapp.ui.screens.appstate

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.skindiseaseapp.core.utils.helper.NetworkMonitor
import com.example.skindiseaseapp.navigation.TopLevelDestination
import com.example.skindiseaseapp.ui.screens.home.HomeScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.home.navigateToHomeScreen
import com.example.skindiseaseapp.ui.screens.schedules.SchedulesScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.schedules.navigateToSettingsScreen
import com.example.skindiseaseapp.ui.screens.skin_check.SkinCheckScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.skin_check.navigateSkinChecksScreen
import com.example.skindiseaseapp.ui.screens.trackers.TrackersScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.trackers.navigateToTrackersScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor?= null,
): MainAppState {
    return remember(navController) {
        MainAppState(navController, coroutineScope, networkMonitor!!)
    }
}

@Stable
class MainAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    private val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return when {
                currentDestination?.hasRoute<HomeScreenNavigationRoute>() == true -> TopLevelDestination.HOME
                currentDestination?.hasRoute<SkinCheckScreenNavigationRoute>() == true -> TopLevelDestination.SKIN_CHECKS
                currentDestination?.hasRoute<TrackersScreenNavigationRoute>() == true -> TopLevelDestination.TRACKERS
                currentDestination?.hasRoute<SchedulesScreenNavigationRoute>() == true -> TopLevelDestination.SCHEDULES
                else -> null
            }
        }

    val shouldShowBottomBar: Boolean
        @SuppressLint("RestrictedApi") @Composable get() = currentDestination?.hierarchy?.any { destination ->
            currentTopLevelDestination?.let {
                destination.hasRoute(it.route)
            } ?: false
        } ?: false


    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries


    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = false
            }
            launchSingleTop = true
            restoreState = false
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHomeScreen(topLevelNavOptions)
            TopLevelDestination.SKIN_CHECKS -> navController.navigateSkinChecksScreen(
                topLevelNavOptions
            )

            TopLevelDestination.TRACKERS -> navController.navigateToTrackersScreen(
                topLevelNavOptions
            )

            TopLevelDestination.SCHEDULES -> navController.navigateToSettingsScreen(
                topLevelNavOptions
            )
        }
    }

    fun onBackPress() {
        navController.popBackStack()

    }
    fun canNavigateBack(): Boolean {
        return navController.previousBackStackEntry != null
    }
}