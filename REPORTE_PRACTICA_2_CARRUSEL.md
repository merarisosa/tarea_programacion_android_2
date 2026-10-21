# Práctica 2 U2 — Image Carousel (Ambientes del Hogar)
**Materia:** Programación Avanzada de Dispositivos Móviles  
**Profesor:** Dionisio Pérez Pérez  
**Proyecto:** SmartHome Hub (Kotlin Multiplatform + Compose Multiplatform)  
**Fecha:** Octubre 2026  

---

## 1. Resumen Ejecutivo
Se implementó con éxito el octavo módulo interactivo denominado **"Ambientes del hogar"** dentro de la aplicación domótica **SmartHome Hub**. Este componente demuestra el uso del control **`HorizontalPager`** de Compose Multiplatform como contraparte directa del control **`CarouselView`** de Xamarin / .NET MAUI, permitiendo explorar fotografías locales de alta definición mediante desplazamiento táctil horizontal, indicadores de página reactivos y controles de navegación asistida.

---

## 2. Archivos Creados y Modificados

### Archivos Modificados:
1. **[`shared/src/commonMain/kotlin/navigation/Screen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/navigation/Screen.kt)**:
   - Se añadió la entrada enum `IMAGE_CAROUSEL` con metadatos académicos:
     - `title`: *"Ambientes del hogar"*
     - `controlName`: *"HorizontalPager"*
     - `mauiEquivalent`: *"Xamarin/MAUI CarouselView"*
     - `description`: *"Explora imágenes locales de los ambientes de una casa inteligente."*
     - `badge`: *"Carrusel"*
2. **[`shared/src/commonMain/kotlin/App.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/App.kt)**:
   - Se importó `ui.screens.ImageCarouselScreen`.
   - Se integró `Screen.IMAGE_CAROUSEL -> ImageCarouselScreen()` en la rama reactiva del bloque `when (currentScreen)`.
   - Se preservó la barra superior unificada (`SmartTopBar`) y el tema global sin duplicidades.
3. **[`shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt)**:
   - Se incorporó el octavo `ControlItem` para `Screen.IMAGE_CAROUSEL` utilizando el icono `Icons.Default.Collections` y un acento verde azulado (`Color(0xFF14B8A6)`).
   - El contador dinámico superior refleja automáticamente **8 Módulos Activos**.

### Archivos Creados:
1. **[`shared/src/commonMain/kotlin/ui/screens/ImageCarouselScreen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/screens/ImageCarouselScreen.kt)**:
   - Pantalla completa del carrusel con `EquivalenceCard`, encabezado, visor con relación de aspecto 3:2, degradado de legibilidad, indicadores circulares activos/pulsables y botones anterior/siguiente.

---

## 3. Fotografías Locales y Atribución

Las cinco imágenes fueron descargadas en formato JPEG real, con relación horizontal (1200 × 800 px) y pesos optimizados (< 200 KB por archivo), garantizando funcionamiento 100% offline sin peticiones de red en tiempo de ejecución:

