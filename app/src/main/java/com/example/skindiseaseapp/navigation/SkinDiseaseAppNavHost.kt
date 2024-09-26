package com.example.skindiseaseapp.navigation

import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.example.skindiseaseapp.navigation.utils.Routes
import com.example.skindiseaseapp.ui.screens.appstate.MainAppState
import com.example.skindiseaseapp.ui.screens.home.HomeScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.home.homeScreen
import com.example.skindiseaseapp.ui.screens.home.navigateToPerformPredictionScreen
import com.example.skindiseaseapp.ui.screens.home.performPredictionScreen
import com.example.skindiseaseapp.ui.screens.login.LoginScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.login.googlesignin.GoogleAuthUiClient
import com.example.skindiseaseapp.ui.screens.login.loginScreen
import com.example.skindiseaseapp.ui.screens.scan_lesion.ScanLesionScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.scan_lesion.navigateToScanLesionScreen
import com.example.skindiseaseapp.ui.screens.scan_lesion.scanLesionScreen
import com.example.skindiseaseapp.ui.screens.schedules.CreateSchedulesScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.schedules.SchedulesScreenNavigationRoute
import com.example.skindiseaseapp.ui.screens.schedules.SchedulesScreenRoute
import com.example.skindiseaseapp.ui.screens.schedules.createSchedulesScreen
import com.example.skindiseaseapp.ui.screens.schedules.navigateToCreateScheduleScreen
import com.example.skindiseaseapp.ui.screens.schedules.schedulesScreen
import com.example.skindiseaseapp.ui.screens.skin_check.skinCheckScreen
import com.example.skindiseaseapp.ui.screens.splash.SplashViewModel
import com.example.skindiseaseapp.ui.screens.trackers.trackersScreen
import com.example.skindiseaseapp.ui.splash.SplashScreenState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SkinDiseaseAppNavHost(
    appState: MainAppState,
    modifier: Modifier = Modifier,
    stopOverridingBackPressForStartScreenLogin: Boolean,
    isAuthenticated: Boolean,
    googleAuthUiClient: GoogleAuthUiClient,
    viewModel: SplashViewModel = hiltViewModel(),
) {

    val navController = appState.navController

    var stopOverridingBackPressForStartScreen by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = stopOverridingBackPressForStartScreenLogin) {
        stopOverridingBackPressForStartScreen = stopOverridingBackPressForStartScreenLogin
    }

    var authStateFireBase by remember {
        mutableStateOf(false)
    }
    var authStateInGoogle by remember {
        mutableStateOf(false)
    }

    val state by viewModel.splashState.collectAsStateWithLifecycle()
    LaunchedEffect(state) {
        when (state) {
            SplashScreenState.StartFlow -> {}
            SplashScreenState.UserNotSigned -> {}
            SplashScreenState.UserSignedIn -> {
            }
        }
    }
    LaunchedEffect(key1 = isAuthenticated) {
        authStateFireBase = isAuthenticated
    }
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination =
            if (!authStateFireBase) {
                Routes.Auth
            } else {
                Routes.Home
            },
            modifier = modifier
        ) {
            navigation<Routes.Auth>(startDestination = LoginScreenNavigationRoute) {
                loginScreen(googleAuthUiClient = googleAuthUiClient,
                    stopOverridingBackPressForStartScreen = stopOverridingBackPressForStartScreen,
                    navigateToHome = {
                        navController.navigate(HomeScreenNavigationRoute) {
                            popUpTo(LoginScreenNavigationRoute) {
                                inclusive = true
                            }
                        }
                    },
                    onContinueWithoutLoginClick = {
                        navController.navigate(HomeScreenNavigationRoute) {
                            popUpTo(LoginScreenNavigationRoute) {
                                inclusive = true
                            }
                        }
                    },
                    navigateBack = {
                        navController.navigateUp()
                    })

            }
            navigation<Routes.Home>(startDestination = HomeScreenNavigationRoute) {
                homeScreen(
                    transitionScope = this@SharedTransitionLayout,
                    onClickScanALesion = {
                        navController.navigateToScanLesionScreen()
                    },
                    onBodyPartItemClicked = { bodyPart ->

                    },
                    onClickTakeASkinCheck = {

                    },
                    onClickGiveFeedback = {

                    },
                    onAuthenticatedUserClick = {
//                        navController.navigateToAuthenticatedUserScreen()
                    },
                    navigateBack = {
                        appState.onBackPress()
//                        navController.navigateUp()
                    }
                )
                scanLesionScreen(
                    navigateBack = {
//                    navController.navigate(SchedulesScreenNavigationRoute) {
//                        popUpTo(HomeScreenNavigationRoute) {
//                            inclusive = false
//                        }
//                    }
                        navController.navigateUp()
                    },
                    onClickUsePhoto = {
                        navController.navigateToPerformPredictionScreen()
                    },
                )

                performPredictionScreen(
                    navController = navController,
                    navigateBack = {
                        navController.navigate(HomeScreenNavigationRoute) {
                            popUpTo(ScanLesionScreenNavigationRoute) {
                                inclusive = false
                            }
                        }
                    },
                    onClickScanALesion = {
                    }
                )

                skinCheckScreen(
                    onBodyPartItemClicked = { bodyPart ->
//                        navController.navigateToCollectionDetailListScreen(
//                            collectionDetailListId = id,
//                            collectionDetailListTitle = title
//                        )
                    },
                    onClickStartSkinCheck = {
                    }
                )
                trackersScreen(onTrackerClick = {
//                    navController.navigateToDetailScreen(photoId = it)
                })
                schedulesScreen(
                    onClickAddSchedule = {
                        navController.navigateToCreateScheduleScreen()
                    },
                    onClickEditSchedule = {
                        navController.navigateToCreateScheduleScreen()
                    }
                )
                createSchedulesScreen(
                    onBackOrCancelClick = {
                        navController.navigateUp()
                    },
                    saveScheduleClick = {
                        navController.navigateUp()
                    },
                    onClickNoneLesionCard = {
                    })
            }
        }
    }
}