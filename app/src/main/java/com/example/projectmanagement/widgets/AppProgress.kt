package com.example.projectmanagement.widgets

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.projectmanagement.constants.AppColor

@Composable
fun AppProgress(modifier: Modifier = Modifier, percentage : Float, color: Color = AppColor.primaryColor, size: Int = 60){
    var progress by remember { mutableStateOf(0F) }
    val progressAnimDuration = 700
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )
    Box(modifier = modifier.size(size.dp)) {
        CircularProgressIndicator(
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            color = color,
            progress = { progressAnimation },
            modifier = Modifier.size(size.dp).align(Alignment.Center),
        )
    }
    LaunchedEffect(LocalLifecycleOwner.current) {
        progress = percentage
    }
}