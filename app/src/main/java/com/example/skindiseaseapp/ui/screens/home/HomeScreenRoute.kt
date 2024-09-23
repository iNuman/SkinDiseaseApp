package com.example.skindiseaseapp.ui.screens.home

import android.os.Build
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.skindiseaseapp.ui.theme.Background
import com.example.skindiseaseapp.ui.theme.OutlineLight
import com.example.skindiseaseapp.ui.theme.bold
import com.example.skindiseaseapp.ui.theme.medium
import com.oguzdogdu.walliescompose.features.settings.SchedulesScreenEvent
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreenRoute(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel,
    onClickScanALesion: () -> Unit,
    onBodyPartItemClicked: (BodyParts) -> Unit,
    onClickTakeASkinCheck: () -> Unit,
    onClickGiveFeedback: () -> Unit,
    onAuthenticatedUserClick: () -> Unit,
    navigateBack: () -> Unit,
) {
    val schedulesSnackBarState by viewModel.snackBarState.collectAsStateWithLifecycle()
    val bodyPartsListData by viewModel.bodyPartsState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val snackState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = schedulesSnackBarState.showSnackBar) {
        when (schedulesSnackBarState.showSnackBar) {
            true -> snackState.showSnackbar("Profile Clicked")
            false -> return@LaunchedEffect
            else -> return@LaunchedEffect
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE, lifecycleOwner = lifecycleOwner) {
        viewModel.handleBodyPartEvent(BodyPartsScreenEvent.GetFullBody)
//        viewModel.handleScreenEvents(HomeScreenEvent.FetchHomeScreenLists)
    }

    // Notifications and Alarm Manager permission
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.S || Build.VERSION.SDK_INT == Build.VERSION_CODES.S_V2) {
        CheckExactAlarmPermission()
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermission(onPermissionGranted = {
//            Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
        })
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackState)
            { message ->
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
                            tint = Color.Yellow
                        )
                        Text(
                            message.visuals.message,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = medium, fontSize = 12.sp, maxLines = 2,
                        )
                    }
                }
            }
        },
        topBar = {
            CommonTopAppBar(
                showTitle = false,
                showSearchIcon = false,
                showDefaultAvatar = true,
                defaultAvatar = SkinDiseaseAppIcons.DefaultAvatar,
                onAuthenticatedUserClick = {
                    viewModel.handleScreenEvents(SchedulesScreenEvent.ProfileIconInHomeClicked(true))
                    onAuthenticatedUserClick.invoke()
                }
            )
        }) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            HomeScreenContent(
                animatedVisibilityScope = animatedVisibilityScope,
                modifier = modifier
            )
            Spacer(modifier = Modifier.size(24.sdp))
            LazyColumnComponent(
                modifier = Modifier.fillMaxSize(),
                bodyPartsListData = bodyPartsListData,
                onClickScanALesion = {
                    onClickScanALesion.invoke()
                },
                onBodyPartItemClicked = { bodyPart ->
                    // Handle body part item click here
                    // For example, you can show a detail screen or log the item
                    // Example: showBodyPartDetail(bodyPart)
                },
                onClickTakeASkinCheck = {
                    onClickTakeASkinCheck.invoke()
                },
                onClickGiveAFeedback = {
                    onClickGiveFeedback.invoke()
                }
            )

        }
    }
}

//@Preview(showBackground = true)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreenContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
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

