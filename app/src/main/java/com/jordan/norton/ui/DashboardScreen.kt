package com.jordan.norton.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import com.jordan.norton.ui.theme.NortonSecurityDashboardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: SecurityViewModel,
    onNavigateToScan: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

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
            StatusHeader(status = uiState.overallStatus)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onNavigateToScan,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Full Scan")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Security Categories",
                style = MaterialTheme.typography.titleMedium
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.categories) { category ->
                    CategoryItem(category = category)
                }
            }
        }
    }
}

@Composable
fun StatusHeader(status: SecurityStatus) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (status) {
                SecurityStatus.SAFE -> MaterialTheme.colorScheme.primaryContainer
                SecurityStatus.WARNING -> MaterialTheme.colorScheme.errorContainer
                SecurityStatus.DANGER -> MaterialTheme.colorScheme.error
                SecurityStatus.UNKNOWN -> MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Device is: ${status.name}",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun CategoryItem(category: SecurityCategory) {
    ListItem(
        headlineContent = { Text(category.title) },
        supportingContent = { Text(category.description) },
        trailingContent = {
            Text(
                text = category.status.name,
                color = when (category.status) {
                    SecurityStatus.SAFE -> MaterialTheme.colorScheme.primary
                    SecurityStatus.WARNING -> MaterialTheme.colorScheme.secondary
                    SecurityStatus.DANGER -> MaterialTheme.colorScheme.error
                    SecurityStatus.UNKNOWN -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    )
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
