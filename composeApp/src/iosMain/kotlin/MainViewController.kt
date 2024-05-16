import androidx.compose.ui.window.ComposeUIViewController
import com.ixidev.jtsalat.App
import com.ixidev.jtsalat.di.KoinComposeApp

fun MainViewController() = ComposeUIViewController {
    KoinComposeApp {
        App()
    }
}