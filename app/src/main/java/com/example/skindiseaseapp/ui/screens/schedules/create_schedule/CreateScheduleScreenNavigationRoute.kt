package com.example.skindiseaseapp.ui.screens.schedules


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import com.example.skindiseaseapp.ui.screens.schedules.create_schedule.CreateScheduleScreenRoute

@Serializable
data object CreateSchedulesScreenNavigationRoute

fun NavController.navigateToCreateScheduleScreen(
    navOptions: NavOptions? = null,
) {
    navigate(route = CreateSchedulesScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.createSchedulesScreen(
    onBackOrCancelClick: () -> Unit,
    saveScheduleClick: () -> Unit,
    onClickNoneLesionCard: () -> Unit,
) {
    composable<CreateSchedulesScreenNavigationRoute> {
        CreateScheduleScreenRoute(
            onBackOrCancelClick = { onBackOrCancelClick.invoke() },
            saveScheduleClick = { saveScheduleClick.invoke() },
            onClickNoneLesionCard = { saveScheduleClick.invoke() }
        )
    }
}