package com.example.projectmanagement.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.projectmanagement.constants.AppColor

@Composable
fun AppButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    showProgress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick, enabled = enabled, colors = ButtonDefaults.buttonColors(
            containerColor = AppColor.primaryColor,      // background color
            contentColor = AppColor.white,          // text/icon color
            disabledContainerColor = AppColor.primaryColorOpacity, // when disabled
            disabledContentColor = AppColor.primaryColorOpacity
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AppTextMedium(
                text = text,
                color = AppColor.white,
                textAlign = TextAlign.Center
            )
        }
    }

}
