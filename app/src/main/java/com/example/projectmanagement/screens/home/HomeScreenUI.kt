package com.example.projectmanagement.screens.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.projectmanagement.R
import com.example.projectmanagement.constants.BugSubmissionScreen
import com.example.projectmanagement.screens.bug.viewModel.ExcelSheetViewModel
import com.example.projectmanagement.widgets.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(navController: NavHostController) {
    val TAG = "HomeScreenUI"
    val context = LocalContext.current
    val excelSheetViewModel = hiltViewModel<ExcelSheetViewModel>()

    // Collect states
    val bugReports by excelSheetViewModel.bugReports.collectAsState()
    val isLoading by excelSheetViewModel.isLoading.collectAsState()
    val errorMessage by excelSheetViewModel.errorMessage.collectAsState()

    // Load data on screen start
    LaunchedEffect(Unit) {
        Log.d(TAG, "Loading bug reports from Excel sheet")
        excelSheetViewModel.loadBugReports()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                textCenter = stringResource(R.string.bug_reports)
                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(BugSubmissionScreen)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Bug Report")
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = "Error: $errorMessage",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                bugReports.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No bug reports found",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(bugReports) { bugReport ->
                            BugReportCard(bugReport = bugReport)
                        }
                    }
                }
            }
        }
    }
}