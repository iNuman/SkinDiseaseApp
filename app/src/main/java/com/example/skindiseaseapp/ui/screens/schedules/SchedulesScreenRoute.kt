package com.example.skindiseaseapp.ui.screens.schedules


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.ui.screens.common.CommonFloatingButton
import com.example.skindiseaseapp.ui.screens.common.CommonTopAppBar
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.screens.schedules.components.RowOfSettingOptions
import com.example.skindiseaseapp.ui.screens.schedules.components.SingleSelectDialog
import com.example.skindiseaseapp.ui.theme.*
import com.example.skindiseaseapp.ui.theme.bold
import com.example.skindiseaseapp.ui.theme.medium
import com.example.skindiseaseapp.ui.theme.regular
import com.example.skindiseaseapp.ui.theme.settings.ThemeValues
import com.example.skindiseaseapp.ui.util.BaseLazyColumn
import com.example.skindiseaseapp.ui.util.ListItem
import com.example.skindiseaseapp.ui.util.ListProperties
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.oguzdogdu.walliescompose.features.settings.SchedulesScreenEvent
import com.oguzdogdu.walliescompose.util.ReusableMenuRowLists
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

typealias onSchedulesScreenEvent = (SchedulesScreenEvent) -> Unit

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SchedulesScreenRoute(
    modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel(),
    onClickAddSchedule: () -> Unit,
    onClickEditSchedule: () -> Unit,
) {
    val schedulesUiState by viewModel.settingsState.collectAsStateWithLifecycle()
    val schedulesSnackBarState by viewModel.snackBarState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = schedulesSnackBarState.showSnackBar) {
        when (schedulesSnackBarState.showSnackBar) {
            true -> snackState.showSnackbar("Cache directory deleted successfully")
            false -> snackState.showSnackbar("Error deleting cache directory")
            else -> return@LaunchedEffect
        }
    }

    val notificationPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.POST_NOTIFICATIONS
    )

    var isPermissionGranted by remember { mutableStateOf(false) }

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || notificationPermissionState.status.isGranted) {
        isPermissionGranted = true
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackState
            )
        },
        topBar = {
            CommonTopAppBar(
                showTitle = false,
                showSearchIcon = false,
                showDefaultAvatar = false,
                defaultAvatar = null
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isPermissionGranted) {
                NotificationEnableButton(modifier = Modifier, onClick = {
                    notificationPermissionState.launchPermissionRequest()

                })
            } else {
                NotificationSchedulesTitle()
                NoSchedulesContent(modifier = modifier, onClickAddSchedule= {
                    onClickAddSchedule.invoke()
                }, onClickEditSchedule = {
                    onClickEditSchedule.invoke()
                })
            }
        }
    }
}

@Composable
fun NotificationEnableButton(modifier: Modifier, onClick: () -> Unit = {}) {

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Request Notifications Permission",
                modifier = modifier
                    .size(64.sdp)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Button(
                onClick = { onClick.invoke() },
                contentPadding = ButtonDefaults.ContentPadding,
                shape = RoundedCornerShape(8.sdp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryContainerDark
                )
            ) {
                Text("Enable Notifications")
            }
        }
    }
}

@Composable
fun NotificationSchedulesTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 9.sdp, top = 12.sdp, bottom = 5.sdp, end = 9.sdp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.notification_schedules_title),
                fontSize = 18.ssp,
                fontFamily = bold,
                fontWeight = FontWeight.W600,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun NoSchedulesContent(modifier: Modifier = Modifier, onClickAddSchedule: () -> Unit = {}, onClickEditSchedule: () -> Unit = {}) {
    Box(modifier = modifier.fillMaxSize()) { // Use a Box to allow positioning
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 12.sdp, top = 12.sdp, bottom = 45.sdp, end = 12.sdp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Request Notifications Permission",
                modifier = Modifier
                    .size(100.sdp)
            )
            Spacer(modifier = modifier.size(12.sdp))
            Text(
                text = stringResource(R.string.no_schedules_title),
                fontSize = 18.ssp,
                fontFamily = bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = Color.Black,
            )
            Spacer(modifier = modifier.size(5.sdp))
            Text(
                text = stringResource(R.string.tap_plus_icon_title),
                fontSize = 12.ssp,
                fontFamily = medium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = OutlineLight,
            )
        }
        Column(
            modifier = modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.BottomEnd)
                .padding(start = 9.sdp, top = 12.sdp, bottom = 12.sdp, end = 8.sdp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            CommonFloatingButton(
                Icons.Filled.Edit,
                contentColor = White,
                backgroundColor = OutlineLight,
                shape = CircleShape,
                onClick = { onClickEditSchedule.invoke() })
            Spacer(modifier = modifier.size(12.sdp))
            CommonFloatingButton(
                Icons.Filled.Add,
                contentColor = White,
                backgroundColor = PrimaryContainerDark,
                shape = CircleShape,
                onClick = { onClickAddSchedule.invoke() })
        }
    }
}


