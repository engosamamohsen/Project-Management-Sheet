package com.example.projectmanagement.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projectmanagement.R
import com.example.projectmanagement.constants.AppColor

@Composable
fun IAppBarIntro(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showBackImage: Boolean = true,
    textEnd: String? = null,
    textEndClick: (() -> Unit?)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackImage)
            IconButton(onClick = {
                navController.popBackStack()
            },modifier = Modifier.size(30.dp)) {
                Icon(
                    modifier = Modifier.size(24.dp).rotate(180f),
                    painter = painterResource(R.drawable.ic_back), contentDescription = "back"
                )
            }
        Spacer(modifier = Modifier.weight(1f))

        textEnd?.let {
            TextButton(
                onClick = {
                    if (textEndClick != null) {
                        textEndClick()
                    }
                }
            ) {
                AppTextBold(
                    text = textEnd,
                    fontSize = 16,
                    color = AppColor.primaryColor
                )
            }
        }
    }
}