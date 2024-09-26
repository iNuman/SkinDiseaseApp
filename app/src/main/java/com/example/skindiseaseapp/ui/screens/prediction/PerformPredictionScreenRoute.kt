package com.example.skindiseaseapp.ui.screens.home

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.domain.model.body.BodyType
import com.example.skindiseaseapp.domain.wrapper.Resource
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.CollectionsIcon
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.Info
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.Person
import com.example.skindiseaseapp.ui.permission.CheckExactAlarmPermission
import com.example.skindiseaseapp.ui.permission.RequestNotificationPermission
import com.example.skindiseaseapp.ui.screens.common.CommonText
import com.example.skindiseaseapp.ui.screens.common.CommonTopAppBar
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.screens.home.events.BodyPartsScreenEvent
import com.example.skindiseaseapp.ui.screens.home.events.BottomSheetOnBoardingScreenEvent
import com.example.skindiseaseapp.ui.theme.Background
import com.example.skindiseaseapp.ui.theme.OutlineLight
import com.example.skindiseaseapp.ui.theme.bold
import com.example.skindiseaseapp.ui.theme.medium
import com.oguzdogdu.walliescompose.features.settings.SchedulesScreenEvent
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.skindiseaseapp.core.utils.helper.common.Constants.MIN_SIZE_OF_CROPPED_IMAGE_OVERLAY


@Composable
fun PerformPredictionScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onClickScanALesion: () -> Unit,
    navigateBack: () -> Unit,
) {
    BackHandler(enabled = true) {
        navigateBack.invoke()
    }

    /*
    * The  key difference in how they handle the lifecycle of the composable.
    * collectAsState(): This function collects the latest value from a Flow and represents its value as a State object.
    *  It starts collecting when the composable enters the composition and stops when it leaves. However,
    * it doesn't automatically stop collecting when the activity or fragment goes into the background. This can lead to unnecessary
    * resource consumption and potentially crashes if the flow emits values while the composable is in the background.
    * collectAsStateWithLifecycle(): This function is similar to
    * collectAsState, but it's lifecycle-aware. It uses the Lifecycle of the composable to automatically
    * start and stop collecting from the flow. It suspends collection when the lifecycle is stopped and resumes
    * collection when the lifecycle is started. This ensures that the flow only emits values when the composable is
    * active and visible, preventing unnecessary work and potential issues.
    * */
    val bitmap by viewModel.croppedBitmap.collectAsStateWithLifecycle()
//    val bitmaps by viewModel.bitmaps.collectAsState()

//    val lifecycleOwner = LocalLifecycleOwner.current
//    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE, lifecycleOwner = lifecycleOwner) {
//        viewModel.handleBottomSheetEvent(BottomSheetOnBoardingScreenEvent.GetScanLesionOItems)
//    }

    val minSize = MIN_SIZE_OF_CROPPED_IMAGE_OVERLAY
    var overlayWidth by remember { mutableFloatStateOf(minSize) }
    var overlayHeight by remember { mutableFloatStateOf(minSize) }

    val overlayWidthInDp: Dp
    val overlayHeightInDp: Dp

    with(LocalDensity.current) {
        overlayWidthInDp = overlayWidth.toDp()
        overlayHeightInDp = overlayHeight.toDp()
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                showTitle = true,
                title = stringResource(R.string.perform_prediction),
                showSearchIcon = false,
                showDefaultAvatar = false,
                defaultAvatar = SkinDiseaseAppIcons.DefaultAvatar,
                onAuthenticatedUserClick = {}
            )
        }) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {


            // Overlay box with border
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(overlayWidthInDp, overlayHeightInDp)
                    .border(
                        border = BorderStroke(5.sdp, Color.White),
                        shape = RoundedCornerShape(12.sdp)
                    )
            ) {
                bitmap?.asImageBitmap()?.let {
                    Image(
                        modifier = Modifier,
                        contentScale = ContentScale.Crop,
                        bitmap = it,
                        contentDescription = stringResource(R.string.image_for_cropping)
                    )
                }

            }
        }

    }

}