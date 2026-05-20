package com.jordan.norton.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jordan.norton.ui.theme.NortonSecurityDashboardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    viewModel: SecurityViewModel,
    onScanComplete: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startScan()
    }

    // Navigate back when scan finishes
    LaunchedEffect(uiState.isScanning) {
        if (!uiState.isScanning && uiState.scanProgress == 1f) {
            onScanComplete()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Scanning Device...") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TODO...",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanPreview() {
    NortonSecurityDashboardTheme {
        ScanScreen(
            viewModel = SecurityViewModel(),
            onScanComplete = {}
        )
    }
}
