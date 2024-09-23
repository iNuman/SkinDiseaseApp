package com.example.skindiseaseapp.navigation

import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.ui.screens.home.HomeScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.schedules.SchedulesScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.skin_check.SkinCheckScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.trackers.TrackersScreenNavigationRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val icon: Int,
    val iconTextId: Int,
    val route: KClass<*>,
) {
    HOME(
        icon = R.drawable.wallpaper,
        iconTextId = R.string.home,
        route = HomeScreenNavigationRoute::class
    ),
    SKIN_CHECKS(
        icon = R.drawable.collections,
        iconTextId = R.string.skin_checks,
        route = SkinCheckScreenNavigationRoute::class
    ),
    TRACKERS(
        icon = R.drawable.favorite,
        iconTextId = R.string.tracker,
        route = TrackersScreenNavigationRoute::class
    ),
    SCHEDULES(
        icon = R.drawable.settings,
        iconTextId = R.string.schedules,
        route = SchedulesScreenNavigationRoute::class
    ),
}
