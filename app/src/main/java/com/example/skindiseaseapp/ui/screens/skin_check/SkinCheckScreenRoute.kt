package com.example.skindiseaseapp.ui.screens.home

import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.skindiseaseapp.ui.screens.common.CommonText
import com.example.skindiseaseapp.ui.screens.common.CommonTopAppBar
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.screens.home.events.BodyPartsScreenEvent
import com.example.skindiseaseapp.ui.theme.Background
import com.example.skindiseaseapp.ui.theme.White
import com.example.skindiseaseapp.ui.theme.medium
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.domain.model.body.BodyType
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.DefaultAvatar
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.Info
import com.example.skindiseaseapp.ui.theme.bold


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SkinCheckScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onBodyPartItemClicked: (BodyParts) -> Unit,
    onClickStartSkinCheck: () -> Unit,
) {

    val bodyPartsListData by viewModel.bodyPartsState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE, lifecycleOwner = lifecycleOwner) {
        viewModel.handleBodyPartEvent(BodyPartsScreenEvent.GetFullBody)
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_STOP) {
//        viewModel.onListTypeChanged(collectionScreenState.collectionsListType.name)
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
            CommonText(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 13.sdp, top = 14.sdp),
                text = stringResource(R.string.take_a_skin_check),
                textSize = 18.ssp,
                textColor = Black,
                fontFamily = medium,
                fontWeight = FontWeight.W700
            )

            Spacer(Modifier.size(9.sdp))

            SkinCheckTabRow(
                modifier = modifier,
                viewModel = viewModel
            )
            val currentStep = remember { mutableIntStateOf(1) }
            Box {
                StepsProgressBar(
                    modifier = Modifier.fillMaxSize(),
                    bodyPartsListData = bodyPartsListData,
                    currentStep = currentStep.intValue, onBodyPartItemClicked = { onBodyPartItemClicked.invoke(it)},
                )
                StartSkinCheckButton(
                    modifier = Modifier
                        .padding(24.sdp)
                        .align(Alignment.BottomCenter),
                    onClickStartSkinCheck = {
                    })
            }
        }
    }
}


@Composable
private fun SkinCheckTabRow(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel? = null,
) {

    val coroutineScope = rememberCoroutineScope()

    val list = listOf(
        stringResource(R.string.full_body), stringResource(R.string.upper_body),
        stringResource(R.string.lower_body)
    )

    val pagerState = androidx.compose.foundation.pager.rememberPagerState(pageCount = { list.size })
    Column {
        ScrollableTabRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 12.sdp, end = 12.sdp, top = 8.sdp, bottom = 14.sdp),
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 8.sdp,
            containerColor = Background,
            indicator = { tabPositions ->
                val transition = updateTransition(pagerState.currentPage, label = "")
                val indicatorStart by transition.animateDp(
                    transitionSpec = {
                        if (initialState < targetState) {
                            spring(stiffness = Spring.StiffnessVeryLow)
                        } else {
                            spring(stiffness = Spring.StiffnessMediumLow)
                        }
                    }, label = ""
                ) {
                    tabPositions[it].left
                }
                val indicatorEnd by transition.animateDp(
                    transitionSpec = {
                        if (initialState < targetState) {
                            spring(stiffness = Spring.StiffnessMediumLow)
                        } else {
                            spring(stiffness = Spring.StiffnessVeryLow)
                        }
                    }, label = ""
                ) {
                    tabPositions[it].right
                }
                Box(
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.BottomStart)
                        .offset(x = indicatorStart)
                        .width(indicatorEnd - indicatorStart)
                        .fillMaxSize()
                        .padding(2.sdp)
                        .background(color = White, RoundedCornerShape(2.sdp))
                        .zIndex(1f)
                )
            },
            divider = {}
        ) {
            list.forEachIndexed { index, title ->
                val selected = pagerState.currentPage == index
                Tab(
                    modifier = Modifier
                        .zIndex(2f)
                        .padding(top = 3.sdp, bottom = 3.sdp),
                    selected = selected,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.sdp, vertical = 3.sdp),
                        text = title,
                        style = if (selected) {
                            MaterialTheme.typography.labelLarge.copy(
                                color = Black,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            MaterialTheme.typography.labelLarge.copy(
                                color = Black,
                                fontWeight = FontWeight.Normal
                            )
                        },
                    )

                }
            }

        }
        HorizontalPager(
            state = pagerState,
        ) { page ->
            val context = LocalContext.current // Get the context
            LaunchedEffect(key1 = page) { // Use LaunchedEffect
                val composableToShow = when (page) {
                    SkinCheckBodyPartsTab.FULL_BODY -> {
                        viewModel?.handleBodyPartEvent(BodyPartsScreenEvent.GetFullBody)

                    }

                    SkinCheckBodyPartsTab.UPPER_BODY -> {
                        viewModel?.handleBodyPartEvent(BodyPartsScreenEvent.GetUpperBody)
//                        viewModel.hotCoinsStateFlow.collectAsState().value
                    }

                    SkinCheckBodyPartsTab.LOWER_BODY -> {
                        viewModel?.handleBodyPartEvent(BodyPartsScreenEvent.GetLowerBody)
                    }

                    else -> {
                        throw IllegalArgumentException("Page ${page.toString()} not supported")
                    }
                }
//            HomeListCoin(composableToShow, onNavigateToDetail)
            }
        }
    }
}

