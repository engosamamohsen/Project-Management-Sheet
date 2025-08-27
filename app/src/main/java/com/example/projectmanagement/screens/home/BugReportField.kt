package com.example.projectmanagement.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun BugReportField(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        when {
            // Handle image URLs (comma-separated links)
            label.contains("IMAGE") && value.contains(",") -> {
                val imageUrls = value.split(",").map { it.trim() }.filter { it.isNotBlank() }
                ImageUrlsList(imageUrls = imageUrls)
            }

            // Handle single image URL
            label.contains("IMAGE") && value.startsWith("http") -> {
                ImageSliderItem(value)
            }

            // Handle regular text
            else -> {
                Text(
                    text = value.ifBlank { "N/A" },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

