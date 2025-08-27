package com.example.projectmanagement.constants

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppColor {
    val primaryColor : Color = Color(0xFF7F56D9);
    val primaryColorOpacity : Color = Color(0xFFE9D7FE);
    val btnColorOpacity : Color = Color(0xFFC89BFD);
    val black : Color = Color(0xFF000000);
    val white : Color = Color(0xFFFFFFFF);
    val textHeaderColor:Color = Color(0xFF101828)
    val textLabelColor:Color = Color(0xFF344054)
    val textHintColor:Color = Color(0xFF475467)
    val textBorderColor: Color = Color(0xffD0D5DD)
    val textFieldHintColor:Color = Color(0xFF667085)
    val grey:Color = Color(0xFF808080)

    //
    val backgroundPrimaryColor = Brush.verticalGradient(
        colors = listOf(primaryColorOpacity, white) // Replace with your desired colors
    )
}