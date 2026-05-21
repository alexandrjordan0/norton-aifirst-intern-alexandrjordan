package com.jordan.norton.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jordan.norton.ui.theme.NortonYellow
import com.jordan.norton.ui.theme.Typography

@Composable
fun NortonButton(
    onClick: () -> Unit,
    text: String,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = NortonYellow,
            contentColor = Color.Black,
        ),
        border = BorderStroke(width = 3.dp, color = Color.Black)
    ) {
        Text(text, style = Typography.titleSmall)
    }
}