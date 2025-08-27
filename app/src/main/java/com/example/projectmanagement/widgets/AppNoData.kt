package com.example.projectmanagement.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.projectmanagement.constants.AppColor

@Composable
fun AppNoData(modifier: Modifier = Modifier, text: String){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AppTextBold(
            modifier = Modifier.fillMaxSize(),
            text = text,
            color = AppColor.primaryColor,
            textAlign = TextAlign.Center
        )
    }
}