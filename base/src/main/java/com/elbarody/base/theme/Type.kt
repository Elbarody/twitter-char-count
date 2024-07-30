package com.elbarody.base.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.elbarody.base.R

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.bold)
        ), fontWeight = FontWeight.Bold, color = DarkGray, fontSize = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.medium)
        ),
        fontWeight = FontWeight.Medium,
        color = DarkGray,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.medium)
        ),
        fontWeight = FontWeight.SemiBold,
        color = DarkGray,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.regular)
        ),
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 16.sp
    ),

    bodySmall = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.regular)
        ),
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 14.sp
    ),
)