private object SkinCheckBodyPartsTab {
    const val FULL_BODY = 0
    const val UPPER_BODY = 1
    const val LOWER_BODY = 2

}

@Composable
fun StepsProgressBar(
    modifier: Modifier = Modifier,
    bodyPartsListData: List<BodyParts>,
    currentStep: Int,
    onBodyPartItemClicked: (BodyParts) -> Unit,
) {
        LazyColumn(
            modifier = modifier
                .padding(start = 16.sdp, end = 16.sdp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            items(bodyPartsListData.size) { step ->
                if (step == 0) { // Add space from top if it's first item
                    Spacer(modifier = Modifier.height(16.sdp))
                }
                Step(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.sdp),
                    bodyPart = bodyPartsListData[step],
                    isComplete = step < currentStep,
                    isCurrent = step == currentStep,
                    onBodyPartItemClicked = { bodyPart ->
                        onBodyPartItemClicked.invoke(bodyPart)
                    }
                )
                if (step == bodyPartsListData.size - 1) { // Add space from bottom if it's last item
                    Spacer(modifier = Modifier.height(57.sdp))
                }
            }
        }
}

@Composable
fun Step(
    modifier: Modifier = Modifier,
    bodyPart: BodyParts,
    isComplete: Boolean,
    isCurrent: Boolean,
    onBodyPartItemClicked: (BodyParts) -> Unit,
) {
    val color = if (isComplete || isCurrent) Black else Black
    val innerCircleColor = if (isComplete) Black else Black

    Row(modifier = modifier
        .fillMaxWidth()
        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
            onBodyPartItemClicked(
                bodyPart
            )
        }, horizontalArrangement = Arrangement.Start
    ) {
        // https://stackoverflow.com/questions/71057137/step-progress-bar-with-android-compose
            Box {
                //Circle
                Canvas(modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(20.sdp)
                    .offset(y = 18.sdp)
                    .border(
                        shape = CircleShape, width = 6.sdp, color = color
                    ),
                    onDraw = {
                        drawCircle(color = innerCircleColor)
                    }
                )
                //Line
                VerticalDivider(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .offset(y = 18.sdp),
                    color = color,
                    thickness = 4.sdp
                )

            }
            Spacer(Modifier.size(18.sdp))

            Image(
                painter = painterResource(bodyPart.image),
                contentDescription = null,
                modifier = Modifier
                    .size(57.sdp)
                    .align(Alignment.Top)
                    .border(
                        width = 2.sdp, color = Color.Green, shape = RoundedCornerShape(8.sdp)
                    )
            )
            Spacer(modifier = Modifier.width(2.sdp))
            CommonText(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 12.sdp, bottom = 18.sdp),
                text = bodyPart.name,
                textSize = 14.ssp,
                textColor = Black,
                fontFamily = medium,
                fontWeight = FontWeight.ExtraBold
            )
        }
}

@Composable
fun StartSkinCheckButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    title: String? = null,
    onClickStartSkinCheck: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
        onClick = { onClickStartSkinCheck.invoke() },
        shape = RoundedCornerShape(8.sdp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green,
            contentColor = Color.White
        ),
    ) {
        Icon(
            modifier = Modifier.size(24.sdp),
            imageVector = imageVector ?: Icons.Default.PlayArrow,
            contentDescription = stringResource(R.string.take_a_skin_check),
            tint = Color.White
        )
        Spacer(Modifier.size(24.sdp))
        CommonText(
            text = title ?: stringResource(R.string.take_a_skin_check),
            fontFamily = medium,
            textColor = Color.White,
            textSize = 14.ssp,
        )
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
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            Spacer(Modifier.size(8.sdp))
            CommonText(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 13.sdp, top = 14.sdp),
                text = stringResource(R.string.take_a_skin_check),
                textSize = 18.ssp,
                textColor = Black,
                fontFamily = medium,
                fontWeight = FontWeight.W700
            )
            Spacer(Modifier.size(9.sdp))
            SkinCheckTabRow(
                modifier = Modifier
            )
            val sampleBodyParts = listOf(
                BodyParts(name = "Head", type = BodyType.UpperBody, image = Info),
                BodyParts(name = "Chest", type = BodyType.UpperBody, image = Info),
                BodyParts(name = "Left Foot", type = BodyType.LowerBody, image = Info)
            )
            val currentStep = remember { mutableIntStateOf(1) }
            Box {
                StepsProgressBar(
                    modifier = Modifier.fillMaxSize(),
                    bodyPartsListData = sampleBodyParts,
                    currentStep = currentStep.intValue, onBodyPartItemClicked = {}
                )
                StartSkinCheckButton(
                    modifier = Modifier
                        .padding(24.sdp)
                        .align(Alignment.BottomCenter),
                    onClickStartSkinCheck = {
                    })
            }

        }
    }
}