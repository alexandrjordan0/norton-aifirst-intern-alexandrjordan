package com.jordan.norton.ui

import androidx.lifecycle.ViewModel
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SecurityUiState(
    val categories: List<SecurityCategory> = emptyList(),
    val isScanning: Boolean = false,
    val scanProgress: Float = 0f,
    val overallStatus: SecurityStatus = SecurityStatus.UNKNOWN
)

class SecurityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SecurityUiState())
    val uiState: StateFlow<SecurityUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {

    }

    fun startScan() {

    }
}
