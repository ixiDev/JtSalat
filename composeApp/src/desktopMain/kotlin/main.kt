import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ixidev.jtsalat.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "JtSalat",
    ) {
        App()
    }
}