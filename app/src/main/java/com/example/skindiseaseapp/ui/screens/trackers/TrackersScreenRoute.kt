package com.example.skindiseaseapp.ui.screens.trackers

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.DefaultAvatar
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.Menu
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.humanBody
import com.example.skindiseaseapp.ui.screens.common.CommonFloatingButton
import com.example.skindiseaseapp.ui.screens.common.CommonFloatingButtonWithText
import com.example.skindiseaseapp.ui.screens.common.CommonText
import com.example.skindiseaseapp.ui.screens.common.CommonTopAppBar
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.theme.LightBlue
import com.example.skindiseaseapp.ui.theme.PrimaryContainerDark
import com.example.skindiseaseapp.ui.theme.White
import com.example.skindiseaseapp.ui.theme.bold
import com.example.skindiseaseapp.ui.theme.medium
import com.example.skindiseaseapp.ui.theme.regular
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


@Composable
fun TrackerScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onTrackerClick: (String?) -> Unit,
) {
    var shareEnabled by remember { mutableStateOf(false) }
    val launcherOfShare =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            shareEnabled = true
        }
    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
//        viewModel.handleUIEvent(
//            FavoriteScreenEvent.GetFavorites
//        )
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                showTitle = false,
                showSearchIcon = false,
                showDefaultAvatar = false,
                defaultAvatar = null
            )
        }) { paddingValues ->

        Spacer(Modifier.size(8.sdp))

        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize()
            ) {
                Spacer(Modifier.size(32.sdp))
                Image(
                    painter = painterResource(humanBody),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.sdp),
                    contentDescription = null
                )
                FirstRow(
                    modifier = modifier,
                    onClickHead = {},
                    onClickLeftShoulder = {},
                    onClickRightShoulder = {},
                    onClickChest = {}
                )

                ListButtonComponent(onClickListButton = {})

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.BottomCenter)
                        .padding(bottom = 12.sdp, start = 8.sdp, end = 8.sdp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    // Flip to back button
                    CommonFloatingButton(
                        image = Icons.Filled.Edit,
                        contentColor = White,
                        backgroundColor = Black,
                        shape = CircleShape,
                        onClick = { }
                    )
                    // Flip to back button
                    CommonFloatingButton(
                        image = Icons.Filled.Refresh,
                        contentColor = White,
                        backgroundColor = Black,
                        shape = CircleShape,
                        onClick = { }
                    )
                }
            }
        }
    }
}