@Composable
fun SchedulesScreen(
    schedulesScreenState: SchedulesScreenState,
    context: Context,
    onSchedulesScreenEvent: onSchedulesScreenEvent,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val themes = listOf(
        ThemeValues.LIGHT_MODE.title, ThemeValues.DARK_MODE.title, ThemeValues.SYSTEM_DEFAULT.title
    )

    var themeLocation by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = schedulesScreenState.getThemeValue) {
        onSchedulesScreenEvent(SchedulesScreenEvent.ThemeChanged)
        themeLocation = if (schedulesScreenState.getThemeValue.isNullOrEmpty()) {
            2
        } else {
            themes.indexOf(schedulesScreenState.getThemeValue)
        }
    }


    if (schedulesScreenState.openThemeDialog) {
        SingleSelectDialog(modifier = modifier,
            title = stringResource(id = R.string.choose_theme),
            optionsList = themes,
            defaultSelected = themeLocation,
            submitButtonText = stringResource(id = R.string.ok),
            dismissButtonText = stringResource(id = R.string.cancel),
            onSubmitButtonClick = { id ->
                coroutineScope.launch {
                    onSchedulesScreenEvent(SchedulesScreenEvent.SetNewTheme(themes[id]))
                    onSchedulesScreenEvent(SchedulesScreenEvent.ThemeChanged)
                }
            },
            onDismissRequest = { value ->
                coroutineScope.launch {
                    onSchedulesScreenEvent(SchedulesScreenEvent.OpenThemeDialog(value))
                }
            })

    }

    ListOfSettingsMenu(
        context = context,
        onSchedulesScreenEvent = onSchedulesScreenEvent
    )
}

@Composable
fun ListOfSettingsMenu(
    context: Context,
    onSchedulesScreenEvent: onSchedulesScreenEvent,
) {
    BaseLazyColumn(
        listProperties = ListProperties(items = ReusableMenuRowLists.newList),
        bottomLoadingContent = { },
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        itemContent = { listItem ->
            when (listItem) {
                is ListItem.Content -> {
                    RowOfSettingOptions(onClickToItem = { click ->
                        when (click.description) {
                            R.string.theme_text -> {
                                onSchedulesScreenEvent(
                                    SchedulesScreenEvent.OpenThemeDialog(
                                        true
                                    )
                                )
                            }

                            R.string.clear_cache_title -> {
                                clearAppCache(
                                    context = context,
                                    onSchedulesScreenEvent = onSchedulesScreenEvent
                                )
                            }

                        }
                    }, listItem = listItem)
                }

                is ListItem.Header -> Text(
                    text = stringResource(id = listItem.titleRes ?: 0),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(12.dp)
                )
            }
        })
}

private fun clearAppCache(context: Context, onSchedulesScreenEvent: onSchedulesScreenEvent) {
    val cacheDir = context.cacheDir
    when {
        cacheDir.exists() -> {
            try {
                cacheDir.deleteRecursively()
                Log.d("AppCache", "Cache directory deleted successfully")
                onSchedulesScreenEvent(SchedulesScreenEvent.ClearCached(true))
            } catch (e: Exception) {
                Log.e("AppCache", "Error deleting cache directory: $e")
                onSchedulesScreenEvent(SchedulesScreenEvent.ClearCached(false))
            }
        }

        else -> Log.d("AppCache", "Cache directory does not exist")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val snackState = remember { SnackbarHostState() }

    Scaffold(modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackState
            )
        },
        topBar = {
            CommonTopAppBar(
                showTitle = false,
                showSearchIcon = false,
                showDefaultAvatar = false,
                defaultAvatar = null
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            NotificationSchedulesTitle()
            NoSchedulesContent(modifier = Modifier)
//            Box(modifier = Modifier.fillMaxSize()) {
//                Column(
//                    modifier = Modifier
//                        .align(Alignment.Center),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Notifications,
//                        contentDescription = "Request Notifications Permission",
//                        modifier = Modifier
//                            .size(64.sdp)
//                    )
//                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//                    Button(
//                        onClick = { },
//                        contentPadding = ButtonDefaults.ContentPadding,
//                        shape = RoundedCornerShape(8.sdp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = PrimaryContainerDark // Set your desired color
//                        )
//                    ) {
//                        Text("Enable Notifications")
//                    }
//                }
//            }
        }
    }

}