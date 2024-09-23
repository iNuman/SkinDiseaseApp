package com.example.skindiseaseapp.ui.screens.schedules.create_schedule

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.UserCardInfo
import com.example.skindiseaseapp.ui.screens.common.CommonText
import com.example.skindiseaseapp.ui.screens.common.CommonTopAppBar
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.theme.Background
import com.example.skindiseaseapp.ui.theme.OutlineLight
import com.example.skindiseaseapp.ui.theme.White
import com.example.skindiseaseapp.ui.theme.medium
import com.example.skindiseaseapp.ui.theme.ripple
import com.example.skindiseaseapp.ui.theme.surfaceVariant
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScheduleScreenRoute(
    modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel(),
    onBackOrCancelClick: () -> Unit,
    saveScheduleClick: (Any) -> Unit,
    onClickNoneLesionCard: () -> Unit,
) {


    var showDialExample by remember { mutableStateOf(false) }
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                showTitle = true,
                title = stringResource(R.string.create_schedule_title),
                showSearchIcon = false,
                showDefaultAvatar = false,
                showCancelTitle = true,
                showSaveTitle = true,
                defaultAvatar = null,
                onCancelIconClicked = {
                    onBackOrCancelClick.invoke()
                },
                onSaveIconClicked = {
                    saveScheduleClick("Saved")
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {

            item {
                CommonText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 13.sdp, top = 14.sdp),
                    text = "Lesion",
                    textSize = 13.ssp,
                    textColor = Black,
                    fontFamily = medium,
                    fontWeight = FontWeight.W700
                )
                NoneLesionCard(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 12.sdp, top = 8.sdp, bottom = 8.sdp, end = 12.sdp),
                    onClickNoneLesionCard = {
                        onBackOrCancelClick.invoke()
                    }
                )
                Spacer(modifier = Modifier.size(8.sdp))
                CommonText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 13.sdp, top = 14.sdp),
                    text = "Scheduling",
                    textSize = 13.ssp,
                    textColor = Black,
                    fontFamily = medium,
                    fontWeight = FontWeight.W700
                )
                SchedulingCard(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 12.sdp, top = 8.sdp, bottom = 8.sdp, end = 12.sdp),
                    viewModel,
                    onClickSchedulingCard = {
                    },
                    onNavigateToDetail = {

                    }
                )
            }
        }
    }
}

@Composable
fun NoneLesionCard(modifier: Modifier, onClickNoneLesionCard: () -> Unit) {

    Column {
        Card(
            onClick = onClickNoneLesionCard,
            elevation = CardDefaults.cardElevation(defaultElevation = 3.sdp),
            colors = CardDefaults.cardColors(
                containerColor = White,
                contentColor = White
            ),
            modifier = modifier
        ) {
            Row(
                modifier = modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(UserCardInfo),
                    contentDescription = "Face",
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .size(57.sdp)
                        .weight(1f)
                )
                CommonText(
                    text = "None",
                    modifier = modifier
                        .padding(4.sdp)
                        .weight(5f),
                    enableOnClick = false
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Face",
                    tint = Color.Gray,
                    modifier = modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.size(2.sdp))
        Text(
            text = stringResource(R.string.you_will_receive_remainders_to_receive_this_lesion),
            fontSize = 10.ssp,
            fontFamily = medium,
            textAlign = TextAlign.Start,
            color = OutlineLight,
            modifier = Modifier.padding(start = 13.sdp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulingCard(
    modifier: Modifier,
    viewModel: HomeViewModel,
    onClickSchedulingCard: () -> Unit,
    onNavigateToDetail: (id: String) -> Unit,
) {
    var showMenu by remember { mutableStateOf(true) }
    var showDialExample by remember { mutableStateOf(false) }

    Column {
        Card(
            onClick = onClickSchedulingCard,
            elevation = CardDefaults.cardElevation(defaultElevation = 3.sdp),
            colors = CardDefaults.cardColors(
                containerColor = White,
                contentColor = White
            ),
            modifier = modifier
        ) {
            DialUseStateExample(onConfirm = { /*TODO*/ }, onDismiss = { /*TODO*/ })
            HorizontalDivider(
                color = ripple,
                modifier = Modifier.padding(start = 12.sdp, end = 12.sdp)
            )
            Spacer(modifier = Modifier.size(8.sdp))
            CommonText(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 12.sdp, top = 14.sdp),
                text = "Repeat Interval",
                textSize = 13.ssp,
                textColor = Black,
                fontFamily = medium,
                fontWeight = FontWeight.W700
            )
            Text(
                text = stringResource(R.string.how_often_would_you_like_to_be_notified),
                fontSize = 10.ssp,
                fontFamily = medium,
                textAlign = TextAlign.Start,
                color = OutlineLight,
                modifier = Modifier.padding(start = 12.sdp, end = 12.sdp)
            )
            HomeTabRow(Modifier.fillMaxWidth(), viewModel, onNavigateToDetail = {
                onNavigateToDetail.invoke(it)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialUseStateExample(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {

    val currentTime = Calendar.getInstance()

    Row(
        modifier = Modifier
            .padding(start = 8.sdp, end = 8.sdp, top = 8.sdp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE),
            is24Hour = false,
        )
        TimePicker(
            state = timePickerState,
        )
    }
}

//@Preview(showBackground = true)
@Composable
private fun HomeTabRow(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onNavigateToDetail: (id: String) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()

    val list = listOf(
        "Daily", "Monthly", "Weekly"
    )

    val pagerState = androidx.compose.foundation.pager.rememberPagerState(pageCount = {
        list.size
    })
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
                    onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } }
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
                    HomeListCoinTab.daily -> {
                        Toast.makeText(context, page.toString(), Toast.LENGTH_SHORT).show()
//                        viewModel.favoriteCoinsStateFlow.collectAsState().value
                    }

                    HomeListCoinTab.weekly -> {
                        Toast.makeText(context, page.toString(), Toast.LENGTH_SHORT).show()

//                        viewModel.hotCoinsStateFlow.collectAsState().value
                    }

                    HomeListCoinTab.monthly -> {
                        Toast.makeText(context, page.toString(), Toast.LENGTH_SHORT).show()

//                        viewModel.newListingsCoinStateFlow.collectAsState().value
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

private object HomeListCoinTab {
    const val daily = 0
    const val weekly = 1
    const val monthly = 2

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                showTitle = true,
                title = stringResource(R.string.create_schedule_title),
                showSearchIcon = false,
                showDefaultAvatar = false,
                showCancelTitle = true,
                showSaveTitle = true,
                defaultAvatar = null,
                onCancelIconClicked = {
                },
                onSaveIconClicked = {
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            item {
                CommonText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 13.sdp, top = 14.sdp),
                    text = "Lesion",
                    textSize = 13.ssp,
                    textColor = Black,
                    fontFamily = medium,
                    fontWeight = FontWeight.W700
                )
                NoneLesionCard(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 12.sdp, top = 8.sdp, bottom = 8.sdp, end = 12.sdp),
                    onClickNoneLesionCard = {
                    }
                )
                Spacer(modifier = Modifier.size(8.sdp))
                CommonText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 13.sdp, top = 14.sdp),
                    text = "Scheduling",
                    textSize = 13.ssp,
                    textColor = Black,
                    fontFamily = medium,
                    fontWeight = FontWeight.W700
                )

                SchedulingCard(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 12.sdp, top = 8.sdp, bottom = 8.sdp, end = 12.sdp),
                    viewModel = hiltViewModel(),
                    onClickSchedulingCard = {
                    },
                    onNavigateToDetail = {}
                )
            }
        }
    }
}