@Composable
fun FirstRow(
    modifier: Modifier = Modifier,
    onClickHead: (Boolean) -> Unit,
    onClickLeftShoulder: (Boolean) -> Unit,
    onClickRightShoulder: (Boolean) -> Unit,
    onClickChest: (Boolean) -> Unit,
) {


    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (bodyContainer) = createRefs()
        val (headView) = createRefs()
        val (flipButton) = createRefs()
        val (upperBodyPartContainerOne) = createRefs()
//            val (savedStoriesContainer, savedStoriesText) = createRefs()
//            val (whatsCloneContainer, whatsCloneText) = createRefs()
//            val horizontalChain = createHorizontalChain(
//                liveStoriesContainer,
//                savedStoriesContainer,
//                whatsCloneContainer,
//                chainStyle = ChainStyle.Spread
//            )

        Box(modifier = Modifier
            .padding(32.sdp)
            .constrainAs(bodyContainer) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start, margin = 32.dp)
                end.linkTo(parent.end, margin = 32.dp)
                bottom.linkTo(parent.bottom, margin = 32.dp)
                width = Dimension.wrapContent
            }) {
            Image(
                painter = painterResource(humanBody),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )

        }

        // Head
        CommonFloatingButtonWithText(
            modifier = Modifier
                .constrainAs(headView) {
                    top.linkTo(bodyContainer.top, margin = 32.dp)
                    start.linkTo(bodyContainer.start, margin = 8.dp)
                    end.linkTo(bodyContainer.end, margin = 8.dp)
                    width = Dimension.wrapContent
                },
            text = "1",
            textSize = 16.ssp,
            fontWeight = FontWeight.Medium,
            textColor = White,
            textAlign = TextAlign.Center,
            contentColor = White,
            backgroundColor = LightBlue,
            shape = CircleShape,
            opacity = 0.7f,
            onClick = {
                onClickHead.invoke(true)
            },
        )

        Column(modifier = Modifier.constrainAs(upperBodyPartContainerOne) {
            top.linkTo(headView.bottom)
            start.linkTo(bodyContainer.start)
            end.linkTo(bodyContainer.end)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }) {

            // Below Head part
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonFloatingButtonWithText(
                    modifier = modifier
                        .size(60.sdp)
                        .align(Alignment.Top),
                    text = "2",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
                CommonFloatingButtonWithText(
                    modifier = Modifier
                        .padding(top = 24.sdp)
                        .align(Alignment.Bottom),
                    text = "3",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
                CommonFloatingButtonWithText(
                    modifier = modifier
                        .size(60.sdp)
                        .align(Alignment.Top),
                    text = "4",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
            }

            // Below Chest part
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonFloatingButtonWithText(
                    modifier = modifier
                        .size(60.sdp)
                        .align(Alignment.Top),
                    text = "5",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
                Spacer(Modifier.size(18.sdp))
                CommonFloatingButtonWithText(
                    modifier = Modifier.padding(top = 24.sdp),
                    text = "6",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
                Spacer(Modifier.size(18.sdp))
                CommonFloatingButtonWithText(
                    modifier = modifier
                        .size(60.sdp)
                        .align(Alignment.Top),
                    text = "7",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
            }

            // Hands part
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-14).sdp),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.Top
            ) {
                Spacer(Modifier.size(8.sdp))

                CommonFloatingButtonWithText(
                    modifier = modifier.align(Alignment.Top),
                    text = "8",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )

                Column {
                    CommonFloatingButtonWithText(
                        modifier = Modifier
                            .size(67.sdp)
                            .offset(y = 8.sdp)
                            .align(Alignment.CenterHorizontally),
                        text = "12",
                        textSize = 16.ssp,
                        fontWeight = FontWeight.Medium,
                        textColor = White,
                        textAlign = TextAlign.Center,
                        contentColor = White,
                        backgroundColor = LightBlue,
                        opacity = 0.5f,
                        shape = CircleShape,
                        onClick = {
                            onClickHead.invoke(true)
                        },
                    )
                    Row {
                        CommonFloatingButtonWithText(
                            modifier = Modifier
                                .size(67.sdp)
                                .align(Alignment.Bottom)
                                .offset(y = (-24).sdp),
                            text = "9",
                            textSize = 16.ssp,
                            fontWeight = FontWeight.Medium,
                            textColor = White,
                            textAlign = TextAlign.Center,
                            contentColor = White,
                            backgroundColor = LightBlue,
                            opacity = 0.5f,
                            shape = CircleShape,
                            onClick = {
                                onClickHead.invoke(true)
                            },
                        )
                        CommonFloatingButtonWithText(
                            modifier = Modifier
                                .size(67.sdp)
                                .align(Alignment.Bottom)
                                .offset(y = (-24).sdp),
                            text = "10",
                            textSize = 16.ssp,
                            fontWeight = FontWeight.Medium,
                            textColor = White,
                            textAlign = TextAlign.Center,
                            contentColor = White,
                            backgroundColor = LightBlue,
                            opacity = 0.5f,
                            shape = CircleShape,
                            onClick = {
                                onClickHead.invoke(true)
                            },
                        )
                    }

                }

                CommonFloatingButtonWithText(
                    modifier = modifier.align(Alignment.Top),
                    text = "11",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )

                Spacer(Modifier.size(8.sdp))
            }

            // Toes Part
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.Top
            ) {

                CommonFloatingButtonWithText(
                    modifier = Modifier
                        .size(67.sdp)
                        .align(Alignment.Bottom),
                    text = "13",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
                Spacer(modifier = Modifier.size(18.sdp))
                CommonFloatingButtonWithText(
                    modifier = Modifier
                        .size(67.sdp)
                        .align(Alignment.Bottom),
                    text = "14",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
            }

            // Foots Part
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.Top
            ) {

                CommonFloatingButtonWithText(
                    modifier = Modifier
                        .size(67.sdp)
                        .align(Alignment.Bottom),
                    text = "15",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
                CommonFloatingButtonWithText(
                    modifier = Modifier
                        .size(67.sdp)
                        .align(Alignment.Bottom),
                    text = "16",
                    textSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    textColor = White,
                    textAlign = TextAlign.Center,
                    contentColor = White,
                    backgroundColor = LightBlue,
                    opacity = 0.5f,
                    shape = CircleShape,
                    onClick = {
                        onClickHead.invoke(true)
                    },
                )
            }
        }


    }
}

@Composable
fun ListButtonComponent(onClickListButton: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        ElevatedButton(
            shape = RoundedCornerShape(16.sdp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Black,
                contentColor = White
            ),
            onClick = { onClickListButton() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.sdp)
        ) {
            Icon(
                imageVector = Menu,
                contentDescription = stringResource(id = R.string.app_name),
                tint = White
            )
            Spacer(Modifier.size(6.sdp))
            Text(
                modifier = Modifier.padding(top = 2.sdp, bottom = 2.sdp),
                text = stringResource(id = R.string.list),
                fontSize = 12.ssp,
                color = White,
                fontFamily = medium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkinCheckPreview() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                showTitle = false,
                showSearchIcon = false,
                showDefaultAvatar = false,
                defaultAvatar = null
            )
        }) { paddingValues ->

        Spacer(Modifier.size(8.sdp))

        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize()
            ) {
                Spacer(Modifier.size(32.sdp))
                FirstRow(
                    modifier = Modifier,
                    onClickHead = {},
                    onClickLeftShoulder = {},
                    onClickRightShoulder = {},
                    onClickChest = {}
                )


                ListButtonComponent(onClickListButton = {})


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.BottomCenter)
                        .padding(bottom = 12.sdp, start = 8.sdp, end = 8.sdp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    // Flip to back button
                    CommonFloatingButton(
                        image = Icons.Filled.Edit,
                        contentColor = White,
                        backgroundColor = Black,
                        shape = CircleShape,
                        onClick = { }
                    )

                    // Flip to back button
                    CommonFloatingButton(
                        image = Icons.Filled.Refresh,
                        contentColor = White,
                        backgroundColor = Black,
                        shape = CircleShape,
                        onClick = { }
                    )

                }
            }
        }
    }
}