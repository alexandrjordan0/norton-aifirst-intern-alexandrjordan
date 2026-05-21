package com.jordan.norton.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jordan.norton.ui.components.CategoryItem
import com.jordan.norton.ui.components.DashboardStatus
import com.jordan.norton.ui.components.NortonAppBar
import com.jordan.norton.ui.theme.NortonLightGrey
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
            NortonAppBar()
        },
        containerColor = NortonLightGrey
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding() + 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                DashboardStatus(
                    deviceHealth = uiState.deviceHealth,
                    onScanClick =
                        onNavigateToScan
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(uiState.categories) { category ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    CategoryItem(category = category)
                }
            }
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
