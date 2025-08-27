package com.example.projectmanagement.common.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.projectmanagement.constants.AppColor
import com.example.projectmanagement.widgets.AppButtonPrimary

@Composable
fun IDialogCenter(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog)
        Dialog(

            onDismissRequest = {
                onDismiss()
            },
            content = {
                Box(
                    modifier = modifier
                        .fillMaxWidth().background(
                        color = AppColor.white,
                        shape = RoundedCornerShape(20.dp)
                    )
                ) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text("welcome is here")
                        Text("welcome is here")
                        Text("welcome is here")
                        Text("welcome is here")
                        Text("welcome is here")
                        Text("welcome is here")
                        AppButtonPrimary(text = "confirm") {
                            onDismiss()
                        }
                    }
                }
            }
        )
}