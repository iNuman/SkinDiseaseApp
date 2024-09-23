package com.example.skindiseaseapp.ui.permission

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.core.utils.extensions.openAppSetting
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    var showRationale by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    // Check permission state and show rationale or grant access
    LaunchedEffect(key1 = permissionState.status) {
        when {
            permissionState.status.isGranted -> {
                showRationale = false
                onPermissionGranted()
            }

            !permissionState.status.isGranted && ActivityCompat.shouldShowRequestPermissionRationale(
                activity!!,
                Manifest.permission.CAMERA
            ) -> {
                showRationale = true
            }

            else -> {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        activity!!,
                        Manifest.permission.CAMERA
                    )
                ) {
                    showRationale = false
                    permissionState.launchPermissionRequest()
                    onPermissionDenied()
                } else {
                    showRationale = true
                    onPermissionDenied()
                }

            }
        }
    }

    // Show rationale dialog if needed
    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text(stringResource(R.string.camera_permission_rationale_title)) },
            text = { Text(stringResource(R.string.camera_permission_rationale_message)) },
            confirmButton = {
                TextButton(onClick = {
                    activity?.let {
                        showRationale = false
                        it.openAppSetting()
                    }
                }) {
                    Text(stringResource(R.string.open_settings))
                }
            },
            dismissButton = {
//                TextButton(onClick = {
//                    showRationale = false
//                    onPermissionDenied()
//                }) {
//                    Text(stringResource(R.string.dismiss))
//                }
            }
        )
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity
    var showRationale by remember { mutableStateOf(false) }
    val permissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    // Check permission status and show rationale or launch permission request
    LaunchedEffect(key1 = permissionState.status) {
        when {
            permissionState.status.isGranted -> {
                onPermissionGranted()
            }
            !permissionState.status.isGranted && permissionState.status.shouldShowRationale -> {
                showRationale = true
            }
            else -> {
                permissionState.launchPermissionRequest()
            }
        }
    }

    // Show rationale dialog if needed
    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text(stringResource(R.string.notification_permission)) },
            text = { Text(stringResource(R.string.notification_permission_text)) },
            confirmButton = {
                TextButton(onClick = {
                    activity?.let {
                        showRationale = false
                        it.openAppSetting()
                    }
                }) {
                    Text(stringResource(R.string.open_settings))
                }
            },
            dismissButton = {
//                TextButton(onClick = {
//                    showRationale = false
//                    onPermissionDenied()
//                }) {
//                    Text(stringResource(R.string.dismiss))
//                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun CheckExactAlarmPermission() {
    val context = LocalContext.current
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val openDialog = remember { mutableStateOf(true) }
    if (!alarmManager.canScheduleExactAlarms() && openDialog.value) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false,
            ),
            icon = {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = null
                )
            }, // NEED TO AD CLICK ICON HERE OR ALARM ICON
            title = {
                Text(
                    text = stringResource(id = R.string.exact_alarm),
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.exact_alarm_text),
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val alarmPermissionIntent = Intent(
                            Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                            Uri.parse("package:com.example.clock"),
                        )
                        context.startActivity(alarmPermissionIntent)
                        openDialog.value = false
                    },
                ) {
                    Text(stringResource(id = R.string.confirm))
                }
            },
            onDismissRequest = {},
        )
    }
}
