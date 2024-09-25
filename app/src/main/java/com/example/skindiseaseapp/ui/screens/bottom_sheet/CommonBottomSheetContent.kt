package com.example.skindiseaseapp.ui.screens.bottom_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.domain.model.bottom_sheet.OnBoardingDataClass
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons
import com.example.skindiseaseapp.ui.screens.common.CommonText
import com.example.skindiseaseapp.ui.theme.BackgroundLight
import com.example.skindiseaseapp.ui.theme.OnErrorContainerLight
import com.example.skindiseaseapp.ui.theme.White
import com.example.skindiseaseapp.ui.theme.regular
import com.example.skindiseaseapp.ui.theme.ripple
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import kotlin.math.absoluteValue
import kotlin.ranges.coerceIn
import kotlin.to


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBottomSheet(modifier: Modifier = Modifier, list: List<OnBoardingDataClass>) {
    var showBottomSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(showBottomSheet)

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = White,
            contentColor = BackgroundLight,
            contentWindowInsets = {
                WindowInsets(0, 0, 0, 0)
            },
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {

            CommonBottomSheetContent(list = list, modifier = modifier)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBottomSheetContent(
    list: List<OnBoardingDataClass>,
    modifier: Modifier = Modifier,
) {
    HorizontalPagerWithIndicators(list)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithIndicators(list: List<OnBoardingDataClass>) {
    val pagerState = rememberPagerState(pageCount = { list.size })

    Column {

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 2.sdp), pageSpacing = 2.sdp
        ) { page ->
            DisplayOnBoardingItem(onBoardingItem = list[page])
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }
                    .collect { currentPage ->
                        pagerState.animateScrollToPage(currentPage)
                    }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.sdp, bottom = 12.sdp)
        ) {
            HorizontalPagerIndicator(
                pageCount = list.size,
                currentPage = pagerState.currentPage,
                targetPage = pagerState.targetPage,
                currentPageOffsetFraction = pagerState.currentPageOffsetFraction
            )
        }
    }
}

@Composable
fun DisplayOnBoardingItem(onBoardingItem: OnBoardingDataClass) {
    Column {
        CommonText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.sdp, end = 18.sdp)
                .wrapContentHeight(),
            text = onBoardingItem.title,
            textSize = 16.ssp,
            textColor = Black,
            fontFamily = regular,
            fontWeight = FontWeight.Normal
        )
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 0.sdp),
            colors = CardDefaults.cardColors(
                containerColor = BackgroundLight,
                contentColor = Black
            ),
            modifier = Modifier
                .padding(start = 67.sdp, end = 67.sdp, top = 14.sdp, bottom = 2.sdp)
                .wrapContentWidth()
                .height(180.sdp)
                .clip(
                    RoundedCornerShape(
                        topStart = 12.sdp,
                        topEnd = 12.sdp,
                        bottomStart = 0.sdp,
                        bottomEnd = 0.sdp
                    )
                ),
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally// Align content to bottom
            ) {
                Image(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(), // Adjust height as needed
                    contentScale = ContentScale.Fit,
                    painter = rememberAsyncImagePainter(
                        model = onBoardingItem.image
                    ),
                    contentDescription = stringResource(R.string.app_name)
                )
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = Black,
    unselectedIndicatorSize: Dp = 8.dp,
    selectedIndicatorSize: Dp = 10.dp,
    indicatorCornerRadius: Dp = 32.sdp,
    indicatorPadding: Dp = 2.dp,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentSize()
            .height(selectedIndicatorSize + indicatorPadding * 2)
    ) {

        repeat(pageCount) { page ->
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)

                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    indicatorColor.copy(alpha = 0.3f) to unselectedIndicatorSize
                }

            Box(
                modifier = Modifier
                    .padding(indicatorPadding)
                    .clip(CircleShape)
                    .background(color)
                    .size(size)

            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DisplayOnBoardingItem() {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val fakeData = listOf(
        OnBoardingDataClass("Title 1", SkinDiseaseAppIcons.Person),
        OnBoardingDataClass("Title 2", SkinDiseaseAppIcons.Person),
        OnBoardingDataClass("Title 3", SkinDiseaseAppIcons.Person),
    )
    CommonBottomSheet(modifier = Modifier.fillMaxSize(), list = fakeData)
}