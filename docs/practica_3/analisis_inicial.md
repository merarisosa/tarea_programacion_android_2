# Auditoría Inicial y Análisis de Arquitectura — Práctica 3: Modales (Encuestas Smart Home)

**Materia:** Programación Avanzada de Dispositivos Móviles  
**Docente:** Dionisio Pérez Pérez  
**Proyecto:** SmartHome Hub (Kotlin Multiplatform + Compose Multiplatform)  
**Autor:** Merari May Sosa  
**Fecha:** Noviembre 2026  
**Entorno de Trabajo:** Linux Ubuntu (x86_64), JDK 17 LTS, Android SDK 34  

---

## 1. Localización y Estructura del Proyecto

* **Directorio raíz:** `/home/merari/Descargas/hw2`
* **Nombre del proyecto Gradle:** `SmartHomeHub` (definido en `settings.gradle.kts`)
* **Módulos detectados:**
  - `:shared`: Módulo central Kotlin Multiplatform que compila para Android (`androidMain`), JVM Desktop (`desktopMain`) e iOS (`iosMain`). Contiene toda la lógica de presentación compartida, componentes visuales, modelos, temas y navegación.
  - `:androidApp`: Aplicación Android nativa con `MainActivity.kt` derivada de `AppCompatActivity` que invoca `@Composable fun MainView() = App()`.
  - `:desktopApp`: Aplicación JVM para escritorio que ejecuta `MainView()` sobre GLFW/Skiko en Linux/macOS/Windows.
  - `iosApp`: Proyecto Xcode complementario para iOS.

---

## 2. Pila Tecnológica y Versiones de Dependencias

* **Kotlin:** `1.9.21`
* **Compose Multiplatform:** `1.5.11`
* **Android Gradle Plugin (AGP):** `8.0.2`
* **Android SDK:** Compile SDK 34, Target SDK 34, Min SDK 24 (`android.suppressUnsupportedCompileSdk=34`).
* **Java:** JDK 17 LTS (`jdk-17.0.20.1+1`).
* **Material Design 3:** Implementación oficial de Jetpack Compose / Compose Multiplatform 1.5.11 (`androidx.compose.material3`).
* **Iconografía:** `compose.materialIconsExtended` disponible en `:shared/commonMain`.
* **Recursos:** `compose.components.resources` (empaquetado local offline).

---

## 3. Estado del Repositorio Git y Estrategia de Ramas

* **Repositorio remoto:** `https://github.com/merarisosa/tarea_programacion_android_2.git` (`origin`)
* **Último commit base en `main`:** `8a721e7` (*test(android): verify image carousel on Android 14, add evidences and cleanup resources*).
* **Rama de trabajo creada para esta práctica:** `feature/practica-3-modales`.
* **Estado de trabajo:** Árbol limpio antes de iniciar modificaciones; sincronizado con `origin/main`.
* **Seguridad de ramas:** Se trabaja de manera aislada en `feature/practica-3-modales` sin alterar `main` ni realizar operaciones destructivas (`reset --hard`, `clean -fd`, `force push`).

---

## 4. Arquitectura y Componentes Reutilizables

1. **Gestión de Navegación Existente:**
   - Enum centralizado [`Screen`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/navigation/Screen.kt) con metadatos académicos (`title`, `controlName`, `mauiEquivalent`, `description`, `badge`).
   - Contenedor raíz [`App.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/App.kt) con estado reactivo `var currentScreen by remember { mutableStateOf(Screen.HOME) }`, envuelto en `Scaffold` con [`SmartTopBar`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/components/SmartTopBar.kt).
   - Menú principal [`HomeScreen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt) que renderiza un `LazyVerticalGrid` de dos columnas con tarjetas interactivas `ControlCard` y contador dinámico de módulos.

