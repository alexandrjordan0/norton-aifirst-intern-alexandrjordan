package com.jordan.norton.model

import kotlin.math.roundToInt

data class DeviceHealth(
    val score: Int,
    val status: SecurityStatus,
)

fun List<SecurityCategory>.calculateDeviceHealth(): DeviceHealth {
    if (isEmpty()) return DeviceHealth(100, SecurityStatus.UNKNOWN)

    var currentScore = 100
    val pointCoefficient = (100f / this.size).roundToInt()

    forEach { category ->
        when (category.status) {
            SecurityStatus.DANGER -> currentScore -= pointCoefficient
            SecurityStatus.WARNING -> currentScore -= (pointCoefficient / 2)
            SecurityStatus.UNKNOWN -> currentScore -= (pointCoefficient / 4)
            SecurityStatus.SAFE -> {}
        }
    }

    val finalScore = currentScore.coerceIn(0, 100)

    val finalStatus = when {
        finalScore >= 85 -> SecurityStatus.SAFE
        finalScore >= 55 -> SecurityStatus.WARNING
        else -> SecurityStatus.DANGER
    }

    return DeviceHealth(finalScore, finalStatus)
}