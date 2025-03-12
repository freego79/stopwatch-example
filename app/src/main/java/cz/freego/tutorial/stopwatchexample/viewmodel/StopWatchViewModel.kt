package cz.freego.tutorial.stopwatchexample.viewmodel

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.freego.tutorial.stopwatchexample.ui.StopWatchViewState
import cz.freego.tutorial.stopwatchexample.utils.rememberViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopWatchViewModel(
    initialMillis: Long,
    isRunning: Boolean,
) : ViewModel() {

    val viewState = StopWatchViewState()

    private var startTime = 0L
    var running = false

    init {
        viewState.elapsedTime = initialMillis
        if (isRunning) startPause()
    }

    fun startPause() {
        if (running) {
            running = false
        } else {
            startTime = SystemClock.elapsedRealtime() - viewState.elapsedTime
            running = true
            viewModelScope.launch {
                while (running) {
                    viewState.elapsedTime = SystemClock.elapsedRealtime() - startTime
                    delay(10)
                }
            }
        }
    }

    fun reset() {
        running = false
        viewState.elapsedTime = 0L
    }
}

@Composable
fun rememberStopWatchViewModel(
    initialMillis: Long = 0L,
    isRunning: Boolean = false,
    key: String? = null,
) = rememberViewModelFactory(
    modelClass = StopWatchViewModel::class.java,
    key = key,
) {
    StopWatchViewModel(
        initialMillis = initialMillis,
        isRunning = isRunning,
    )
}