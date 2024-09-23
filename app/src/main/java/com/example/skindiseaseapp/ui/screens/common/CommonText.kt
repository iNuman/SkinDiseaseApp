package com.example.skindiseaseapp.ui.screens.common

import android.R.attr.enabled
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.example.skindiseaseapp.ui.theme.PrimaryContainerDark
import com.example.skindiseaseapp.ui.theme.medium
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun CommonText(
    modifier: Modifier = Modifier,
    text: String? = null,
    textSize: TextUnit? = null,
    fontFamily: FontFamily? = null,
    textColor: Color? = null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    enableOnClick: Boolean = false,
    interactionSource: MutableInteractionSource? = null,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = modifier
            .clickable(
                enabled = rememberSaveable {  enableOnClick }, // avoid multiple clicks used rememberSaveable
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick?.invoke()
            },
        text = text ?: "",
        fontSize = textSize ?: 12.ssp,
        fontFamily = fontFamily?: medium,
        color = textColor ?: PrimaryContainerDark,
        fontWeight = fontWeight ?: FontWeight.Normal,
        textAlign = textAlign ?: TextAlign.Start
    )
}
