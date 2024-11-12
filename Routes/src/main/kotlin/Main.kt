import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import mx.sinsel.view.MainScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {

        MainScreen()
    }
}