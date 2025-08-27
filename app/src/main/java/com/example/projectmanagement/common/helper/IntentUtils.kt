package com.example.projectmanagement.common.helper

import android.content.Intent
import android.net.Uri

object IntentUtils {
    fun extractImagesFromIntent(intent: Intent): List<Uri> {
        return when (intent.action) {
            Intent.ACTION_SEND -> {
                intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)?.let {
                    listOf(it)
                } ?: emptyList()
            }
            Intent.ACTION_SEND_MULTIPLE -> {
                intent.getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM)
                    ?: emptyList()
            }
            else -> emptyList()
        }
    }

    fun extractTextFromIntent(intent: Intent): String? {
        return when (intent.action) {
            Intent.ACTION_SEND -> intent.getStringExtra(Intent.EXTRA_TEXT)
            else -> null
        }
    }
}