package com.example.projectmanagement.screens.bug.add

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.projectmanagement.R
import com.example.projectmanagement.common.helper.uriToFile
import com.example.projectmanagement.constants.AppColor
import com.example.projectmanagement.constants.HomeScreen
import com.example.projectmanagement.screens.bug.EXCEL_COLUMN
import com.example.projectmanagement.screens.bug.ImagesSelection
import com.example.projectmanagement.screens.bug.viewModel.ExcelSheetViewModel
import com.example.projectmanagement.screens.bug.viewModel.SharedImageViewModel
import com.example.projectmanagement.screens.bug.viewModel.UploadImageViewModel
import com.example.projectmanagement.widgets.AppButtonPrimary
import com.example.projectmanagement.widgets.AppTextBold
import com.example.projectmanagement.widgets.ITextField
import com.example.projectmanagement.widgets.TopAppBar
import java.io.File
import kotlin.collections.ifEmpty

@Composable
fun AddBugScreenUI(
    navController: NavHostController,
    sharedImages: List<Uri> = emptyList()
) {
    val TAG = "AddBugScreenUI"
    var description by remember { mutableStateOf("") }
    val uploadImageViewModel = hiltViewModel<UploadImageViewModel>()
    val excelSheetViewModel = hiltViewModel<ExcelSheetViewModel>()
    val sharedImageViewModel = hiltViewModel<SharedImageViewModel>()

    val context = LocalContext.current

    // Upload states
    val currentImageIndex by uploadImageViewModel.currentImageIndex.collectAsState()
    val totalImages by uploadImageViewModel.totalImages.collectAsState()
    val isUploading by uploadImageViewModel.isUploading.collectAsState()
    val uploadSuccess by uploadImageViewModel.uploadSuccess.collectAsState()


    // Excel sheet states
    val isSubmitting by excelSheetViewModel.isSubmitting.collectAsState()
    val submitSuccess by excelSheetViewModel.submitSuccess.collectAsState()
    val errorMessage by excelSheetViewModel.errorMessage.collectAsState()

    var selectedFiles = remember { mutableStateListOf<File>() }

    // Initialize with shared images if any
    var selectedImages by remember {
        mutableStateOf(sharedImages.ifEmpty { emptyList() })
    }

    // Show toast if images were received via intent
    LaunchedEffect(sharedImages) {
        if (sharedImages.isNotEmpty()) {
            Toast.makeText(
                context,
                "Received ${sharedImages.size} image(s) from another app",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Handle upload success
    LaunchedEffect(uploadSuccess) {
        uploadSuccess?.let { urls ->
            Log.d(TAG, "Images uploaded successfully: $urls")
            // Automatically submit to Google Sheets after successful upload
            if (description.isNotBlank()) {
//                excelSheetViewModel.testConnection() //for check connection
                excelSheetViewModel.submitBugReport(
                    mapOf(
                        EXCEL_COLUMN.DESCRIPTION.name to description,
                        EXCEL_COLUMN.IMAGE_URLS.name to urls
                    )
                )
            }
        }
    }
    // Handle Google Sheets submission result
    LaunchedEffect(submitSuccess) {
        submitSuccess?.let { success ->
            if (success) {
                Toast.makeText(
                    context,
                    "Bug report submitted to Google Sheets successfully!",
                    Toast.LENGTH_LONG
                ).show()
                // Clear shared images
                sharedImageViewModel.clearSharedImages()

                navController.navigate(HomeScreen) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            } else {
                Toast.makeText(context, "Failed to submit to Google Sheets", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    // Handle errors
    LaunchedEffect(errorMessage) {
        errorMessage?.let { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            textCenter = stringResource(R.string.add_bug_report),
            navController = navController,
            onBackClick = {
                navController.navigate(HomeScreen) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        )
    }, bottomBar = {}) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            AppTextBold(
                color = AppColor.black,
                text = stringResource(R.string.report_bug),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))


            ImagesSelection(
                selectedImages = selectedImages,
                uploadMultiple = { uris ->
                    //clear shared images
                    sharedImageViewModel.clearSharedImages()
                    // Add new images to existing list
                    selectedImages = selectedImages + uris
                    // Your upload logic here
                },
                onRemoveImage = { uri ->
                    //clear shared images
                    sharedImageViewModel.clearSharedImages()
                    // Remove image from list
                    selectedImages = selectedImages.filter { it != uri }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ITextField(
                text = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                label = stringResource(R.string.description),
                hint = stringResource(R.string.add_your_desctription),
                onChange = {
                    description = it
                })

            Spacer(modifier = Modifier.height(16.dp))
            // Progress Upload indicator
            if (isUploading) {
                UploadLoaderUI(totalImages, currentImageIndex)
            }
            // Progress Submit indicator
            if (isSubmitting) {
                SubmitLoaderUI()
            }

            Spacer(modifier = Modifier.height(16.dp))
            AppButtonPrimary(
                text = when {
                    isUploading -> stringResource(R.string.uploading)
                    isSubmitting -> stringResource(R.string.submitting)
                    else -> stringResource(R.string.add_bug)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isUploading && !isSubmitting && selectedImages.isNotEmpty() && description.isNotBlank()
            ) {
                if (selectedImages.isNotEmpty()) {
                    // Reset previous state
                    excelSheetViewModel.resetState()
                    // Convert URIs to Files and upload
                    selectedFiles.clear()
                    selectedImages.forEachIndexed { index, uri ->
                        val file = uri.uriToFile(
                            context,
                            "upload_${System.currentTimeMillis()}_$index.jpg"
                        )
                        selectedFiles.add(file)
                    }
                    if (selectedFiles.isNotEmpty())
                        uploadImageViewModel.uploadMultipleImages(selectedFiles)
                }
            }
        }
    }
}