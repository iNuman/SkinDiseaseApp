package com.example.skindiseaseapp.ui.theme

import androidx.compose.material3.Typography

import androidx.compose.ui.unit.sp
import com.example.skindiseaseapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val regular = FontFamily(Font(R.font.poppins_regular))
val medium = FontFamily(Font(R.font.poppins_medium))
val bold = FontFamily(Font(R.font.poppins_semibold))
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = medium,
        color = Color.Black,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ), titleSmall = TextStyle(
        fontFamily = medium,
        color = Color.Unspecified,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleMedium = TextStyle(
        fontFamily = medium,
        color = Color.Unspecified,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = medium,
        color = Color.White,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)