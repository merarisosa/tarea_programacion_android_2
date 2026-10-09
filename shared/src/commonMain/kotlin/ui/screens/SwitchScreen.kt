package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SwitchScreen() {
    var masterArmed by remember { mutableStateOf(true) }
    var perimeterSensors by remember { mutableStateOf(true) }
    var motionSensors by remember { mutableStateOf(true) }
    var sirenEnabled by remember { mutableStateOf(false) }
    var pushAlerts by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tarjeta de Equivalencia Académica
        EquivalenceCard(
            controlKmp = "Switch (Material 3)",
            xamarinMauControl = "Switch (IsToggled)",
            description = "En Xamarin/.NET MAUI se declara como <Switch IsToggled=\"{Binding IsArmed}\" OnColor=\"Green\" />. En Compose Multiplatform se utiliza Switch(checked = ..., onCheckedChange = { ... }), con soporte de animaciones nativas de thumb y track integradas en Material Design 3."
        )

        // Panel Principal con Switch Maestro de Alarma
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (masterArmed) Color(0xFF064E3B) else Color(0xFF1E293B)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (masterArmed) Color(0xFF10B981).copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (masterArmed) Icons.Default.Shield else Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (masterArmed) Color(0xFF34D399) else Color(0xFF94A3B8),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (masterArmed) "SISTEMA ARMADO" else "SISTEMA DESARMADO",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = if (masterArmed) "Protección activa en tiempo real" else "Sensores en modo reposo",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (masterArmed) Color(0xFFA7F3D0) else Color(0xFF94A3B8)
                        )
                    }
                }

                // Switch Maestro
                Switch(
                    checked = masterArmed,
                    onCheckedChange = { isChecked ->
                        masterArmed = isChecked
                        if (!isChecked) {
                            perimeterSensors = false
                            motionSensors = false
                            sirenEnabled = false
                        } else {
                            perimeterSensors = true
                            motionSensors = true
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF10B981),
                        uncheckedThumbColor = Color(0xFF94A3B8),
                        uncheckedTrackColor = Color(0xFF334155)
                    )
                )
            }
        }

        // Lista de Sensores con Switches Individuales
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "SENSORES Y DISPOSITIVOS DE SEGURIDAD",
                    style = MaterialTheme.typography.labelMedium.copy(
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                SensorSwitchItem(
                    title = "Sensores de Puertas y Ventanas",
                    subtitle = "8 contactos magnéticos perimetrales",
                    icon = Icons.Default.DoorFront,
                    checked = perimeterSensors,
                    onCheckedChange = { perimeterSensors = it }
                )

                SensorSwitchItem(
                    title = "Sensores de Movimiento (PIR)",
                    subtitle = "Detección infrarroja en sala y pasillo",
                    icon = Icons.Default.Sensors,
                    checked = motionSensors,
                    onCheckedChange = { motionSensors = it }
                )

                SensorSwitchItem(
                    title = "Sirena de Alta Potencia (110 dB)",
                    subtitle = "Alarma sonora exterior en caso de intrusión",
                    icon = Icons.Default.Campaign,
                    checked = sirenEnabled,
                    accentColor = Color(0xFFEF4444),
                    onCheckedChange = { sirenEnabled = it }
                )

                SensorSwitchItem(
                    title = "Notificaciones Push Inmediatas",
                    subtitle = "Envío de alertas a celulares autorizados",
                    icon = Icons.Default.NotificationsActive,
                    checked = pushAlerts,
                    onCheckedChange = { pushAlerts = it }
                )
            }
        }
    }
}

@Composable
private fun SensorSwitchItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    accentColor: Color = Color(0xFF10B981),
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (checked) accentColor.copy(alpha = 0.15f) else Color(0xFF334155).copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (checked) accentColor else Color(0xFF94A3B8),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = accentColor,
                uncheckedThumbColor = Color(0xFF94A3B8),
                uncheckedTrackColor = Color(0xFF334155)
            )
        )
    }
}
