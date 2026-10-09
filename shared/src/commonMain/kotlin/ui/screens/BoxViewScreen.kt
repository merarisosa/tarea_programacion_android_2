package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

data class AmbientColor(
    val name: String,
    val color: Color,
    val hex: String
)

@Composable
fun BoxViewScreen() {
    val palette = listOf(
        AmbientColor("Neón Cian", Color(0xFF06B6D4), "#06B6D4"),
        AmbientColor("Púrpura Deep", Color(0xFF8B5CF6), "#8B5CF6"),
        AmbientColor("Rosa Cyber", Color(0xFFEC4899), "#EC4899"),
        AmbientColor("Esmeralda", Color(0xFF10B981), "#10B981"),
        AmbientColor("Ámbar Cálido", Color(0xFFF59E0B), "#F59E0B"),
        AmbientColor("Azul Eléctrico", Color(0xFF3B82F6), "#3B82F6")
    )

    var selectedColor by remember { mutableStateOf(palette[0]) }
    var cornerRadiusDp by remember { mutableFloatStateOf(24f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tarjeta de Equivalencia Detallada
        EquivalenceCard(
            controlKmp = "Box (Layout / Foundation)",
            xamarinMauControl = "BoxView",
            description = "En Xamarin/.NET MAUI, <BoxView Color=\"Red\" CornerRadius=\"10\" HeightRequest=\"50\" /> se usa para dibujar bloques de color, tarjetas o líneas. En Compose Multiplatform su equivalente directo es el composable Box, aplicándole modificadores como Modifier.size(), Modifier.background(), Modifier.clip(RoundedCornerShape(...)) y Modifier.border()."
        )

        // Tarjeta del Control: Tiras LED Ambientales
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(selectedColor.color.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = null,
                            tint = selectedColor.color,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Lámpara RGB Ambiental (Box en KMP)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Demostración de Box con color, degradado y bordes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // COMPONENTE BOX EQUIVALENTE A BOXVIEW:
                // Representa la lámpara inteligente con color y radio dinámicos
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(cornerRadiusDp.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    selectedColor.color,
                                    selectedColor.color.copy(alpha = 0.35f),
                                    Color(0xFF0F172A)
                                )
                            )
                        )
                        .border(
                            width = 2.dp,
                            color = selectedColor.color.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(cornerRadiusDp.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "COMPONENTE Box",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            color = Color.White
                        )
                        Text(
                            text = "Tono: ${selectedColor.name} (${selectedColor.hex})",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = "Radio de esquinas: ${cornerRadiusDp.roundToInt()} dp",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Ajustar CornerRadius del Box en tiempo real:",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Slider(
                    value = cornerRadiusDp,
                    onValueChange = { cornerRadiusDp = it },
                    valueRange = 0f..50f,
                    colors = SliderDefaults.colors(
                        thumbColor = selectedColor.color,
                        activeTrackColor = selectedColor.color,
                        inactiveTrackColor = Color(0xFF334155)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de Colores (utilizando múltiples Box circulares)
                Text(
                    text = "Paleta de Colores (Renderizados con Box):",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    palette.forEach { item ->
                        val isSelected = item == selectedColor
                        // Cada botón de color es un Box con borde y esquinas redondeadas
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(item.color)
                                .clickable { selectedColor = item }
                                .border(
                                    width = if (isSelected) 3.dp else 1.dp,
                                    color = if (isSelected) Color.White else Color(0xFF334155),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Demostración de Box como Separador / Línea (BoxView HeightRequest="2")
                Text(
                    text = "Box como Línea Divisoria (Equivalente BoxView Height=2):",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Línea divisoria implementada con Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    selectedColor.color,
                                    Color.Transparent
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "En Xamarin se usaba <BoxView HeightRequest=\"2\" Color=\"...\" />. En Compose se hace con Box(Modifier.fillMaxWidth().height(2.dp).background(...)).",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
