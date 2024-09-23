package com.example.skindiseaseapp.ui.screens.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ScanLesionScreenNavigationRoute

fun NavController.navigateToScanLesionScreen(
    navOptions: NavOptions? = null,
) {
    navigate(route = ScanLesionScreenNavigationRoute, navOptions)
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.scanLesionScreen(
    navigateBack: () -> Unit,
) {
    composable<ScanLesionScreenNavigationRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        ScanLesionScreenRoute(
            viewModel = viewModel,
            navigateBack = {
                navigateBack.invoke()
            }
        )
    }
}