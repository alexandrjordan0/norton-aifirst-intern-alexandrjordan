package com.jordan.norton.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import com.jordan.norton.ui.components.NortonButton
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
        SecurityCategory("3", "Network", SecurityStatus.SAFE, "Connection is encrypted")
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Security Dashboard") })
        }
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

            Text(
                text = "Security Categories",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

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
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Device Status: ${status.name}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        NortonButton(
            onClick = onScanClick,
            text = "Scan"
        )
    }
}

@Composable
fun CategoryItem(category: SecurityCategory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = category.status.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = when (category.status) {
                        SecurityStatus.SAFE -> MaterialTheme.colorScheme.primary
                        SecurityStatus.WARNING -> MaterialTheme.colorScheme.secondary
                        SecurityStatus.DANGER -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.description,
                style = MaterialTheme.typography.bodySmall
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
