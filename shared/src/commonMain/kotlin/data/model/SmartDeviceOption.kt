package data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.DevicesOther
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Representa una opción de dispositivo inteligente seleccionable en la encuesta.
 */
data class SmartDeviceOption(
    val id: String,
    val name: String,
    val description: String,
    val icon: ImageVector
)

val smartDeviceOptions = listOf(
    SmartDeviceOption(
        id = "lighting",
        name = "Iluminación inteligente",
        description = "Focos RGB, tiras LED y atenuadores automatizados",
        icon = Icons.Default.Lightbulb
    ),
    SmartDeviceOption(
        id = "ac",
        name = "Aire acondicionado",
        description = "Termostatos y climatización por zonas",
        icon = Icons.Default.AcUnit
    ),
    SmartDeviceOption(
        id = "cameras",
        name = "Cámaras de seguridad",
        description = "Vigilancia perimetral y detección de movimiento",
        icon = Icons.Default.Videocam
    ),
    SmartDeviceOption(
        id = "locks",
        name = "Cerraduras inteligentes",
        description = "Control de acceso biométrico y remoto",
        icon = Icons.Default.Lock
    ),
    SmartDeviceOption(
        id = "plugs",
        name = "Enchufes inteligentes",
        description = "Monitoreo de energía y apagado programado",
        icon = Icons.Default.Power
    ),
    SmartDeviceOption(
        id = "voice",
        name = "Asistentes de voz",
        description = "Control por comandos de voz e integraciones",
        icon = Icons.Default.RecordVoiceOver
    ),
    SmartDeviceOption(
        id = "other",
        name = "Otro",
        description = "Sensores especiales, persianas o electrodomésticos",
        icon = Icons.Default.DevicesOther
    )
)
