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
import ui.screens.HomeScreen

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
                    else -> HomeScreen(onNavigate = { target -> currentScreen = target }) // Placeholder for Phase 2
                }
            }
        }
    }
}

expect fun getPlatformName(): String