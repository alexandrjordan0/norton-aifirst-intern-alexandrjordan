package com.jordan.norton.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import com.jordan.norton.ui.theme.NortonGreen
import com.jordan.norton.ui.theme.NortonGrey
import com.jordan.norton.ui.theme.NortonLightGrey
import com.jordan.norton.ui.theme.NortonOrange
import com.jordan.norton.ui.theme.NortonRed

@Composable
fun CategoryItem(category: SecurityCategory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 160.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(0.5.dp, NortonGrey.copy(alpha = 0.3f))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                tint = NortonLightGrey,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .requiredSize(140.dp)
                    .offset(x = 24.dp, y = 44.dp)
            )

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
}
