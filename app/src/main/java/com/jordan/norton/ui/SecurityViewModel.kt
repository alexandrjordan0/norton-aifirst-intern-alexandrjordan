package com.jordan.norton.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordan.norton.model.DeviceHealth
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import com.jordan.norton.model.calculateDeviceHealth
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
    val currentAction: String = ""
) {
    val deviceHealth: DeviceHealth
        get() = categories.calculateDeviceHealth()
}

class SecurityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SecurityUiState())
    val uiState: StateFlow<SecurityUiState> = _uiState.asStateFlow()

    private val actions = listOf(
        "Checking system version...",
        "Scanning for malicious apps...",
        "Verifying network security...",
        "Analyzing password strength...",
        "Finalizing report..."
    )

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        _uiState.update { state ->
            state.copy(
                categories = listOf(
                    SecurityCategory(
                        "1",
                        "OS Version",
                        SecurityStatus.SAFE,
                        "System is up to date",
                        Icons.Default.Settings
                    ),
                    SecurityCategory(
                        "2",
                        "App Threats",
                        SecurityStatus.WARNING,
                        "2 apps require attention",
                        Icons.Default.Warning
                    ),
                    SecurityCategory(
                        "3",
                        "Wi-Fi Safety",
                        SecurityStatus.SAFE,
                        "No Wi-Fi Connection",
                        Icons.Default.Wifi
                    ),
                    SecurityCategory(
                        "4",
                        "Password Strength",
                        SecurityStatus.SAFE,
                        "No weak passwords found",
                        Icons.Default.Lock
                    )
                )
            )
        }
    }

    fun startScan() {
        if (_uiState.value.isScanning) return

        _uiState.update { it.copy(isScanning = true, scanProgress = 0f) }

        viewModelScope.launch {
            actions.forEachIndexed { index, action ->
                _uiState.update { it.copy(currentAction = action) }
                val startProgress = index.toFloat() / actions.size
                val endProgress = (index + 1).toFloat() / actions.size

                val steps = 20
                for (step in 1..steps) {
                    delay(100)
                    val progress =
                        startProgress + (endProgress - startProgress) * (step.toFloat() / steps)
                    _uiState.update { it.copy(scanProgress = progress) }
                }
                delay(800)
            }

            val newCategories = generateRandomScanResults(_uiState.value.categories)

            _uiState.update { state ->
                state.copy(
                    isScanning = false,
                    scanProgress = 1f,
                    categories = newCategories,
                )
            }
        }
    }

    private fun generateRandomScanResults(currentCategories: List<SecurityCategory>): List<SecurityCategory> {
        return currentCategories.map { category ->
            val randomStatus = listOf(
                SecurityStatus.SAFE,
                SecurityStatus.WARNING,
                SecurityStatus.DANGER
            ).random()

            val newDescription = when (randomStatus) {
                SecurityStatus.SAFE -> "Secured and safe"
                SecurityStatus.WARNING -> "Action recommended"
                SecurityStatus.DANGER -> "Critical threat detected!"
                else -> category.description
            }

            category.copy(status = randomStatus, description = newDescription)
        }
    }
}
