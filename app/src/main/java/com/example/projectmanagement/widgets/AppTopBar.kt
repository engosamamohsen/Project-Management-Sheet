package com.example.projectmanagement.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectmanagement.constants.AppColor


@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    textCenter: String? = null,
    navController: NavController? = null,
    end: @Composable (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .height(100.dp),
    ) {

        textCenter?.let {
            AppTextBold(
                modifier = Modifier.align(Alignment.Center),
                text = it,
                fontSize = 18,
                color = AppColor.black
            )
        }

        navController?.let {
            IconButton(onClick = {
                if(onBackClick == null)
                    it.popBackStack()
                else
                    onBackClick()
            },modifier = Modifier.align(Alignment.CenterStart).size(48.dp)) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )

            }
        }

        end?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
            ) {
                it()
            }
        }
    }
}