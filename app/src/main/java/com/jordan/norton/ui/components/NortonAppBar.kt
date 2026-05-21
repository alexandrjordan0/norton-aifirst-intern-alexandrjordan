package com.jordan.norton.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NortonAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                top = 36.dp,
                end = 0.dp,
                bottom = 0.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // logo
    }
}