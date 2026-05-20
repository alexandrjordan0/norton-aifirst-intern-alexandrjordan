package com.jordan.norton.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import com.jordan.norton.ui.components.NortonButton
import com.jordan.norton.ui.theme.NortonGreen
import com.jordan.norton.ui.theme.NortonGrey
import com.jordan.norton.ui.theme.NortonLightGrey
import com.jordan.norton.ui.theme.NortonOrange
import com.jordan.norton.ui.theme.NortonRed
import com.jordan.norton.ui.theme.NortonSecurityDashboardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: SecurityViewModel,
    onNavigateToScan: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val categories = listOf(
        SecurityCategory("1", "App Security", SecurityStatus.SAFE, "No malicious apps found"),
        SecurityCategory("2", "Device Security", SecurityStatus.WARNING, "OS update required"),
        SecurityCategory("3", "Network", SecurityStatus.DANGER, "Connection is encrypted")
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("nnnn") })
        },
        contentColor = NortonLightGrey
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            DashboardStatus(
                status = uiState.overallStatus,
                onScanClick = onNavigateToScan
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(categories) { category ->
                    CategoryItem(category = category)
                }
            }
        }
    }
}

@Composable
fun DashboardStatus(
    status: SecurityStatus,
    onScanClick: () -> Unit
) {
    Column {
        Text(
            text = "Device Status: ${status.name}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        NortonButton(
            onClick = onScanClick,
            text = "Run Scan"
        )

        // TODO add row, that will contain some color indicator of current status. some tint or something
    }
}

@Composable
fun CategoryItem(category: SecurityCategory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = category.status.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = when (category.status) {
                        SecurityStatus.SAFE -> NortonGreen
                        SecurityStatus.WARNING -> NortonOrange
                        SecurityStatus.DANGER -> NortonRed
                        else -> NortonGrey
                    }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.description,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    NortonSecurityDashboardTheme {
        DashboardScreen(
            viewModel = SecurityViewModel(),
            onNavigateToScan = {}
        )
    }
}
