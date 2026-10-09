# SmartHome Hub 🏠
### Práctica de Controles de Interfaz en Kotlin Multiplatform (Compose UI)
**Materia:** Programación Avanzada de Dispositivos Móviles  
**Nivel:** Maestría en Sistemas Computacionales  
**Autor:** Merari May Sosa  

---

## 📌 Descripción del Proyecto

**SmartHome Hub** es una aplicación multiplataforma desarrollada con **Kotlin Multiplatform (KMP)** y **Compose Multiplatform** (desarrollado por JetBrains). 

El proyecto resuelve la práctica de implementación y visualización de controles visuales avanzados a través de una arquitectura de domótica y automatización del hogar. La pantalla principal cuenta con un **Grid responsivo de 2 columnas** donde cada tarjeta interactiva actúa como botón para navegar a una pantalla dedicada a cada control.

---

## 🔄 Tabla de Equivalencias: Xamarin / .NET MAUI ➔ Kotlin Multiplatform

Dado que la materia aborda la transición y comparación entre tecnologías móviles, se presenta el mapeo técnico exacto de los controles solicitados:

| Control en Xamarin / .NET MAUI | Equivalente en Compose Multiplatform | Paquete / Componente KMP | Rol en SmartHome Hub |
| :--- | :--- | :--- | :--- |
| **`DatePicker`** | **`DatePicker`** | `androidx.compose.material3.DatePicker` | **Modo Vacaciones**: Selección de fecha para agendar ausencia y bloqueo de seguridad del hogar. |
| **`TimePicker`** | **`TimePicker`** | `androidx.compose.material3.TimePicker` | **Rutinas y Encendido**: Reloj interactivo (dial/manecillas) para programar encendido de luces y cafetera. |
| **`Slider`** | **`Slider`** | `androidx.compose.material3.Slider` | **Regulador de Luz y Clima**: Control continuo (0-100% brillo) y control por pasos (16°C-30°C termostato). |
| **`Image`** | **`Image`** | `androidx.compose.foundation.Image` | **Cámara Exterior**: Visor de cámara perimetral demostrando `ContentScale` (`Crop`, `Fit`, `FillBounds`, `Inside` equivalentes al `Aspect` de MAUI). |
| **`BoxView`** | **`Box`** *(con Modificadores)* | `androidx.compose.foundation.layout.Box` | **Iluminación RGB**: Demostración de `Box` con degradados, esquinas redondeadas (`clip`), bordes, selector de colores y líneas separadoras. |
| **`Switch`** *(Extra 1)* | **`Switch`** | `androidx.compose.material3.Switch` | **Sistema de Alarma**: Armado maestro de seguridad y control independiente de sensores de presencia y sirena. |
| **`ProgressBar` / `ActivityIndicator`** *(Extra 2)* | **`LinearProgressIndicator`** & **`CircularProgressIndicator`** | `androidx.compose.material3.*ProgressIndicator` | **Monitor de Energía**: Indicador circular de consumo diario (kWh), barra lineal de batería solar e indicador indeterminado de sincronización. |

---

## 🏗️ Arquitectura del Proyecto

El proyecto está organizado bajo la estructura oficial recomendada por JetBrains para proyectos KMP:

```text
Smart Home Hub/
├── androidApp/                       # Módulo de la aplicación Android nativa
│   └── src/androidMain/kotlin/       # MainActivity y configuración Android
├── desktopApp/                       # Módulo de la aplicación de escritorio (macOS/JVM)
│   └── src/jvmMain/kotlin/           # Launcher de escritorio Window(...)
├── shared/                           # Módulo de código y lógica 100% compartida
│   └── src/commonMain/kotlin/
│       ├── App.kt                    # Punto de entrada y Scaffold con router reactivo
│       ├── navigation/
│       │   └── Screen.kt             # Modelo declarativo de pantallas y metadatos
│       ├── theme/
│       │   ├── Color.kt              # Paleta domótica moderna (Dark Navy, Cian, Esmeralda)
│       │   └── Theme.kt              # SmartHomeTheme con Material 3
│       └── ui/
│           ├── components/
│           │   └── SmartTopBar.kt    # Barra superior con navegación de regreso (Back)
│           └── screens/
│               ├── HomeScreen.kt             # Grid de 2 columnas con tarjetas/botones
│               ├── DatePickerScreen.kt       # Demostración de DatePicker
│               ├── TimePickerScreen.kt       # Demostración de TimePicker
│               ├── SliderScreen.kt           # Demostración de Slider continuo y discreto
│               ├── ImageScreen.kt            # Demostración de Image y ContentScale
│               ├── BoxViewScreen.kt          # Demostración de Box como BoxView
│               ├── SwitchScreen.kt           # Demostración de Switch (Extra 1)
│               └── ProgressIndicatorScreen.kt# Demostración de ProgressIndicator (Extra 2)
└── README.md
```

---

## 🚀 Cómo Ejecutar el Proyecto

### 1. Ejecutar en Android (Emulador o Dispositivo Físico)
1. Abre **Android Studio**.
2. Selecciona **File ➔ Open** y elige la carpeta `Smart Home Hub`.
3. Espera a que termine la sincronización de Gradle (Gradle Sync).
4. En el selector superior de configuración de ejecución, elige **`androidApp`**.
5. Selecciona tu emulador o dispositivo Android y presiona el botón **Run ▶**.

### 2. Ejecutar en Desktop (macOS / PC nativo)
Gracias a Compose Multiplatform, puedes correr la aplicación directamente en tu computadora sin necesidad de iniciar un emulador de Android:
* Desde la terminal:
  ```bash
  ./gradlew :desktopApp:run
  ```
* O desde Android Studio: selecciona la configuración **`desktopApp`** y pulsa **Run ▶**.

---

## 🌿 Historial de Desarrollo (Git Commits por Fases)

El desarrollo del proyecto se estructuró en etapas incrementales:
1. `feat: initial project structure for Smart Home Hub with Kotlin Multiplatform`
2. `feat(navigation): implement Screen router and SmartHome Material 3 theme`
3. `feat(ui): implement responsive LazyVerticalGrid dashboard in HomeScreen`
4. `feat(controls): add DatePicker and TimePicker screens for automation routines`
5. `feat(controls): add Slider and Image screens for dimmer and camera monitoring`
6. `feat(controls): add BoxViewScreen demonstrating Compose Box as Xamarin BoxView equivalent`
7. `feat(controls): add Switch and ProgressIndicator screens as extra smart controls`
8. `docs: add comprehensive README with architecture, MAUI equivalences, and execution guide`

---

## 🛠️ Tecnologías y Versiones Utilizadas
* **Kotlin:** 1.9.21
* **Compose Multiplatform:** 1.5.11
* **Material 3:** Material Design 3 Multiplatform
* **Gradle:** 8.2.1
* **JDK:** Java 17+ (Compatible con JDK 17, 21 y 23)