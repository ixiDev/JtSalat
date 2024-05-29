import androidx.compose.ui.window.ComposeUIViewController
import com.ixidev.jtsalat.di.KoinComposeApp
import com.ixidev.jtsalat.ui.App

fun MainViewController() = ComposeUIViewController {
    KoinComposeApp {
        App()
    }
}