package com.example.projectmanagement.network.model

import androidx.annotation.Keep

@Keep
data class BugReport(
    val TIMESTAMP: String = "",
    val DESCRIPTION: String = "",
    val IMAGE_URLS: List<String> = emptyList(),
    val STATUS: String = ""
){
    // Method to get all data as key-value pairs for dynamic display
    fun getAllData(): Map<String, String> = mapOf(
        "TIMESTAMP" to TIMESTAMP,
        "DESCRIPTION" to DESCRIPTION,
        "IMAGE_URLS" to IMAGE_URLS.joinToString(", "),
        "STATUS" to STATUS
    ).filter { it.value.isNotBlank() }
}