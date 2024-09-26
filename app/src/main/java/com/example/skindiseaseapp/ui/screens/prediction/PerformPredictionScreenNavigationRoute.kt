package com.example.skindiseaseapp.ui.screens.home

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.screens.scan_lesion.ScanLesionScreenNavigationRoute
import kotlinx.serialization.Serializable

@Serializable
data object PerformPredictionScreenNavigationRoute

fun NavController.navigateToPerformPredictionScreen(navOptions: NavOptions? = null, ) {
    navigate(route = PerformPredictionScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.performPredictionScreen(
    navController: NavHostController,
    onClickScanALesion: () -> Unit,
    navigateBack: () -> Unit,
) {
    composable<PerformPredictionScreenNavigationRoute> { backStackEntry ->
//        val viewModel: HomeViewModel = hiltViewModel()
        // Shared View Model for ScanLesionScreen and PerformPredictionScreen to share bitmap
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(ScanLesionScreenNavigationRoute)
        }
        val sharedViewModel = hiltViewModel<HomeViewModel>(parentEntry)
       PerformPredictionScreenRoute(
            viewModel = sharedViewModel,
            onClickScanALesion = {
                onClickScanALesion.invoke()
            },
            navigateBack = {
                navigateBack.invoke()
            },
        )
    }
}