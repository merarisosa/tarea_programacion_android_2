import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import navigation.Screen
import theme.SmartHomeTheme
import ui.components.SmartTopBar
import ui.screens.BoxViewScreen
import ui.screens.DatePickerScreen
import ui.screens.HomeScreen
import ui.screens.ImageCarouselScreen
import ui.screens.ImageScreen
import ui.screens.ProgressIndicatorScreen
import ui.screens.SliderScreen
import ui.screens.SwitchScreen
import ui.screens.TimePickerScreen

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }

    SmartHomeTheme {
        Scaffold(
            topBar = {
                SmartTopBar(
                    currentScreen = currentScreen,
                    onBackClick = { currentScreen = Screen.HOME }
                )
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    Screen.HOME -> HomeScreen(onNavigate = { target -> currentScreen = target })
                    Screen.DATE_PICKER -> DatePickerScreen()
                    Screen.TIME_PICKER -> TimePickerScreen()
                    Screen.SLIDER -> SliderScreen()
                    Screen.IMAGE -> ImageScreen()
                    Screen.BOX_VIEW -> BoxViewScreen()
                    Screen.SWITCH -> SwitchScreen()
                    Screen.PROGRESS -> ProgressIndicatorScreen()
                    Screen.IMAGE_CAROUSEL -> ImageCarouselScreen()
                }
            }
        }
    }
}

expect fun getPlatformName(): String