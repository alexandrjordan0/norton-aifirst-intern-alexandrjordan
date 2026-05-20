package com.jordan.norton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jordan.norton.ui.DashboardScreen
import com.jordan.norton.ui.ScanScreen
import com.jordan.norton.ui.SecurityViewModel
import com.jordan.norton.ui.theme.NortonSecurityDashboardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NortonSecurityDashboardTheme {
                SecurityApp()
            }
        }
    }
}

@Composable
fun SecurityApp() {
    val navController = rememberNavController()
    val viewModel: SecurityViewModel = viewModel()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToScan = { navController.navigate("scan") }
            )
        }
        composable("scan") {
            ScanScreen(
                viewModel = viewModel,
                onScanComplete = { navController.popBackStack() }
            )
        }
    }
}
