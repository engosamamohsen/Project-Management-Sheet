package com.example.projectmanagement.screens.bug.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.projectmanagement.R
import com.example.projectmanagement.widgets.AppTextBold


@Composable
fun UploadLoaderUI(totalImages: Int , currentImageIndex: Int){
    val progressPercent = if (totalImages > 0) (currentImageIndex * 100) / totalImages else 0

    LinearProgressIndicator(
        progress = { progressPercent / 100f },
        modifier = Modifier.fillMaxWidth(),
        color = ProgressIndicatorDefaults.linearColor,
        trackColor = ProgressIndicatorDefaults.linearTrackColor,
        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
    )

    AppTextBold(
        text = "${stringResource(R.string.uploading)} $currentImageIndex/$totalImages",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}