package cz.freego.tutorial.stopwatchexample.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue

class StopWatchViewState {

    var elapsedTime by mutableLongStateOf(0L)
}