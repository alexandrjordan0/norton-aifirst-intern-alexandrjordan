package com.jordan.norton.model

import androidx.compose.ui.graphics.vector.ImageVector

enum class SecurityStatus {
    SAFE, WARNING, DANGER, UNKNOWN
}

data class SecurityCategory(
    val id: String,
    val title: String,
    val status: SecurityStatus,
    val description: String,
    val icon: ImageVector,
)
