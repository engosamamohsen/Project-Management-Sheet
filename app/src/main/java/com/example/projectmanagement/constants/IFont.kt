package com.example.projectmanagement.constants

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.projectmanagement.R

object IFont {
    val customFontFamily = FontFamily(
        Font(R.font.medium, FontWeight.Normal),
        Font(R.font.bold, FontWeight.Bold),
        Font(R.font.regular, FontWeight.Normal),
        Font(R.font.semi_bold, FontWeight.SemiBold),
    )
}