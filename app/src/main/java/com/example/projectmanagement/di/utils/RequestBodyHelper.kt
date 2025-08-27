package com.example.projectmanagement.di.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.projectmanagement.app.MyApplication
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun String.toRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun Uri.createMultipartBodyPart(paramFileName: String): MultipartBody.Part? {
    val byteArray = toBytesArray(MyApplication.appContext) ?: return null
    val extension = getMimeType((MyApplication.appContext)) ?: return null

    return MultipartBody.Part.createFormData(
        paramFileName, "File.$extension", byteArray.toRequestBody()
    )
}


private  val TAG = "Uri"
private fun Uri.toBytesArray(context: Context): ByteArray? {
    return try {
        val inputStream = context.applicationContext.contentResolver.openInputStream(this)

        return inputStream?.readBytes()
    }catch (throwable: Throwable) {
        Log.d(TAG, "toBytesArray: ${throwable.message}")

        null
    }
}

private fun Uri.getMimeType(context: Context): String? {
    return if (scheme == ContentResolver.SCHEME_CONTENT) {
        // If scheme is a content
        MimeTypeMap.getSingleton()?.getExtensionFromMimeType(context.contentResolver.getType(this))
    }else {
        // If scheme is a File
        // This will replace white spaces with %20 and also other special characters.
        // This will avoid returning null values on file name with spaces and special characters.
        MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(path ?: return null))?.toString())
    }
}