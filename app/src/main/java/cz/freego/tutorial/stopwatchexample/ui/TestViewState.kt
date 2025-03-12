package cz.freego.tutorial.stopwatchexample.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class TestViewState {

    var secondsElapsed by mutableIntStateOf(0)
    var progressToExit by mutableFloatStateOf(1f)
}