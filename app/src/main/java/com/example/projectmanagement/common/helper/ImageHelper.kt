package com.example.projectmanagement.common.helper

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.File
import java.io.FileOutputStream
val TAG = "imageHelper"
fun Uri.getFileSize(context: Context): Long {
    var size: Long = -1
    val cursor: Cursor? = context.contentResolver.query(this, null, null, null, null)
    cursor?.use {
        val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
        if (sizeIndex != -1 && it.moveToFirst()) {
            size = it.getLong(sizeIndex)
        }
    }
    return size
}

fun Uri.isFileSizeValid(context: Context): Boolean {
    val fileSize = getFileSize(context)
    val maxSizeBytes = 10 * 1024 * 1024 // 10MB
    return fileSize in 0..maxSizeBytes
}


fun Uri.uriToFile(context: Context, fileName: String): File {

    val inputStream = context.contentResolver.openInputStream(this)
        ?: throw Exception("Cannot open input stream for URI: $this")

    val tempFile = File(context.cacheDir, fileName)
    val outputStream = FileOutputStream(tempFile)

    try {
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        Log.d(TAG, "File created: ${tempFile.absolutePath}, size: ${tempFile.length()}")
        return tempFile
    } catch (e: Exception) {
        Log.e(TAG, "Failed to convert URI to file: ${e.message}")
        if (tempFile.exists()) {
            tempFile.delete()
        }
        throw e
    }
}