package cz.freego.tutorial.stopwatchexample.ui.component

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.freego.tutorial.stopwatchexample.ui.theme.StopWatchExampleTheme

@Composable
fun TestCompose(
    modifier: Modifier = Modifier,
    viewModel: TestViewModel = viewModel()
    // viewModel() je funkce, která je součástí Jetpack Compose a zajišťuje, že ViewModel je
    // správně přiřazen k životnímu cyklu komponenty, např. aktivity nebo fragmentu. Tím pádem se
    // stav ve TestViewModel neztratí při otočení obrazovky.
) {
    val firstStopWatchViewModel: StopWatchViewModel = viewModel(
        key = "firstStopWatchViewModel"
    )
    val externalStopWatchViewModel: StopWatchViewModel = viewModel(
        key = "externalStopWatchViewModel"
    )
    // Proč jsme nepoužili remember? Protože Funkce remember se používá pro uchovávání stavu,
    // ale v tomto případě neudržuje stav mezi otočením obrazovky, což způsobuje resetování stopky.
    // Použití viewModel() zajistí, že stavy zůstanou i po změně konfigurace. Proto i zde instance
    // firstStopWatchViewModel a externalStopWatchViewModel získáváme přes viewModel().
    //
    // Proč používáme v metodě viewModel() i parametr "key"? Tímto způsobem každá stopka
    // (firstStopWatchViewModel a externalStopWatchViewModel) získá svůj vlastní
    // nezávislý ViewModel, což znamená, že změny stavu v jedné instanci stopky nebudou
    // ovlivňovat druhou.

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Elapsed Seconds: ${viewModel.secondsElapsed}", style = MaterialTheme.typography.headlineSmall)

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
    }
}

@Preview(showBackground = true)
@Composable
fun TestComposePreview() {
    StopWatchExampleTheme {
        TestCompose()
    }
}