//@Composable
//fun HomeScreenContent(
//    modifier: Modifier = Modifier,
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(start = 12.sdp, top = 16.sdp, bottom = 8.sdp, end = 12.sdp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Row(
//            modifier = modifier
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(Person),
//                contentDescription = "Skin Check App Title",
//                modifier = modifier
//                    .size(64.sdp),
//            )
//            Text(
//                text = stringResource(id = R.string.skin_check),
//                fontWeight = FontWeight.W700,
//                fontFamily = medium,
//                letterSpacing = 2.ssp,
//                fontSize = 32.ssp,
//            )
//        }
//    }
//}

@Composable
fun LazyColumnComponent(
    modifier: Modifier = Modifier,
    bodyPartsListData: List<BodyParts>,
    onClickScanALesion: () -> Unit,
    onBodyPartItemClicked: (BodyParts) -> Unit,
    onClickTakeASkinCheck: () -> Unit,
    onClickGiveAFeedback: () -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item {
            // Button for scanning lesions
            ScanALesionButton(modifier, onClickScanALesion = {
                onClickScanALesion.invoke()
            })

            Spacer(Modifier.size(32.sdp))

            CommonText(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 12.sdp),
                text = stringResource(R.string.stay_up_to_date),
                textSize = 14.ssp,
                textColor = Black,
                fontFamily = bold,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.size(2.sdp))
            Text(
                text = stringResource(R.string.regions_to_update),
                fontSize = 12.ssp,
                fontFamily = medium,
                textAlign = TextAlign.Start,
                color = OutlineLight,
                modifier = Modifier.padding(start = 12.sdp)
            )

            Spacer(Modifier.size(12.sdp))

            LazyRow(
                modifier = modifier
                    .padding(start = 9.sdp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(5.sdp)
            ) {
                items(
                    items = bodyPartsListData,
                    key = { it.hashCode() }) { bodyPart ->
                    ImageCard(modifier, bodyPart, onBodyPartItemClicked = { bodyPart ->
                        onBodyPartItemClicked.invoke(bodyPart)
                    })
                }
            }

            Spacer(Modifier.size(16.sdp))

            OutlinedButtonWithIconForSkinCheck(modifier, onClickTakeASkinCheck = {
                onClickTakeASkinCheck.invoke()
            })

            Spacer(Modifier.size(32.sdp))

            CommonText(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 12.sdp),
                text = stringResource(R.string.education),
                textSize = 14.ssp,
                textColor = Black,
                fontFamily = bold,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.size(2.sdp))
            Text(
                text = stringResource(R.string.learn_about_signs_and_symptoms),
                fontSize = 12.ssp,
                fontFamily = medium,
                textAlign = TextAlign.Start,
                color = OutlineLight,
                modifier = Modifier.padding(start = 12.sdp)
            )

            Spacer(Modifier.size(12.sdp))

            LazyRow(
                modifier = modifier
                    .padding(start = 9.sdp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(5.sdp)
            ) {
                items(
                    items = bodyPartsListData,
                    key = { it.hashCode() }) { bodyPart ->
                    ImageCard(modifier, bodyPart, onBodyPartItemClicked = { bodyPart ->
                        onBodyPartItemClicked.invoke(bodyPart)
                    })
                }
            }

            Spacer(Modifier.size(24.sdp))

            ShareSkinApp(modifier = modifier, onClickShareSkinApp = {
                onClickGiveAFeedback.invoke()
            })
        }
    }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    bodyParts: BodyParts,
    onBodyPartItemClicked: (BodyParts) -> Unit,
) {
    Card(
        onClick = {
            onBodyPartItemClicked.invoke(bodyParts)
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.sdp),
        colors = CardDefaults.cardColors(
            containerColor = Background,
            contentColor = Black
        ),
        modifier = modifier
            .padding(start = 2.sdp, top = 2.sdp, bottom = 2.sdp)
            .width(150.sdp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.sdp, bottom = 12.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(bodyParts.image),
                contentDescription = null,
                modifier = Modifier
                    .size(50.sdp)
                    .padding(start = 2.sdp)
            )
            Spacer(modifier = Modifier.width(2.sdp))
            CommonText(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 2.sdp),
                text = bodyParts.name,
                textSize = 12.ssp,
                textColor = Black,
                fontFamily = medium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ScanALesionButton(modifier: Modifier, onClickScanALesion: () -> Unit) {
    ElevatedButton(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
        shape = RoundedCornerShape(16.sdp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green,
            contentColor = Color.White
        ),
        onClick = { onClickScanALesion.invoke() },
    ) {
        Icon(
            painter = painterResource(CollectionsIcon),
            contentDescription = stringResource(R.string.scan_a_lesion),
            tint = Color.White
        )
        Spacer(Modifier.size(24.sdp))
        Text(
            text = stringResource(R.string.scan_a_lesion),
            fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium)),
            color = colorResource(id = R.color.white),
            fontSize = 14.ssp,
        )
    }
}

@Composable
fun ShareSkinApp(modifier: Modifier, onClickShareSkinApp: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        CommonText(
            modifier = modifier
                .fillMaxWidth(),
            text = stringResource(R.string.share_skincheck),
            textSize = 16.ssp,
            textColor = Black,
            fontFamily = bold,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            enableOnClick = false
        )
        Spacer(modifier = Modifier.size(4.sdp))
        Text(
            text = stringResource(R.string.keep_your_family_and_friends_safe),
            fontSize = 12.ssp,
            fontFamily = medium,
            textAlign = TextAlign.Center,
            color = OutlineLight,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(18.sdp))
        Image(
            imageVector = Icons.Default.Share,
            contentScale = ContentScale.FillBounds,
            contentDescription = "Profile Image",
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .size(38.sdp)

        )
        Spacer(modifier = Modifier.size(24.sdp))
        OutlinedButtonWithIconForFeedback(modifier, onClickShareSkinApp = {
            onClickShareSkinApp.invoke()
        })
        Spacer(modifier = Modifier.size(42.sdp))
    }
}