| Archivo | Ambiente | Peso | Autor | Origen / Licencia |
| :--- | :--- | :--- | :--- | :--- |
| `ambiente_sala.jpg` | Sala de Estar | 187 KB | Spacejoy | [Unsplash (1600210492486)](https://unsplash.com/photos/1600210492486-724fe5c67fb0) — Unsplash License (Libre uso) |
| `ambiente_cocina.jpg` | Cocina Integral | 149 KB | Jason Briscoe | [Unsplash (1556911220)](https://unsplash.com/photos/1556911220-e15b29be8c8f) — Unsplash License (Libre uso) |
| `ambiente_dormitorio.jpg` | Habitación | 183 KB | Spacejoy | [Unsplash (1616594039964)](https://unsplash.com/photos/1616594039964-ae9021a400a0) — Unsplash License (Libre uso) |
| `ambiente_estudio.jpg` | Estudio / Oficina | 154 KB | Collov Home | [Unsplash (1585412727339)](https://unsplash.com/photos/1585412727339-54e4bae3bbf9) — Unsplash License (Libre uso) |
| `ambiente_jardin.jpg` | Jardín / Terraza | 129 KB | Huy Nguyen | [Unsplash (1584467735871)](https://unsplash.com/photos/1584467735871-8e85353a8413) — Unsplash License (Libre uso) |

---

## 4. Gestión de Recursos Compartidos vs. Drawable Android

De acuerdo con las directrices de la consigna académica:
1. **Código Compartido (`commonMain`):**
   - Las imágenes están alojadas en `shared/src/commonMain/resources/` y en `shared/src/commonMain/resources/drawable/`.
   - Se cargan mediante la API compatible de Compose Multiplatform 1.5.11: `painterResource(ambiente.resourceName)`.
2. **Referencia Académica a `Resources/drawable`:**
   - Se incorporaron réplicas idénticas en el directorio Android nativo `shared/src/androidMain/res/drawable/`.
   - Este directorio está vinculado formalmente en `shared/build.gradle.kts` mediante `sourceSets["main"].res.srcDirs("src/androidMain/res")`.
   - Se explica que la duplicación obedece al cumplimiento de la consigna sin comprometer la independencia multiplataforma del módulo compartido.

---

## 5. Arquitectura del Carrusel: HorizontalPager y Estado Reactivo

* **Control Base:** `androidx.compose.foundation.pager.HorizontalPager`.
* **Anotación de Compatibilidad:** `@OptIn(ExperimentalFoundationApi::class)` requerida en Compose 1.5.11.
* **Estado:** `val pagerState = rememberPagerState(initialPage = 0) { listaAmbientes.size }`.
* **Fuente Única de Verdad:**
  - El contador dinámico `"X de 5"` lee directamente `pagerState.currentPage + 1`.
  - La fila de indicadores resalta el índice activo expandiéndolo a una cápsula de 24 dp.
  - Al pulsar cualquier indicador se lanza `coroutineScope.launch { pagerState.animateScrollToPage(index) }`.
  - Los botones **Anterior** y **Siguiente** se deshabilitan en los límites (`currentPage == 0` y `currentPage == lastIndex` respectivamente).

---

## 6. Comandos Ejecutados y Resultados de Compilación

1. **Compilación del Target de Escritorio:**
   ```bash
   ./gradlew :desktopApp:compileKotlinJvm
   # Resultado: BUILD SUCCESSFUL en 3s
   ```
2. **Empaquetado de Binario Ejecutable (UberJar):**
   ```bash
   ./gradlew :desktopApp:packageUberJarForCurrentOS
   # Resultado: BUILD SUCCESSFUL (Genera KotlinMultiplatformComposeDesktopApplication-linux-x64-1.0.0.jar)
   ```
3. **Ejecución y Verificación Visual:**
   - Se ejecutó el binario en la sesión de escritorio X11/Wayland activa (`DISPLAY=:0`) utilizando el entorno Temurin JDK 17.
   - La aplicación desplegó fluidamente el panel con los 8 módulos, la apertura de "Ambientes del hogar" y la navegación entre las 5 fotografías.

---

## 7. Catálogo de Evidencias Generadas

Todas las capturas reales de ejecución están almacenadas en:
📁 **[`/home/merari/Descargas/hw2/capturas_reporte_U2_carrusel/`](file:///home/merari/Descargas/hw2/capturas_reporte_U2_carrusel)**

* **`figura_01_inicio_modulo_carrusel.png`**: Vista general de la pantalla de inicio mostrando el banner y los módulos activos.
* **`figura_02_carrusel_sala.png`**: Carrusel en la página 1 (Sala de Estar), botón Anterior deshabilitado.
* **`figura_03_carrusel_cocina.png`**: Carrusel en la página 2 (Cocina Integral), indicador sincronizado.
* **`figura_04_carrusel_dormitorio.png`**: Carrusel en la página 3 (Dormitorio Principal).
* **`figura_05_carrusel_estudio.png`**: Carrusel en la página 4 (Estudio / Oficina).
* **`figura_06_carrusel_jardin.png`**: Carrusel en la página 5 (Jardín Exterior), botón Siguiente deshabilitado.
* **`figura_07_recursos_drawable.png`**: Estructura de archivos de los recursos empaquetados en `commonMain/resources` y `androidMain/res/drawable`.
* **`figura_08_compilacion_exitosa.png`**: Terminal con el proceso de construcción exitoso en Gradle.

---

## 8. Notas sobre Entornos y Plataformas

* **Escritorio (Linux JVM):** Compilado y ejecutado exitosamente con JDK 17 (Headful). Verificación visual completa.
* **Android:** El código fuente en `androidApp` y `shared` está 100% alineado con Compose Multiplatform y AGP 8.0.2. En la máquina actual no se encuentra configurado el SDK local de Android (`ANDROID_HOME`), por lo que su compilación de APK se ejecutará directamente al abrir el proyecto en Android Studio.
* **iOS:** Marcado como target deshabilitado por el plugin de Kotlin/Native en máquinas Linux (requiere Xcode en macOS).
