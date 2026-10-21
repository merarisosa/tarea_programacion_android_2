package ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * Modelo ligero para representar cada ambiente del hogar en el carrusel.
 */
data class AmbienteItem(
    val id: Int,
    val title: String,
    val description: String,
    val resourceName: String,
    val contentDescription: String
)

/**
 * Lista ordenada de los 5 ambientes con fotografías locales empaquetadas.
 */
val listaAmbientes = listOf(
    AmbienteItem(
        id = 1,
        title = "Sala",
        description = "Un espacio para relajarte con iluminación inteligente",
        resourceName = "ambiente_sala.jpg",
        contentDescription = "Fotografía de la sala de estar moderna con iluminación domótica"
    ),
    AmbienteItem(
        id = 2,
        title = "Cocina",
        description = "Comodidad y tecnología para tus actividades diarias",
        resourceName = "ambiente_cocina.jpg",
        contentDescription = "Fotografía de la cocina integral inteligente con acabados modernos"
    ),
    AmbienteItem(
        id = 3,
        title = "Dormitorio",
        description = "Descanso con un ambiente a tu medida",
        resourceName = "ambiente_dormitorio.jpg",
        contentDescription = "Fotografía de la habitación principal con ambiente cálido y relajante"
    ),
    AmbienteItem(
        id = 4,
        title = "Estudio",
        description = "Un espacio para trabajar y concentrarte",
        resourceName = "ambiente_estudio.jpg",
        contentDescription = "Fotografía del estudio y área de trabajo con luz natural"
    ),
    AmbienteItem(
        id = 5,
        title = "Jardín",
        description = "Conecta tu hogar con el exterior",
        resourceName = "ambiente_jardin.jpg",
        contentDescription = "Fotografía del jardín exterior y terraza con vegetación cuidada"
    )
)

/**
 * Pantalla principal del Carrusel de Imágenes (PRACTICA 2 U2).
 * Demuestra el uso de HorizontalPager en Compose Multiplatform como
 * equivalente al control CarouselView de Xamarin / .NET MAUI.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun ImageCarouselScreen() {
    // Estado del Pager como fuente única de verdad para el carrusel
    val pagerState = rememberPagerState(initialPage = 0) { listaAmbientes.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Tarjeta de Equivalencia Académica
        EquivalenceCard(
            controlKmp = "HorizontalPager (Foundation Pager)",
            xamarinMauControl = "CarouselView",
            description = "En Xamarin/.NET MAUI se implementa mediante <CarouselView ItemsSource=\"{Binding Ambientes}\"> con DataTemplate y Position. En Compose Multiplatform se utiliza HorizontalPager(state = pagerState) con rememberPagerState { items.size }, proveyendo desplazamiento horizontal táctil, animación fluida y sincronización de indicadores sin librerías externas."
        )

        // 2. Encabezado e Introducción
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF06B6D4).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Collections,
                        contentDescription = null,
                        tint = Color(0xFF06B6D4),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Explora tu hogar",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Desliza para conocer cada ambiente",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // 3. Tarjeta Principal del Carrusel
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Barra de Estado Superior del Carrusel: Título del ambiente + Contador
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val ambienteActual = listaAmbientes[pagerState.currentPage]

                    Text(
                        text = ambienteActual.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF06B6D4).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "${pagerState.currentPage + 1} de ${listaAmbientes.size}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF06B6D4)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Contenedor del HorizontalPager (Fotografía Protagonista)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 2f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0F172A))
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp))
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { pageIndex ->
                        val ambiente = listaAmbientes[pageIndex]

                        Box(modifier = Modifier.fillMaxSize()) {
                            // Imagen local cargada mediante painterResource
                            Image(
                                painter = painterResource(ambiente.resourceName),
                                contentDescription = ambiente.contentDescription,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Degradado oscuro inferior para máxima legibilidad del texto
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black.copy(alpha = 0.85f)
                                            )
                                        )
                                    )
                                    .padding(horizontal = 14.dp, vertical = 12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = ambiente.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = ambiente.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFE2E8F0)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Indicadores de Página (Dots interactivos pulsables)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listaAmbientes.forEachIndexed { index, _ ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(
                                    width = if (isSelected) 24.dp else 8.dp,
                                    height = 8.dp
                                )
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) Color(0xFF06B6D4) else Color(0xFF475569)
                                )
                                .clickable {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Botones de Navegación "Anterior" y "Siguiente"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Botón Anterior
                    OutlinedButton(
                        onClick = {
                            if (pagerState.currentPage > 0) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }
                        },
                        enabled = pagerState.currentPage > 0,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Página anterior",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Anterior")
                    }

                    // Botón Siguiente
                    Button(
                        onClick = {
                            if (pagerState.currentPage < listaAmbientes.lastIndex) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                        enabled = pagerState.currentPage < listaAmbientes.lastIndex,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF06B6D4)
                        )
                    ) {
                        Text("Siguiente")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Página siguiente",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
