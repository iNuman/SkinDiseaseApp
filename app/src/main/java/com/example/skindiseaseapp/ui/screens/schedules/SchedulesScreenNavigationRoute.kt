package com.example.skindiseaseapp.ui.screens.schedules


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SchedulesScreenNavigationRoute

fun NavController.navigateToSettingsScreen(
    navOptions: NavOptions? = null,
) {
    navigate(route = SchedulesScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.schedulesScreen(
    onClickAddSchedule: () -> Unit,
    onClickEditSchedule: () -> Unit,
) {
    composable<SchedulesScreenNavigationRoute> {
        SchedulesScreenRoute(onClickAddSchedule = {
            onClickAddSchedule.invoke()
        }, onClickEditSchedule = {
            onClickEditSchedule.invoke()
        })
    }
}