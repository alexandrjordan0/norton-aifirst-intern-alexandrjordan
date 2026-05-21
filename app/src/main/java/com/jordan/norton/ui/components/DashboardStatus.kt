package com.jordan.norton.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.jordan.norton.model.DeviceHealth
import com.jordan.norton.model.SecurityStatus
import com.jordan.norton.ui.theme.NortonBlue
import com.jordan.norton.ui.theme.NortonGreen
import com.jordan.norton.ui.theme.NortonLightGrey
import com.jordan.norton.ui.theme.NortonOrange
import com.jordan.norton.ui.theme.NortonRed

@Composable
fun DashboardStatus(
    deviceHealth: DeviceHealth,
    onScanClick: () -> Unit
) {
    val statusColor = when (deviceHealth.status) {
        SecurityStatus.SAFE -> NortonGreen
        SecurityStatus.WARNING -> NortonOrange
        SecurityStatus.DANGER -> NortonRed
        else -> NortonBlue
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 160.dp),
        contentAlignment = Alignment.Center,
    ) {

        // Status color indicator
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .blur(60.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
        ) {
            val trianglePath = Path().apply {
                moveTo(size.width, size.height)
                lineTo(size.width - 180.dp.toPx(), size.height)
                lineTo(size.width, size.height - 400.dp.toPx())
                close()
            }
            drawPath(
                path = trianglePath,
                color = statusColor.copy(alpha = 0.67f)
            )
        }

        // Health info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = "Device Health",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                NortonButton(
                    onClick = onScanClick,
                    text = "Run Scan"
                )
            }

            // Health score progress indicator
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(
                        2.dp,
                        NortonLightGrey,
                        CircleShape
                    )
                    .background(NortonLightGrey, shape = CircleShape)
                    .padding(4.dp)
            ) {
                CircularProgressIndicator(
                    progress = { deviceHealth.score / 100f },
                    modifier = Modifier.size(90.dp),
                    color = statusColor,
                    strokeWidth = 8.dp,
                    trackColor = statusColor.copy(alpha = 0.2f)
                )
                Text(
                    text = "${deviceHealth.score}",
                    style = MaterialTheme.typography.titleLarge,
                    color = statusColor
                )
            }
        }
    }
}
