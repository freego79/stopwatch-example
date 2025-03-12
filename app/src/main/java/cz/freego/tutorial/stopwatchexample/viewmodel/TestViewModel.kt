package cz.freego.tutorial.stopwatchexample.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.freego.tutorial.stopwatchexample.data.datasource.GreetingsDataSource
import cz.freego.tutorial.stopwatchexample.ui.Test
import cz.freego.tutorial.stopwatchexample.ui.TestViewState
import cz.freego.tutorial.stopwatchexample.utils.rememberViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TestViewModel(
    initialSeconds: Int,
    private val _greetingsDataSource: GreetingsDataSource,
): ViewModel(), Test.Actions {
    // SharedFlow pro posílání událostí do UI
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    val viewState = TestViewState()

    private val secondsToExitApp = 180
    private val toastMessageSecondsBeforeExitApp = 15

    private var running = true

    init {
        viewState.secondsElapsed = initialSeconds
        viewState.progressToExit = 1f

        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (running) {
                    viewState.secondsElapsed++
                    viewState.progressToExit = 1f - viewState.secondsElapsed.toFloat() / secondsToExitApp.toFloat()
                    if (viewState.secondsElapsed + toastMessageSecondsBeforeExitApp == secondsToExitApp) {
                        showToastMessage("Aplikace bude zavřena za $toastMessageSecondsBeforeExitApp vteřin!")
                    }
                    if (viewState.secondsElapsed >= secondsToExitApp) {
                        exitApp()
                    }
                }
            }
        }
    }

    private fun showToastMessage(message: String) {
        viewModelScope.launch {
            _eventFlow.emit(Event.ShowToast(message))
        }
    }

    override fun showGreetings() {
        showToastMessage(_greetingsDataSource.nextGreetings())
    }

    override fun exitApp() {
        viewModelScope.launch {
            _eventFlow.emit(Event.ExitEvent)
        }
    }

    // Definice eventu (může být rozšířena podle potřeby)
    sealed class Event {
        object ExitEvent : Event()
        data class ShowToast(val message: String) : Event()
    }
}

@Composable
fun rememberTestViewModel(
    initialSeconds: Int = 0,
) = rememberViewModelFactory(TestViewModel::class.java) {
    TestViewModel(
        initialSeconds = initialSeconds,
        _greetingsDataSource = GreetingsDataSource()
    )
}