2. **Diseño Visual y Temas:**
   - [`SmartHomeTheme`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/theme/Theme.kt) configurado en modo oscuro por defecto (apariencia domótica profesional de noche).
   - Paleta corporativa en [`Color.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/theme/Color.kt): `PrimaryDark` (`#0F172A`), `SurfaceDark` (`#1E293B`), `CardBackground` (`#182234`), `CardBorder` (`#334155`), acentos (`AccentTeal` `#06B6D4`, `AccentEmerald` `#10B981`, `AccentAmber` `#F59E0B`, `AccentPurple` `#8B5CF6`).

3. **Módulos Previos Intactos:**
   - Se conservan estrictamente las 8 pantallas y funcionalidades anteriores:
     1. `DATE_PICKER`: Modo Vacaciones (`DatePicker`).
     2. `TIME_PICKER`: Rutinas de Encendido (`TimePicker`).
     3. `SLIDER`: Regulador de Luz (`Slider`).
     4. `IMAGE`: Cámara Exterior (`Image` con modos de escala).
     5. `BOX_VIEW`: Luces RGB y Decoración (`BoxView`).
     6. `SWITCH`: Sistema de Alarma (`Switch`).
     7. `PROGRESS`: Monitor de Energía (`ProgressIndicator`).
     8. `IMAGE_CAROUSEL`: Ambientes del hogar (`HorizontalPager`).

---

## 5. Estrategia de Implementación de la Práctica 3 (Modales y Encuestas)

### A. Equivalencias Académicas (Capítulo 4: Modales y Formularios)
El ejercicio del libro de trabajo de la asignatura ilustra un formulario modal de encuesta en Xamarin.Forms utilizando componentes clásicos. Su traducción técnica óptima y moderna a Compose Multiplatform 1.5.11 es:

| Componente Xamarin.Forms | Equivalente KMP / Compose Multiplatform | Justificación y Rol en SmartHome Hub |
| :--- | :--- | :--- |
| `StackLayout` | `Column(verticalArrangement = ...)` | Layout vertical apilado con scroll reactivo |
| `Entry` | `OutlinedTextField` | Campo de texto con label flotante, soporte de error y teclado accesible |
| `Label` | `Text` (Material 3 Typography) | Tipografía escalable con colores temáticos |
| `DatePicker` | `DatePicker` / `DatePickerDialog` (Material 3) | Selector modal de calendario con validación de fechas límite |
| `ActionSheet` / Modal Picker | `ModalBottomSheet` / Selector Modal | Hoja inferior modal para seleccionar el dispositivo favorito |
| `Button` | `Button` / `OutlinedButton` | Acciones de confirmación y cancelación estilizadas |
| `PushModalAsync` / `PopModalAsync` | `Dialog(onDismissRequest)` / Modal Sheet | Presentación en capa superpuesta modal real con oscurecimiento de fondo (scrim) |
| `MessagingCenter.Send` / Callbacks | State Hoisting + Callbacks (`onSurveyAdded`, `onDismiss`) | Mecanismo idiomático reactivo en Compose para notificar eventos al padre |

### B. Archivos a Crear y Modificar:
1. **Modificar:** [`shared/src/commonMain/kotlin/navigation/Screen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/navigation/Screen.kt):
   - Agregar `SURVEYS` ("Encuestas y opiniones", control "Modal Dialog / Sheet", MAUI "Modal Page & ActionSheet", badge "Modales").
2. **Modificar:** [`shared/src/commonMain/kotlin/App.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/App.kt):
   - Conectar `Screen.SURVEYS -> SurveyScreen(...)` dentro del `when (currentScreen)`.
3. **Modificar:** [`shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt):
   - Agregar el 9no módulo `ControlItem` para `Screen.SURVEYS` con icono `Icons.Default.RateReview` / `Assignment` y acento violeta/índigo (`Color(0xFF6366F1)`).
   - El contador dinámico superior reflejará automáticamente **9 Módulos Activos**.
4. **Crear:** `shared/src/commonMain/kotlin/data/model/Survey.kt`:
   - Modelo de datos inmutable para las encuestas (`id`, `name`, `dateMillis`, `formattedDate`, `favoriteDevice`, `rating`, `comments`, `createdAt`).
5. **Crear:** `shared/src/commonMain/kotlin/data/repository/SurveyRepository.kt`:
   - Repositorio en memoria con estado observable (`StateFlow` / `mutableStateListOf`) con datos iniciales demostrativos de domótica para enriquecer la experiencia desde el primer arranque.
6. **Crear:** `shared/src/commonMain/kotlin/ui/screens/SurveyScreen.kt`:
   - Pantalla principal de encuestas: cabecera informativa, tarjeta académica de equivalencias, botón flotante/principal `+ Nueva encuesta`, estado vacío y listado reactivo de tarjetas de encuesta.
7. **Crear:** `shared/src/commonMain/kotlin/ui/components/SurveyModalDialog.kt`:
   - Formulario modal real superpuesto mediante `Dialog(onDismissRequest = ...)` con `DialogProperties(usePlatformDefaultWidth = false)`:
     - Nombre con validación (`OutlinedTextField`).
     - Selector modal de fecha (`DatePickerDialog` de Material 3 con restricción para no seleccionar fechas futuras).
     - Selector modal de dispositivo favorito (`ModalBottomSheet` o diálogo de opciones modal seleccionable).
     - Calificación interactiva de 1 a 5 estrellas con iconos `Icons.Default.Star` / `Icons.Default.StarBorder`.
     - Campo multilínea de comentarios y mejoras con límite razonable.
     - Botones de acción: `Cancelar` y `Guardar encuesta`.
     - Validaciones reactivas y mensajes de error visibles.
8. **Crear:** `shared/src/commonMain/kotlin/ui/components/DeviceSelectionSheet.kt`:
   - Selector modal de hoja inferior para seleccionar entre las 7 opciones de dispositivos inteligentes domóticos.

---

## 6. Dependencias y Riesgos de Compatibilidad

* **Dependencias:** No se requieren librerías de terceros externas. Compose Multiplatform 1.5.11 cuenta con todos los componentes requeridos:
  - `androidx.compose.ui.window.Dialog`
  - `androidx.compose.material3.ModalBottomSheet` / `rememberModalBottomSheetState`
  - `androidx.compose.material3.DatePicker` / `rememberDatePickerState`
  - `androidx.compose.material3.AlertDialog`
* **Riesgo 1 (DatePicker / ModalBottomSheet OptIn):** Se requiere anotar las composables correspondientes con `@OptIn(ExperimentalMaterial3Api::class)`.
* **Riesgo 2 (Ajuste de Teclado en Diálogos Modales):** En pantallas pequeñas de Android, el teclado virtual puede superponerse a los campos inferiores del diálogo. Se resolverá configurando un contenedor con `Modifier.verticalScroll(rememberScrollState())` y dimensionamiento adecuado con `imePadding()`.
* **Riesgo 3 (Targets no-Linux):** Al compilar en Linux, solo se construyen `:androidApp` y `:desktopApp`. La verificación en emulador Android se realizará con aceleración KVM usando el AVD `pixel34` instalado previamente en `/home/merari/Android/Sdk`.

---

## 7. Conclusión de la Auditoría

El entorno se encuentra listo, estable y verificado. No existen bloqueos críticos. Se procede a la fase de planificación detallada e implementación de la Práctica 3.
