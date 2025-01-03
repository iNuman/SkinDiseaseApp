package com.example.skindiseaseapp.ui.screens.appstate

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.core.utils.helper.NetworkMonitor
import com.example.skindiseaseapp.navigation.TopLevelDestination
import com.example.skindiseaseapp.navigation.SkinDiseaseAppNavHost
import com.example.skindiseaseapp.ui.screens.login.googlesignin.GoogleAuthUiClient
import com.example.skindiseaseapp.ui.theme.medium
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SkinDiseaseApp(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor?= null,
    googleAuthUiClient: GoogleAuthUiClient,
    appState: MainAppState = rememberMainAppState(
        coroutineScope = coroutineScope,
        networkMonitor = networkMonitor
    ),
    isAuthenticated: Boolean,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val notConnectedMessage = stringResource(R.string.internet_error)

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }
    Scaffold(modifier = modifier.fillMaxSize(), bottomBar = {
        SharedTransitionLayout {
            AnimatedVisibility(
                visible = appState.shouldShowBottomBar
            ) {
                AppNavBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier = modifier
                        .renderInSharedTransitionScopeOverlay()
                        .animateEnterExit(
                            enter = fadeIn() + slideInVertically {
                                it
                            },
                            exit = fadeOut() + slideOutVertically {
                                it
                            }
                        )
                )
            }
        }
    }, snackbarHost = {
        SnackbarHost(snackbarHostState) {
            Snackbar(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(),
                containerColor = MaterialTheme.colorScheme.background
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Filled.Warning,
                        "",
                        Modifier.padding(horizontal = 8.dp),
                        tint = Yellow
                    )
                    Text(
                        it.visuals.message,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontFamily = medium, fontSize = 12.sp, maxLines = 2,
                    )
                }
            }
        }
    }) {
        SkinDiseaseAppNavHost(
            appState = appState,
            modifier = modifier.padding(it),
            stopOverridingBackPressForStartScreenLogin = true,
            isAuthenticated = isAuthenticated,
            googleAuthUiClient = googleAuthUiClient
        )
    }
}

@Composable
internal fun AppNavBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                alwaysShowLabel = true,
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = destination.icon),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = destination.iconTextId),
                        fontSize = 11.sp,
                        fontFamily = medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
        }
    }
}

@SuppressLint("RestrictedApi")
private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.hasRoute(destination.route)
    } ?: false