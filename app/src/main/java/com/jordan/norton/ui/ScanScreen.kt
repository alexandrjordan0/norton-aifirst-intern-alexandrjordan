package com.jordan.norton.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jordan.norton.ui.components.NortonAppBar
import com.jordan.norton.ui.theme.NortonBlue
import com.jordan.norton.ui.theme.NortonGreen
import com.jordan.norton.ui.theme.NortonGrey
import com.jordan.norton.ui.theme.NortonLightGrey
import com.jordan.norton.ui.theme.NortonSecurityDashboardTheme
import com.jordan.norton.ui.theme.NortonYellow
import kotlin.math.cos
import kotlin.math.sin

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

    // disable pop while scanning
    BackHandler(enabled = uiState.isScanning) {
    }

    Scaffold(
        topBar = {
            NortonAppBar()
        },
        containerColor = NortonLightGrey
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val infiniteTransition = rememberInfiniteTransition(label = "cloudTransition")
            val animatedValue by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 2 * Math.PI.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(4000, easing = LinearEasing), repeatMode = RepeatMode.Restart
                ),
                label = "cloudAnimation"
            )

            // Cloudy animation
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            ) {
                val xOffset = sin(animatedValue) * 60.dp.toPx()
                val yOffset = cos(animatedValue) * 40.dp.toPx()

                val baseCenter = Offset(size.width * 0.8f, size.height * 0.9f)

                drawCircle(
                    color = NortonBlue.copy(alpha = 0.8f),
                    radius = 320.dp.toPx(),
                    center = baseCenter + Offset(xOffset, yOffset)
                )

                drawCircle(
                    color = NortonYellow.copy(alpha = 0.4f),
                    radius = 260.dp.toPx(),
                    center = baseCenter + Offset(
                        -150.dp.toPx() + xOffset * 0.7f,
                        -60.dp.toPx() + yOffset * 1.3f
                    )
                )

                drawCircle(
                    color = NortonGreen.copy(alpha = 0.3f),
                    radius = 200.dp.toPx(),
                    center = baseCenter + Offset(
                        50.dp.toPx() + xOffset * 1.4f,
                        -10.dp.toPx() + yOffset * 0.8f
                    )
                )
            }

            // Scan info section
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Running Smart Scan",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = uiState.currentAction,
                    style = MaterialTheme.typography.labelMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = { uiState.scanProgress },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(4.dp),
                    color = NortonGrey,
                    trackColor = NortonGrey.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.weight(2f))
            }
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
