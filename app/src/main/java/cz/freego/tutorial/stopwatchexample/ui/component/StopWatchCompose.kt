package cz.freego.tutorial.stopwatchexample.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.freego.tutorial.stopwatchexample.ui.theme.StopWatchExampleTheme
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun StopWatchCompose(
    modifier: Modifier = Modifier,
    showButtons: Boolean = true,
    viewModel: StopWatchViewModel = viewModel(),
    // viewModel() je funkce, která je součástí Jetpack Compose a zajišťuje, že ViewModel je
    // správně přiřazen k životnímu cyklu komponenty, např. aktivity nebo fragmentu. Tím pádem se
    // stav ve StopWatchViewModel neztratí při otočení obrazovky.
) {
    Card {
        Column(
            modifier = modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = formatTime(viewModel.elapsedTime), style = MaterialTheme.typography.headlineMedium)
            if (showButtons) {
                Row {
                    Button(onClick = { viewModel.startPause() }) {
                        Text(
                            when {
                                viewModel.elapsedTime > 0L && !viewModel.running -> "RESUME"
                                viewModel.running -> "PAUSE"
                                else -> "START"
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { viewModel.reset() }) {
                        Text("RESET")
                    }
                }
            }
        }
    }
}

private fun formatTime(milliseconds: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
    val millis = (milliseconds % 1000) / 10
    return String.format(Locale.getDefault(), "%02d:%02d:%02d.%02d", hours, minutes, seconds, millis)
}

@Preview(showBackground = true)
@Composable
fun StopWatchComposePreview() {
    StopWatchExampleTheme {
        StopWatchCompose()
    }
}