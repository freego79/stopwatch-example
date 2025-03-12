package cz.freego.tutorial.stopwatchexample.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.freego.tutorial.stopwatchexample.ui.theme.StopWatchExampleTheme
import cz.freego.tutorial.stopwatchexample.viewmodel.StopWatchViewModel
import cz.freego.tutorial.stopwatchexample.viewmodel.TestViewModel
import cz.freego.tutorial.stopwatchexample.viewmodel.rememberStopWatchViewModel
import cz.freego.tutorial.stopwatchexample.viewmodel.rememberTestViewModel

@Composable
fun TestCompose(
    modifier: Modifier = Modifier,
    elapsedSeconds: Int = 0,
    viewModel: TestViewModel = rememberTestViewModel(initialSeconds = elapsedSeconds)
) {
    val firstStopWatchViewModel = rememberStopWatchViewModel(
        initialMillis = 30000L,
        key = "firstStopWatchViewModel",
    )

    val externalStopWatchViewModel = rememberStopWatchViewModel(
        isRunning = true,
        key = "externalStopWatchViewModel",
    )

    val context = LocalContext.current

    // Odběr událostí z ViewModelu
    LaunchedEffect(viewModel.eventFlow) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is TestViewModel.Event.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is TestViewModel.Event.ExitEvent -> {
                    (context as? android.app.Activity)?.finish()
                }
            }
        }
    }

    with(viewModel) {
        Test.Content(
            actions = this,
            secondsElapsed = viewState.secondsElapsed,
            progressToExit = viewState.progressToExit,
            modifier = modifier,
            firstStopWatchViewModel = firstStopWatchViewModel,
            externalStopWatchViewModel = externalStopWatchViewModel,
        )
    }
}

object Test {

    interface Actions {
        fun showGreetings() = Unit
        fun exitApp() = Unit
    }

    @Composable
    fun Content(
        actions: Actions,
        secondsElapsed: Int,
        progressToExit: Float,
        modifier: Modifier = Modifier,
        firstStopWatchViewModel: StopWatchViewModel,
        externalStopWatchViewModel: StopWatchViewModel,
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Elapsed Seconds: $secondsElapsed", style = MaterialTheme.typography.headlineSmall)

            LinearProgressIndicator(
                progress = { progressToExit },
                modifier = Modifier.height(8.dp),
                color = Color.Red,
                strokeCap = StrokeCap.Butt
            )

            Spacer(modifier = Modifier.height(16.dp))

            StopWatchCompose(
                modifier = Modifier.background(Color(0xFFAAFFAA)),
                viewModel = firstStopWatchViewModel
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))

            StopWatchCompose(
                modifier = Modifier.background(Color(0xFFFFBB88)),
                viewModel = externalStopWatchViewModel,
                showButtons = false
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = { externalStopWatchViewModel.startPause() }) {
                    Text("START/PAUSE\nEXTERNAL")
                }
                Spacer(modifier = Modifier.width(6.dp))
                Button(onClick = { externalStopWatchViewModel.reset() }) {
                    Text("RESET\nEXTERNAL")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    colors = ButtonDefaults.buttonColors().copy(containerColor = Color(0xFF008800)),
                    onClick = { actions.showGreetings() }
                ) {
                    Text("SHOW TOAST")
                }
                Spacer(modifier = Modifier.width(6.dp))
                Button(
                    colors = ButtonDefaults.buttonColors().copy(containerColor = Color(0xFF880000)),
                    onClick = { actions.exitApp() }
                ) {
                    Text("EXIT APP")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestComposePreview() {
    StopWatchExampleTheme {
        TestCompose(elapsedSeconds = 15)
    }
}
