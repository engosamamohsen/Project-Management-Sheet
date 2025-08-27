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
fun SubmitLoaderUI(){
    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    //"Submitting to Google Sheets..."
    AppTextBold(
        text = stringResource(R.string.submit_to_google_sheet),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}