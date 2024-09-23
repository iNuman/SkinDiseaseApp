package com.example.skindiseaseapp.ui.screens.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenNavigationRoute

fun NavController.navigateToHomeScreen(
    navOptions: NavOptions? = null,
) {
    navigate(route = HomeScreenNavigationRoute, navOptions)
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeScreen(
    transitionScope: SharedTransitionScope,
    onClickScanALesion: () -> Unit,
    onClickGiveFeedback: () -> Unit,
    onBodyPartItemClicked: (BodyParts) -> Unit,
    onClickTakeASkinCheck: () -> Unit,
    onAuthenticatedUserClick: () -> Unit,
    navigateBack: () -> Unit,
) {
    composable<HomeScreenNavigationRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        transitionScope.HomeScreenRoute(
            animatedVisibilityScope = this,
            viewModel = viewModel,
            onClickScanALesion = {
                onClickScanALesion.invoke()
            },
            onClickGiveFeedback = {
                onClickGiveFeedback.invoke()
            },
            onBodyPartItemClicked = {
                onBodyPartItemClicked.invoke(it)
            },
            onClickTakeASkinCheck = {
                onClickTakeASkinCheck.invoke()
            },
            navigateBack = {
                navigateBack.invoke()
            },
            onAuthenticatedUserClick = {
                onAuthenticatedUserClick.invoke()
            }
        )
    }
}