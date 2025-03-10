package cz.freego.tutorial.stopwatchexample.ui.component

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    var secondsElapsed by mutableIntStateOf(0)
        private set

    private var running = true

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (running) {
                    secondsElapsed++
                }
            }
        }
    }
}