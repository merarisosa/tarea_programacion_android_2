# Práctica 2 U2 — Image Carousel (Ambientes del Hogar)
**Materia:** Programación Avanzada de Dispositivos Móviles  
**Profesor:** Dionisio Pérez Pérez  
**Proyecto:** SmartHome Hub (Kotlin Multiplatform + Compose Multiplatform)  
**Fecha:** Octubre 2026  

---

## 1. Resumen Ejecutivo
Se implementó el octavo módulo interactivo denominado **"Ambientes del hogar"** dentro de la aplicación domótica **SmartHome Hub**. Este componente demuestra el uso del control **`HorizontalPager`** de Compose Multiplatform como contraparte directa del control **`CarouselView`** de Xamarin / .NET MAUI, permitiendo explorar fotografías locales de alta definición mediante desplazamiento táctil horizontal, indicadores de página reactivos y controles de navegación asistida.

---

## 2. Archivos Creados y Modificados

### Archivos Modificados:
1. **[`shared/src/commonMain/kotlin/navigation/Screen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/navigation/Screen.kt)**:
   - Entrada enum `IMAGE_CAROUSEL` con metadatos académicos:
     - `title`: *"Ambientes del hogar"*
     - `controlName`: *"HorizontalPager"*
     - `mauiEquivalent`: *"Xamarin/MAUI CarouselView"*
     - `description`: *"Explora imágenes locales de los ambientes de una casa inteligente."*
     - `badge`: *"Carrusel"*
2. **[`shared/src/commonMain/kotlin/App.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/App.kt)**:
   - Importación de `ui.screens.ImageCarouselScreen`.
   - Conexión de `Screen.IMAGE_CAROUSEL -> ImageCarouselScreen()` en el `when (currentScreen)`.
   - Reutilización estricta del `Scaffold` y `SmartTopBar` existentes sin duplicar barras de navegación.
3. **[`shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt)**:
   - Incorporación del 8vo `ControlItem` para `Screen.IMAGE_CAROUSEL` con icono `Icons.Default.Collections` y acento teal (`Color(0xFF14B8A6)`).
   - El contador dinámico superior refleja automáticamente **8 Módulos Activos**.

### Archivos Creados:
1. **[`shared/src/commonMain/kotlin/ui/screens/ImageCarouselScreen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/screens/ImageCarouselScreen.kt)**:
   - Pantalla completa del carrusel con `EquivalenceCard`, visor 3:2, degradado de legibilidad, indicadores circulares activos/pulsables y botones anterior/siguiente.

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
1. **Código Compartido (`commonMain/resources/`):**
   - Las imágenes están alojadas en la raíz de `shared/src/commonMain/resources/`.
   - Se cargan mediante la API compatible de Compose Multiplatform 1.5.11: `painterResource(ambiente.resourceName)`.
   - **Ruta real consumida:** `painterResource("ambiente_sala.jpg")` busca el recurso directamente en la raíz del classpath generado por Gradle.
2. **Referencia Académica a `Resources/drawable`:**
   - Se incorporaron copias idénticas en el directorio nativo `shared/src/androidMain/res/drawable/`.
   - Este directorio está formalmente vinculado en `shared/build.gradle.kts` mediante:
     ```kotlin
     sourceSets["main"].res.srcDirs("src/androidMain/res")
     ```
   - Esta duplicación representa una adaptación académica para cumplir con el requisito de referencia a drawable de la consigna sin acoplar la UI compartida a `R.drawable`.

---

## 5. Arquitectura del Carrusel: HorizontalPager y Estado Reactivo

* **Control Base:** `androidx.compose.foundation.pager.HorizontalPager`.
* **Anotación de Compatibilidad:** `@OptIn(ExperimentalFoundationApi::class)` requerida en Compose Multiplatform 1.5.11.
* **Estado:** `val pagerState = rememberPagerState(initialPage = 0) { listaAmbientes.size }`.
* **Fuente Única de Verdad:**
  - El contador dinámico `"X de 5"` lee directamente `pagerState.currentPage + 1`.
  - La fila de indicadores resalta el índice activo expandiéndolo a una cápsula de 24 dp.
  - Al pulsar cualquier indicador se lanza `coroutineScope.launch { pagerState.animateScrollToPage(index) }`.
  - Los botones **Anterior** y **Siguiente** se deshabilitan en los límites (`currentPage == 0` y `currentPage == lastIndex` respectivamente).

---

## 6. Estado de Compilación y Ejecución por Plataforma

Para mantener estricto rigor académico, se distingue de manera explícita y transparente el estado de cada plataforma:

### A. Plataforma Android (`androidApp`):
* **Compilación:** **COMPILADA EXITOSAMENTE**
  - Android SDK instalado en espacio de usuario: `/home/merari/Android/Sdk` (API 34, Build-Tools 34.0.0, Platform-Tools 35.0.2).
  - Tarea ejecutada: `./gradlew :androidApp:assembleDebug` (BUILD SUCCESSFUL).
  - **APK Generado:** [`androidApp/build/outputs/apk/debug/androidApp-debug.apk`](file:///home/merari/Descargas/hw2/androidApp/build/outputs/apk/debug/androidApp-debug.apk) (17 MB).
* **Ejecución y Verificación en Emulador:** **EJECUTADA Y VERIFICADA EN VIVO**
  - **Dispositivo Virtual:** AVD `pixel34` (Google APIs Intel x86_64, Android 14 / API 34).
  - **Aceleración por Hardware:** KVM activa (`/dev/kvm`), renderizado gráfico acelerado con SwiftShader/Vulkan.
  - **Pruebas de Funcionalidad Superadas al 100%:**
    1. Visualización correcta en el grid principal de 8 módulos con el badge "Carrusel" y el control `HorizontalPager`.
    2. Carga y renderizado óptimo de las 5 fotografías locales (Sala, Cocina, Dormitorio, Estudio, Jardín).
    3. Deslizamiento gestual horizontal (swipe) fluido y animaciones de transición.
    4. Sincronización bidireccional de la fila de indicadores con cambio dinámico de tamaño (cápsula expandida activa).
    5. Contador textual dinámico reactivo ("1 de 5" a "5 de 5").
    6. Comportamiento en límites: botón *Anterior* deshabilitado en página 1; botón *Siguiente* deshabilitado en página 5.
    7. Retorno al menú principal mediante el botón de retroceso de `SmartTopBar`.
    8. **Verificación Offline:** Se deshabilitaron las interfaces Wi-Fi y datos móviles en el emulador (`svc wifi disable` y `svc data disable`). El carrusel continuó operando de forma 100% autónoma y sin dependencias de red, confirmando el empaquetado local de los recursos.

### B. Plataforma Desktop (JVM - Linux):
* **Compilación:** **COMPILADA EXITOSAMENTE**
  - Tarea: `./gradlew :desktopApp:compileKotlinJvm` (BUILD SUCCESSFUL).
  - Tarea: `./gradlew :desktopApp:packageUberJarForCurrentOS` (Generó binario `KotlinMultiplatformComposeDesktopApplication-linux-x64-1.0.0.jar`).
* **Ejecución:** **EJECUTADA Y COMPROBADA EN VIVO**
  - Se ejecutó el binario en la sesión gráfica activa (`DISPLAY=:0`) con JDK 17 Headful.
  - Se verificó la carga de las 5 fotos locales, navegación táctil, swipe horizontal, sincronización de indicadores y retorno al inicio.

### C. Plataforma iOS (`iosApp`):
* **Compilación:** **NO SOPORTADA EN ESTE ENTORNO LINUX**
  - El plugin de Kotlin/Native deshabilita automáticamente los targets iOS (`iosArm64`, `iosSimulatorArm64`, `iosX64`) en hosts Linux debido a la dependencia obligatoria del toolchain de Xcode y macOS SDKs.

---

## 7. Catálogo de Evidencias Disponibles

Las capturas de pantalla obtenidas en las sesiones de prueba se encuentran organizadas y diferenciadas por plataforma:

### Evidencias Android (Nativas en Emulador Android 14 API 34):
📁 **[`/home/merari/Descargas/hw2/capturas_reporte_U2_carrusel/android/`](file:///home/merari/Descargas/hw2/capturas_reporte_U2_carrusel/android/)**
* **`figura_01_android_inicio.png`**: Pantalla inicial de SmartHome Hub mostrando la tarjeta de presentación y los primeros módulos.
* **`figura_01_android_modulo_carrusel.png`**: Desplazamiento en el grid mostrando el 8vo módulo "Ambientes del hogar" con su badge y botón "Abrir".
* **`figura_02_android_carrusel_sala.png`**: Página 1 ("1 de 5") - Sala de Estar, botón *Anterior* deshabilitado e indicador inicial expandido.
* **`figura_03_android_carrusel_cocina.png`**: Página 2 ("2 de 5") - Cocina Integral tras presionar botón *Siguiente*.
* **`figura_04_android_carrusel_dormitorio.png`**: Página 3 ("3 de 5") - Habitación tras gesto de swipe táctil horizontal.
* **`figura_05_android_carrusel_estudio.png`**: Página 4 ("4 de 5") - Estudio / Oficina con degradado de texto y metadatos del ambiente.
* **`figura_06_android_carrusel_jardin.png`**: Página 5 ("5 de 5") - Jardín / Terraza, botón *Siguiente* deshabilitado en el límite superior.
* **`figura_07_android_regreso_inicio.png`**: Retorno exitoso a la pantalla Home tras pulsar la flecha de navegación de la barra superior.
* **`figura_08_android_carrusel_offline.png`**: Ejecución en modo avión / sin conexión (Wi-Fi y datos desactivados), demostrando carga 100% local.

### Evidencias Desktop (JVM Linux):
📁 **[`/home/merari/Descargas/hw2/capturas_reporte_U2_carrusel/`](file:///home/merari/Descargas/hw2/capturas_reporte_U2_carrusel/)**
* **`figura_01_inicio_modulo_carrusel.png`**: Grid de 8 módulos activos en ventana de escritorio.
* **`figura_02_carrusel_sala.png`** a **`figura_06_carrusel_jardin.png`**: Secuencia completa de páginas en resolución de escritorio.
* **`figura_07_recursos_drawable.png`**: Inspección de los archivos de recursos en consola.
* **`figura_08_compilacion_exitosa.png`**: Registro terminal del build exitoso de Gradle.

---

## 8. Verificación de Recursos y Limpieza de Redundancias
1. **Recurso Real Consumido:**
   - La función `painterResource("ambiente_*.jpg")` de Compose Multiplatform 1.5.11 carga directamente del classpath raíz de `shared/src/commonMain/resources/`.
   - Las 5 imágenes redundantes que se habían colocado inicialmente en `shared/src/commonMain/resources/drawable/` fueron eliminadas tras comprobar que no eran requeridas por Compose, evitando duplicación innecesaria en el paquete compartido.
2. **Adaptación Académica Android (`androidMain/res/drawable/`):**
   - Se conservaron intactas las 5 imágenes en `shared/src/androidMain/res/drawable/`.
   - Esto satisface la consigna académica respecto a "Resources/drawable" para el entorno Android compilado sin romper la portabilidad de `commonMain`.
