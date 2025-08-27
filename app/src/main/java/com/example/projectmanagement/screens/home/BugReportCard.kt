package com.example.projectmanagement.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projectmanagement.model.BugReport
import kotlin.collections.component1
import kotlin.collections.component2


@Composable
fun BugReportCard(bugReport: BugReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display all key-value pairs dynamically
            bugReport.getAllData().forEach { (key, value) ->
                BugReportField(
                    label = key.replace("_", " ").uppercase(),
                    value = value
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
