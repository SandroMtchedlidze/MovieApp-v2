package com.space.presentation.base

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

private fun canClick(duration: Long): Boolean {
    val now = SystemClock.uptimeMillis()
    if (now - lastClickTime < duration) return false

    lastClickTime = now
    return true
}

@Composable
fun rememberOnClick(
    durationMs: Long = CLICK_DURATION_DEFAULT,
    onClick: () -> Unit
): () -> Unit {
    val current by rememberUpdatedState(onClick)
    return remember(durationMs) { { if (canClick(durationMs)) current() } }
}

private var lastClickTime = 0L

const val CLICK_DURATION_DEFAULT = 300L