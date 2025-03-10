package cz.freego.tutorial.stopwatchexample.ui.component

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopWatchViewModel : ViewModel() {
    var elapsedTime by mutableLongStateOf(0L)
        private set

    private var startTime = 0L
    var running = false

    fun startPause() {
        if (running) {
            running = false
        } else {
            startTime = SystemClock.elapsedRealtime() - elapsedTime
            running = true
            viewModelScope.launch {
                while (running) {
                    elapsedTime = SystemClock.elapsedRealtime() - startTime
                    delay(10)
                }
            }
        }
    }

    fun reset() {
        running = false
        elapsedTime = 0L
    }
}
