import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ixidev.jtsalat.App
import com.ixidev.jtsalat.di.KoinComposeApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "JtSalat",
    ) {
        KoinComposeApp {
            App()
        }
    }
}