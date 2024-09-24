package com.example.skindiseaseapp.ui.screens.common

import android.graphics.fonts.FontFamily
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.skindiseaseapp.ui.theme.PrimaryContainerDark
import com.example.skindiseaseapp.ui.theme.White
import com.example.skindiseaseapp.ui.theme.bold
import com.example.skindiseaseapp.ui.theme.medium
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun CommonFloatingButton(
    image: ImageVector,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    onClick: () -> Unit,
) {

    LargeFloatingActionButton(
        onClick = { onClick.invoke() },
        shape = shape,
        modifier = modifier.scale(0.8f),
        containerColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource
    ) {
        Icon(imageVector = image, contentDescription = "", modifier.size(32.sdp))
    }
}

@Composable
fun CommonFloatingButtonSmall(
    image: ImageVector,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    onClick: () -> Unit,
) {

    FloatingActionButton(
        onClick = { onClick() },
        shape = shape,
        modifier = modifier.scale(0.8f),
        containerColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource
    ) {
        Icon(imageVector = image, contentDescription = "", modifier.size(24.sdp))
    }
}

@Composable
fun CommonFloatingButtonWithText(
    modifier: Modifier = Modifier,
    opacity: Float,
    text: String? = null,
    textSize: TextUnit? = null,
    textColor: Color? = null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    offsetX: Dp? = null,
    offsetY: Dp? = null,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    onClick: () -> Unit,
) {

    LargeFloatingActionButton(
        onClick = { onClick() },
        shape = shape,
        modifier = modifier
            .scale(0.8f)
            .alpha(opacity)
            .offset(x = offsetX ?: 0.sdp, y = offsetY ?: 0.sdp),
        containerColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource
    ) {
        Text(
            text = text ?: "",
            fontSize = textSize ?: 12.ssp,
            fontFamily = bold,
            color = textColor ?: White,
            fontWeight = fontWeight ?: FontWeight.Normal,
            textAlign = textAlign ?: TextAlign.Start,
//            modifier = Modifier.alpha(opacity),
        )
    }
}