@Composable
fun OutlinedButtonWithIconForSkinCheck(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    title: String? = null,
    onClickTakeASkinCheck: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .border(
                width = 2.sdp,
                color = Color.Green,
                shape = RoundedCornerShape(8.sdp)
            ),
        onClick = { onClickTakeASkinCheck.invoke() },
        shape = RoundedCornerShape(8.sdp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Green
        ),
    ) {
        Icon(
            imageVector = imageVector ?: Icons.Default.Favorite,
            contentDescription = stringResource(R.string.take_a_skin_check),
            tint = Color.Green
        )
        Spacer(Modifier.size(24.sdp))
        Text(
            text = title ?: stringResource(R.string.take_a_skin_check),
            fontFamily = FontFamily(Font(R.font.poppins_regular, FontWeight.Normal)),
            color = Color.Green,
            fontSize = 14.ssp,
        )
    }
}

@Composable
fun OutlinedButtonWithIconForFeedback(
    modifier: Modifier = Modifier,
    onClickShareSkinApp: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .border(
                width = 2.sdp,
                color = Color.Green,
                shape = RoundedCornerShape(8.sdp)
            ),
        onClick = { onClickShareSkinApp.invoke() },
        shape = RoundedCornerShape(8.sdp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Green
        ),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = stringResource(R.string.give_a_feedback),
            tint = Color.Green
        )
        Spacer(Modifier.size(24.sdp))
        Text(
            text = stringResource(R.string.give_feedback),
            fontFamily = FontFamily(Font(R.font.poppins_regular, FontWeight.Normal)),
            color = Color.Green,
            fontSize = 14.ssp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                showTitle = false,
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
            val sampleBodyParts = listOf(
                BodyParts(name = "Head", type = BodyType.UpperBody, image = Info),
                BodyParts(name = "Chest", type = BodyType.UpperBody, image = Info),
                BodyParts(name = "Pelvis", type = BodyType.LowerBody, image = Info)
            )
//            HomeScreenContent()
            Spacer(modifier = Modifier.size(24.sdp))
            LazyColumnComponent(
                modifier = Modifier.fillMaxWidth(),
                bodyPartsListData = sampleBodyParts,
                onClickScanALesion = {},
                onClickGiveAFeedback = {},
                onBodyPartItemClicked = {},
                onClickTakeASkinCheck = {}
            )
        }

    }
}