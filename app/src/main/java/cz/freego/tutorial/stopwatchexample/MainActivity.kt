package cz.freego.tutorial.stopwatchexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cz.freego.tutorial.stopwatchexample.ui.component.TestCompose
import cz.freego.tutorial.stopwatchexample.ui.theme.StopWatchExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopWatchExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestCompose(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
