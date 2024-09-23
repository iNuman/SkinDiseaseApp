package com.example.skindiseaseapp.ui.screens.home

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.Person
import com.example.skindiseaseapp.ui.permission.RequestCameraPermission
import com.example.skindiseaseapp.ui.screens.common.CommonTopAppBar
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.screens.home.events.BodyPartsScreenEvent
import com.example.skindiseaseapp.ui.theme.medium
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import android.Manifest
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ScanLesionScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navigateBack: () -> Unit,
) {

    val context = LocalContext.current

    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (!permissionState.status.isGranted) {
        RequestCameraPermission(
            onPermissionGranted = {
                // Show permission granted message
//                Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
            },
            onPermissionDenied = {
                // Handle denial scenario
//                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                showTitle = true,
                showSearchIcon = false,
                showDefaultAvatar = true,
                defaultAvatar = SkinDiseaseAppIcons.DefaultAvatar,
                onAuthenticatedUserClick = {
                }
            )
        }) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            ScanLesionScreenContent(modifier = modifier.fillMaxWidth())
        }
    }
}

@Composable
fun ScanLesionScreenContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 12.sdp, top = 16.sdp, bottom = 8.sdp, end = 12.sdp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Person),
                contentDescription = "Skin Check App Title",
                modifier = modifier
                    .size(64.sdp)
            )
            Text(
                text = stringResource(id = R.string.skin_check),
                fontWeight = FontWeight.W700,
                fontFamily = medium,
                letterSpacing = 2.ssp,
                fontSize = 32.ssp,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScanLesionScreenPreview() {

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                showTitle = true,
                showSearchIcon = false,
                showDefaultAvatar = true,
                defaultAvatar = SkinDiseaseAppIcons.DefaultAvatar,
                onAuthenticatedUserClick = {
                }
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {

            ScanLesionScreenContent(modifier = Modifier.fillMaxWidth())
        }

    }
}