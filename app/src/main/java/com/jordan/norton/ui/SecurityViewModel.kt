package com.jordan.norton.ui

import androidx.lifecycle.ViewModel
import com.jordan.norton.model.DeviceHealth
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import com.jordan.norton.model.calculateDeviceHealth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SecurityUiState(
    val categories: List<SecurityCategory> = emptyList(),
    val isScanning: Boolean = false,
    val scanProgress: Float = 0f,
) {
    val deviceHealth: DeviceHealth
        get() = categories.calculateDeviceHealth()
}

class SecurityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SecurityUiState())
    val uiState: StateFlow<SecurityUiState> = _uiState.asStateFlow()

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
                        "System is up to date"
                    ),
                    SecurityCategory(
                        "2",
                        "App Threats",
                        SecurityStatus.WARNING,
                        "2 apps require attention"
                    ),
                    SecurityCategory(
                        "3",
                        "Wi-Fi Safety",
                        SecurityStatus.DANGER,
                        "Unsecured public network detected"
                    ),
                    SecurityCategory(
                        "4",
                        "Password Strength",
                        SecurityStatus.SAFE,
                        "No weak passwords found"
                    )
                )
            )
        }
    }

    fun startScan() {

    }
}
