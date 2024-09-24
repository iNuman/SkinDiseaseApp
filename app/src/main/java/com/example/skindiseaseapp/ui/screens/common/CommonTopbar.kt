package com.example.skindiseaseapp.ui.screens.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.ui.theme.medium
import network.chaintech.sdpcomposemultiplatform.ssp
import com.example.skindiseaseapp.ui.theme.*
import network.chaintech.sdpcomposemultiplatform.sdp


@Composable
fun CommonTopAppBar(
    modifier: Modifier = Modifier,
    showTitle: Boolean = false,
    showCancelTitle: Boolean = false,
    showSaveTitle: Boolean = false,
    title: String = stringResource(R.string.app_name),
    showSearchIcon: Boolean = false,
    showDefaultAvatar: Boolean = false,
    defaultAvatar: Any? = null,
    onAuthenticatedUserClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onSaveIconClicked: (() -> Unit)? = null,
    onCancelIconClicked: (() -> Unit)? = null,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.sdp, start = 10.sdp, end = 10.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (showSearchIcon) {
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            }
        } else if (showCancelTitle) {
            Text(
                modifier = modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onCancelIconClicked?.invoke()
                    },
                text = stringResource(R.string.cancel),
                fontSize = 12.ssp,
                fontFamily = medium,
                color = PrimaryContainerDark
            )

        } else {
            Spacer(Modifier.weight(1f)) // Empty space with weight
        }
        if (showTitle) {
            Text(
                text = title,
                modifier = modifier,
                textAlign = TextAlign.Center,
                fontSize = 12.ssp,
                fontFamily = bold,
                color = Color.Black
            )
        } else {
            Spacer(Modifier.weight(6f)) // Empty space with weight
        }
        if (showDefaultAvatar || showSearchIcon) {
            IconButton(
                onClick = if (showDefaultAvatar) {
                    onAuthenticatedUserClick
                } else if (showSearchIcon) {
                    onSearchClick
                } else {
                    return@Row
                },
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f)
            ) {
                AsyncImage(
                    model = defaultAvatar,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = stringResource(R.string.profile_image),
                    modifier = Modifier
                        .size(32.sdp)
                        .clip(RoundedCornerShape(50.sdp))
                        .border(
                            1.dp,
                            Color.DarkGray,
                            shape = RoundedCornerShape(50.sdp)
                        )
                )
            }
        } else if (showSaveTitle) {
            Text(
                modifier = modifier
                    .padding(top = 10.sdp, bottom = 10.sdp)
                    .clickable(enabled = false,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        onSaveIconClicked?.invoke()
                    },
                text = stringResource(R.string.save),
                textAlign = TextAlign.Center,
                fontSize = 12.ssp,
                fontFamily = medium,
                color = OnSurfaceVariantLight
            )
        } else {
            Spacer(Modifier.weight(1f)) // Empty space with weight
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CommonTopAppBar(
        modifier = Modifier.wrapContentHeight(),
        showSaveTitle = true,
        showCancelTitle = true,
        showTitle = true
    )
}