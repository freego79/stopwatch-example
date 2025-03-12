package cz.freego.tutorial.stopwatchexample.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun <T : ViewModel> rememberViewModelFactory(
    modelClass: Class<T>,
    key: String? = null,
    factory: () -> T,
): T {
    val factoryInstance = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val viewModel = factory()
                if (modelClass.isInstance(viewModel)) {
                    @Suppress("UNCHECKED_CAST")
                    return viewModel as T
                }
                throw IllegalArgumentException("Unknown ViewModel: $modelClass")
            }
        }
    }
    return viewModel(
        modelClass = modelClass,
        factory = factoryInstance,
        key = key,
    )
}
