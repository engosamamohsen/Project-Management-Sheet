package com.example.projectmanagement.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.projectmanagement.constants.AppColor
import com.example.projectmanagement.constants.IFont


@Composable
private fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = AppColor.black,
    size: Int = 14,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = size.sp,
        textAlign = textAlign,
        fontWeight = fontWeight,
        lineHeight = 30.sp,
        fontFamily = IFont.customFontFamily,

    )
}


@Composable
fun AppTextBold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = AppColor.black,
    fontSize: Int = 16,
    textAlign: TextAlign = TextAlign.Start,
) {
    CustomText(
        modifier = modifier,
        text = text,
        color = color,
        size = fontSize,
        textAlign = textAlign,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun AppTextMedium(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = AppColor.black,
    fontSize: Int = 14,
    textAlign: TextAlign = TextAlign.Start,
) {
    CustomText(
        modifier = modifier,
        text = text,
        color = color,
        size = fontSize,
        textAlign = textAlign,
        fontWeight = FontWeight.Medium
    )
}
