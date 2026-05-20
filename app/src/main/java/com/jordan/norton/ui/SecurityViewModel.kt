package com.jordan.norton.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        _uiState.update {
            it.copy(
                categories = listOf(
                    SecurityCategory("1", "Device Security", SecurityStatus.SAFE, "Your device is protected."),
                    SecurityCategory("2", "Web Protection", SecurityStatus.WARNING, "Some issues found in web settings."),
                    SecurityCategory("3", "Identity Safety", SecurityStatus.SAFE, "No identity threats detected."),
                    SecurityCategory("4", "Privacy Monitor", SecurityStatus.DANGER, "Critical privacy risks detected!")
                ),
                overallStatus = SecurityStatus.WARNING
            )
        }
    }

    fun startScan() {
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true, scanProgress = 0f) }
            
            for (i in 1..100) {
                delay(50) // Simulate work
                _uiState.update { it.copy(scanProgress = i / 100f) }
            }
            
            _uiState.update { 
                it.copy(
                    isScanning = false, 
                    scanProgress = 1f,
                    overallStatus = SecurityStatus.SAFE // Update status after scan
                ) 
            }
        }
    }
}
