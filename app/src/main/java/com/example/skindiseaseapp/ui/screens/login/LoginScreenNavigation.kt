package com.example.skindiseaseapp.ui.screens.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.skindiseaseapp.ui.screens.login.googlesignin.GoogleAuthUiClient
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreenNavigationRoute

fun NavController.navigateToLoginScreen(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(LoginScreenNavigationRoute, navOptions)

fun NavGraphBuilder.loginScreen(
    googleAuthUiClient: GoogleAuthUiClient,
    stopOverridingBackPressForStartScreen: Boolean = false,
    navigateToHome: () -> Unit,
    onContinueWithoutLoginClick: () -> Unit,
    navigateBack: () -> Unit
) {
    composable<LoginScreenNavigationRoute> {
        LoginScreenRoute(googleAuthUiClient = googleAuthUiClient,
            stopOverridingBackPressForStartScreen = stopOverridingBackPressForStartScreen,
            navigateToHome = {
                navigateToHome.invoke()
            },
            onContinueWithoutLoginClick = {
                onContinueWithoutLoginClick.invoke()
            },
            navigateBack = {
                navigateBack.invoke()
            })
    }
}