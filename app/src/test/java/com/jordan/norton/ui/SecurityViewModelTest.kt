package com.jordan.norton.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import com.jordan.norton.model.SecurityCategory
import com.jordan.norton.model.SecurityStatus
import com.jordan.norton.model.calculateDeviceHealth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SecurityViewModelTest {

    @Before
    fun setup() {
        // Use StandardTestDispatcher for virtual time control
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Test suite generated via AI and manually reviewed
    @Test
    fun `test 1 - initial data loading`() = runTest {
        val viewModel = SecurityViewModel()
        // Wait for ViewModel init block
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state.categories.isNotEmpty())
        assertEquals(4, state.categories.size)

        val expectedScore = state.categories.calculateDeviceHealth().score
        assertEquals(expectedScore, state.deviceHealth.score)
    }

    // Test suite generated via AI and manually reviewed
    @Test
    fun `test 2 - double click prevention`() = runTest {
        val viewModel = SecurityViewModel()
        runCurrent()

        viewModel.startScan()
        runCurrent()

        assertTrue(viewModel.uiState.value.isScanning)

        advanceTimeBy(101)
        runCurrent()
        val progressAfterOneStep = viewModel.uiState.value.scanProgress
        assertTrue(
            "Progress should be > 0 but was $progressAfterOneStep",
            progressAfterOneStep > 0f
        )

        viewModel.startScan()
        runCurrent()

        assertTrue(viewModel.uiState.value.isScanning)
        assertEquals(
            "Progress should not have reset",
            progressAfterOneStep,
            viewModel.uiState.value.scanProgress,
            0.001f
        )
    }

    // Test suite generated via AI and manually reviewed
    @Test
    fun `test 3 - score boundaries`() {
        val emptyList = emptyList<SecurityCategory>()
        assertEquals(100, emptyList.calculateDeviceHealth().score)

        val allSafe = listOf(
            SecurityCategory("1", "T1", SecurityStatus.SAFE, "D1", Icons.Default.Settings)
        )
        assertEquals(100, allSafe.calculateDeviceHealth().score)

        val allDanger = List(5) {
            SecurityCategory(it.toString(), "T", SecurityStatus.DANGER, "D", Icons.Default.Settings)
        }
        assertEquals(0, allDanger.calculateDeviceHealth().score)
    }

    // Test suite generated via AI and manually reviewed
    @Test
    fun `test 4 - integrity after scan`() = runTest {
        val viewModel = SecurityViewModel()
        runCurrent()

        viewModel.startScan()
        runCurrent()

        // Advance time in steps. Total time = 5 actions * (20*100 + 800) = 14000ms.
        repeat(15) {
            advanceTimeBy(1000)
            runCurrent()
        }

        val finalState = viewModel.uiState.value
        assertEquals("Scan Complete", finalState.currentAction)
        assertEquals(false, finalState.isScanning)
        assertEquals(1f, finalState.scanProgress, 0.01f)
        assertEquals(4, finalState.categories.size)
    }

    // Test suite generated via AI and manually reviewed
    @Test
    fun `test 5 - sequence of text updates`() = runTest {
        val viewModel = SecurityViewModel()
        runCurrent()

        viewModel.startScan()
        // First action string is set before the first delay
        runCurrent()
        assertEquals("Checking system version...", viewModel.uiState.value.currentAction)

        // Loop through the 5 actions. Each action has 20 steps of 100ms delay.
        // Total delay per action: 2000ms.

        // Action 1 loop finished after 2800ms (2000ms loop + 800ms delay).
        advanceTimeBy(2800)
        runCurrent()
        assertEquals("Scanning for malicious apps...", viewModel.uiState.value.currentAction)

        advanceTimeBy(2800)
        runCurrent()
        assertEquals("Verifying network security...", viewModel.uiState.value.currentAction)

        advanceTimeBy(2800)
        runCurrent()
        assertEquals("Analyzing password strength...", viewModel.uiState.value.currentAction)

        advanceTimeBy(2800)
        runCurrent()
        assertEquals("Finalizing report...", viewModel.uiState.value.currentAction)

        advanceTimeBy(2800)
        runCurrent()
        assertEquals("Scan Complete", viewModel.uiState.value.currentAction)
    }
}
