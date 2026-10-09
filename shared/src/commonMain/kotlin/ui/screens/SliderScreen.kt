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
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun SliderScreen() {
    var brightness by remember { mutableFloatStateOf(65f) }
    var temperature by remember { mutableFloatStateOf(22f) }

    val brightnessPercent = brightness.roundToInt()
    val tempInt = temperature.roundToInt()

    // Color dinámico de bombilla según brillo
    val bulbColor = Color(0xFFF59E0B).copy(
        alpha = (0.2f + (brightness / 100f) * 0.8f).coerceIn(0.2f, 1f)
    )

    // Color de termostato según temperatura (Azul frío a Rojo cálido)
    val tempColor = when {
        tempInt < 20 -> Color(0xFF38BDF8) // Frío
        tempInt in 20..24 -> Color(0xFF10B981) // Confort / Verde
        else -> Color(0xFFEF4444) // Caliente
    }

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
            controlKmp = "Slider",
            xamarinMauControl = "Slider",
            description = "En Xamarin/.NET MAUI se declara como <Slider Minimum=\"0\" Maximum=\"100\" Value=\"{Binding Level}\" />. En Compose Multiplatform se utiliza el componente declarativo Slider(value = ..., onValueChange = ..., valueRange = ..., steps = ...), permitiendo reaccionar en tiempo real para animaciones y estados."
        )

        // 1. Control Slider Continuo: Atenuador de Iluminación
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF59E0B).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = Color(0xFFF59E0B),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Atenuador de Luz (Continuo)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Slider continuo (0% a 100%)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = "$brightnessPercent%",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFFF59E0B)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Previsualización interactiva de bombilla según el Slider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    bulbColor,
                                    Color(0xFF0F172A)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Foco inteligente",
                        tint = if (brightnessPercent > 10) Color.White else Color.Gray,
                        modifier = Modifier.size((28 + (brightness / 100f) * 24).dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Control Oficial Slider en KMP
                Slider(
                    value = brightness,
                    onValueChange = { brightness = it },
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFF59E0B),
                        activeTrackColor = Color(0xFFF59E0B),
                        inactiveTrackColor = Color(0xFF334155)
                    )
                )

                Text(
                    text = "Intensidad lumínica actual: $brightnessPercent% de potencia",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 2. Control Slider Discreto: Termostato del Hogar
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(tempColor.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Thermostat,
                                contentDescription = null,
                                tint = tempColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Termostato (Slider con Pasos)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Rango de 16°C a 30°C (14 pasos)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = "$tempInt °C",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = tempColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Slider discreto con steps
                Slider(
                    value = temperature,
                    onValueChange = { temperature = it },
                    valueRange = 16f..30f,
                    steps = 13, // 14 niveles enteros
                    colors = SliderDefaults.colors(
                        thumbColor = tempColor,
                        activeTrackColor = tempColor,
                        inactiveTrackColor = Color(0xFF334155)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "16°C (Eco / Frío)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF38BDF8)
                    )
                    Text(
                        text = "30°C (Calefacción)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFEF4444)
                    )
                }
            }
        }
    }
}
