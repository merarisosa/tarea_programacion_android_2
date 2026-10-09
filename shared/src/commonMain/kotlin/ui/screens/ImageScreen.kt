package ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class ScaleOption(val title: String, val mauiEquivalent: String, val scale: ContentScale) {
    CROP("Crop", "AspectFill", ContentScale.Crop),
    FIT("Fit", "AspectFit", ContentScale.Fit),
    FILL_BOUNDS("FillBounds", "Fill", ContentScale.FillBounds),
    INSIDE("Inside", "AspectFit (Center)", ContentScale.Inside)
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen() {
    var selectedScale by remember { mutableStateOf(ScaleOption.FIT) }
    var snapshotCount by remember { mutableIntStateOf(0) }

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
            controlKmp = "Image (Foundation)",
            xamarinMauControl = "Image (Aspect)",
            description = "En Xamarin/.NET MAUI se escribe como <Image Source=\"...\" Aspect=\"AspectFill\" />. En Compose Multiplatform se utiliza Image(painter = painterResource(...), contentDescription = ..., contentScale = ContentScale.Crop), ofreciendo control preciso sobre dimensiones y recorte."
        )

        // Tarjeta del Visor de Cámara con Image
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                                .background(Color(0xFFEC4899).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Videocam,
                                contentDescription = null,
                                tint = Color(0xFFEC4899),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Cámara Perimetral Cam-01",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Transmisión en directo (1080p)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFEF4444).copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FiberManualRecord,
                                contentDescription = null,
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(10.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "REC",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFFEF4444)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Marco del Visor con el Componente Compose Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F172A))
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Componente Oficial Image de Compose Multiplatform
                    Image(
                        painter = painterResource("compose-multiplatform.xml"),
                        contentDescription = "Vista de cámara exterior",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = selectedScale.scale
                    )

                    // Overlay informativo sobre la imagen
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color.Black.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = "MODO: ${selectedScale.title} (${selectedScale.mauiEquivalent})",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp
                                ),
                                color = Color(0xFF38BDF8)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color.Black.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = "TIMESTAMP: 2026-10-08 23:50:12 UTC-6",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp
                                ),
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ajuste de Escala (Equivalente al Aspect de MAUI):",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Selector de ContentScale
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ScaleOption.values().forEach { option ->
                        ElevatedFilterChip(
                            selected = selectedScale == option,
                            onClick = { selectedScale = option },
                            label = { Text(option.title, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Botón interactivo para simular toma de captura
                Button(
                    onClick = { snapshotCount++ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEC4899)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (snapshotCount == 0) "Capturar Instantánea" else "Captura Guardada ($snapshotCount fotos)",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
