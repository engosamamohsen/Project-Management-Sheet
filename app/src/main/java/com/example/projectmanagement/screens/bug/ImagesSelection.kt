package com.example.projectmanagement.screens.bug

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.projectmanagement.R
import com.example.projectmanagement.constants.AppColor
import com.example.projectmanagement.common.helper.isFileSizeValid
import com.example.projectmanagement.widgets.AppImageUri
import com.example.projectmanagement.widgets.AppTextMedium
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImagesSelection(
    modifier: Modifier = Modifier,
    uploadMultiple: (List<Uri>) -> Unit = {},
    selectedImages: List<Uri> = emptyList(),
    onRemoveImage: (Uri) -> Unit = {}
) {
    val TAG = "FileUploadChoice"


    val context = LocalContext.current
    var isDialogVisible by remember { mutableStateOf(false) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    val list = remember { mutableListOf<Uri>() }

    // Multiple image picker
    val multipleImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            list.clear()
            uris.forEach { uri ->
                if (uri.isFileSizeValid(context)) {
                    list.add(uri)
                } else {
                    // Handle file too large
                    Toast.makeText(context,  "${uri.path} File size exceeds 10MB limit", Toast.LENGTH_SHORT).show()
                }
            }
            if (list.isNotEmpty()) {
                uploadMultiple(list)
            }
        }
    }
    // Camera permission
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            capturedImageUri?.let { uri ->
                list.clear()
                if (uri.isFileSizeValid(context)) {
                    list.add(uri)
                    uploadMultiple(list)
                } else {
                    // Handle file too large
                    Toast.makeText(context,  "${uri.path} File size exceeds 10MB limit", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Function to create image file URI
    fun createImageFileUri(): Uri {
        val imageFile = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        ).also { capturedImageUri = it }
    }

    // Request camera permission when needed
    DisposableEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
        onDispose { }
    }



    Box(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .border(
                        BorderStroke(1.dp, AppColor.primaryColor),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        //here add permission file image or pdf or file

                        isDialogVisible = true
                    }
            ) {


                Spacer(modifier = Modifier.height(16.dp))


                AppTextMedium(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = AppColor.primaryColor,
                    text = stringResource(R.string.press_click_to_upload_your_images)
                )

                AppTextMedium(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 5.dp, end = 5.dp),
                    textAlign = TextAlign.Center,
                    color = AppColor.grey,
                    text = stringResource(R.string.max_limit_size)
                )

                Spacer(modifier = Modifier.height(16.dp))

            }


            // Selected Images Display
            if (selectedImages.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                AppTextMedium(
                    text = "Selected Images (${selectedImages.size})",
                    color = AppColor.primaryColor,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(selectedImages) { uri ->
                        AppImageUri(
                            uri = uri,
                            onRemove = { onRemoveImage(uri) }
                        )
                    }
                }
            }

        }

        if (isDialogVisible) {
            AlertDialog(
                onDismissRequest = { isDialogVisible = false },
                title = {
                    Text(text = stringResource(R.string.choose_image_type))
                },
                text = {
                    Column {
                        Text(text = stringResource(R.string.please_select_bug_image))

                        Spacer(modifier = Modifier.height(16.dp))

                        TextButton(onClick = {
                            isDialogVisible = false
                            multipleImageLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }) {
                            Text(text = stringResource(R.string.select_multiple_images))
                        }

                        TextButton(onClick = {
                            isDialogVisible = false
                            cameraLauncher.launch(createImageFileUri())
                        }) {
                            Text(text = stringResource(R.string.screenshot))
                        }
                    }
                },
                confirmButton = {}
            )

        }

    }
}


