# Documentación Técnica de Implementación — Práctica 3: Modales (Encuestas Smart Home)

**Materia:** Programación Avanzada de Dispositivos Móviles  
**Docente:** Dionisio Pérez Pérez  
**Proyecto:** SmartHome Hub (Kotlin Multiplatform + Compose Multiplatform)  
**Autor:** Merari May Sosa  
**Fecha:** Noviembre 2026  

---

## 1. Visión General de la Arquitectura

En esta tercera práctica se integró el noveno módulo interactivo a la solución domótica **SmartHome Hub**, denominado **"Encuestas y opiniones"** (`Screen.SURVEYS`). Su propósito principal es demostrar la implementación, ciclo de vida y comunicación de **interfaces modales** en Compose Multiplatform, estableciendo una equivalencia directa con los patrones de páginas modales (`PushModalAsync` / `PopModalAsync`), selectores de acciones (`DisplayActionSheet`) y mensajería desacoplada (`MessagingCenter`) de Xamarin.Forms / .NET MAUI.

El desarrollo se llevó a cabo respetando estrictamente la arquitectura limpia y reactiva de Kotlin Multiplatform, garantizando portabilidad sobre Android (`androidApp`) y JVM Desktop (`desktopApp`), manteniendo 100% funcionales las 8 pantallas desarrolladas en las prácticas precedentes.

---

## 2. Inventario de Archivos Creados y Modificados

### A. Archivos Creados:
1. **[`shared/src/commonMain/kotlin/data/model/Survey.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/data/model/Survey.kt)**:
   - Entidad inmutable que modela la respuesta del participante: `id`, `name`, `dateMillis`, `formattedDate`, `favoriteDevice`, `rating` (1 a 5), `comments` y `createdAt`.
2. **[`shared/src/commonMain/kotlin/data/model/SmartDeviceOption.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/data/model/SmartDeviceOption.kt)**:
   - Catálogo estático de los 7 dispositivos inteligentes evaluados (Iluminación, Climatización, Cámaras, Cerraduras, Enchufes, Asistentes de voz y Otro), asociando nombre, descripción técnica e iconografía de Material Icons.
3. **[`shared/src/commonMain/kotlin/data/repository/SurveyRepository.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/data/repository/SurveyRepository.kt)**:
   - Almacén de estado reactivo en memoria basado en `mutableStateListOf<Survey>()`, con operaciones de inserción al inicio (LIFO), validación de unicidad por ID, eliminación y limpieza.
4. **[`shared/src/commonMain/kotlin/util/PlatformUtils.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/util/PlatformUtils.kt)** (y sus implementaciones `android`, `desktop`, `ios`):
   - Mecanismo `expect/actual` para obtener el timestamp epoch actual (`getCurrentEpochMillis()`) y formatear fechas en cadena `"dd/MM/yyyy"` (`formatEpochDate()`) sin incorporar dependencias pesadas de terceros.
5. **[`shared/src/commonMain/kotlin/ui/components/DeviceSelectionSheet.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/components/DeviceSelectionSheet.kt)**:
   - Componente modal anclado en la parte inferior (ActionSheet modal), con oscurecimiento de fondo (scrim), barra de agarre (drag handle), tarjetas interactivas de dispositivos y confirmación mediante check visual.
6. **[`shared/src/commonMain/kotlin/ui/components/SurveyModalDialog.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/components/SurveyModalDialog.kt)**:
   - Formulario modal completo desplegado sobre `Dialog(onDismissRequest, properties = DialogProperties(usePlatformDefaultWidth = false))`, gestionando validaciones en vivo, calendario emergente (`DatePickerDialog`), selector de dispositivos, calificación interactiva de 5 estrellas, contador de caracteres y diálogo de éxito (`AlertDialog`).
7. **[`shared/src/commonMain/kotlin/ui/screens/SurveyScreen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/screens/SurveyScreen.kt)**:
   - Pantalla anfitriona del módulo que renderiza el encabezado corporativo índigo, la tarjeta informativa de equivalencias académicas (`EquivalenceCard`), el botón de apertura modal `+ Nueva encuesta`, el estado vacío cuando no hay registros y la lista reactiva de tarjetas de encuesta registradas.
8. **[`shared/src/commonTest/kotlin/data/SurveyRepositoryTest.kt`](file:///home/merari/Descargas/hw2/shared/src/commonTest/kotlin/data/SurveyRepositoryTest.kt)**:
   - Batería de 5 pruebas unitarias automatizadas que validan el estado inicial, la adición exitosa, el orden cronológico inverso, la prevención de duplicados y la eliminación.

### B. Archivos Modificados:
1. **[`shared/src/commonMain/kotlin/navigation/Screen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/navigation/Screen.kt)**:
   - Incorporación de la entrada `Screen.SURVEYS` con metadatos para la barra de navegación y tarjetas del dashboard.
2. **[`shared/src/commonMain/kotlin/App.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/App.kt)**:
   - Enlace de `Screen.SURVEYS -> SurveyScreen()` dentro del selector `when (currentScreen)`, respetando la barra superior unificada `SmartTopBar`.
3. **[`shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt`](file:///home/merari/Descargas/hw2/shared/src/commonMain/kotlin/ui/screens/HomeScreen.kt)**:
   - Integración del noveno `ControlItem` para `Screen.SURVEYS` con icono `Icons.Default.RateReview` y acento índigo (`Color(0xFF6366F1)`). El contador del dashboard se actualiza dinámicamente a **9 Módulos Activos**.
4. **[`shared/build.gradle.kts`](file:///home/merari/Descargas/hw2/shared/build.gradle.kts)**:
   - Configuración del source set `commonTest` con `kotlin("test")` para soportar pruebas unitarias multiplataforma.

---

## 3. Comportamiento y Flujo de Navegación Modal

A diferencia de una transición ordinaria de pantalla donde el árbol visual es reemplazado en su totalidad, la presentación modal en esta práctica cumple con los principios de diseño de interfaz de usuario para tareas transaccionales:

```
[Dashboard Principal (HomeScreen)]
        │
        ▼ (Tocar Tarjeta "Encuestas y opiniones")
[Pantalla de Encuestas (SurveyScreen)]
        │
        ▼ (Tocar "+ Nueva encuesta")
 ┌────────────────────────────────────────────────────────┐
 │ [Ventana Modal: SurveyModalDialog]                     │
 │  - Fondo oscurecido con scrim (Color.Black 70% alfa)   │
 │  - Contenedor flotante con elevación de 8dp y bordes   │
 │  - Botón de cierre ("X") y cancelar                    │
 │                                                        │
 │   ├── (Tocar Fecha) ──> [DatePickerDialog Modal]       │
 │   │                     (Calendario Material 3)        │
 │   │                                                    │
 │   └── (Tocar Dispositivo) ──> [DeviceSelectionSheet]   │
 │                               (ActionSheet Inferior)   │
 │                                                        │
 │   ├── (Validar y Guardar) ──> [AlertDialog Éxito]     │
 └─────────────────────────────┬──────────────────────────┘
                               │ (Aceptar y Cerrar Modal)
                               ▼
[Pantalla de Encuestas Actualizada con Tarjeta Registrada]
```

### Mecanismo de Comunicación (Equivalente MessagingCenter):
En Xamarin.Forms, una página modal secundaria suele comunicarse con la página que la invocó a través de `MessagingCenter.Send<TSender>(this, "SurveyAdded", data)`. En Compose Multiplatform, el patrón canónico es **State Hoisting** acoplado a **Callbacks fuertemente tipados**:
* `SurveyScreen` declara la función lambda `onSurveySaved = { newSurvey -> SurveyRepository.addSurvey(newSurvey) }`.
* `SurveyModalDialog` recibe dicha función y la invoca una vez que el usuario valida la entrada y confirma el diálogo de éxito.
* El repositorio actualiza su `mutableStateListOf`, lo que desencadena una recomposición atómica en `SurveyScreen`, mostrando la tarjeta en milisegundos sin recargar la pantalla.

---

## 4. Validaciones del Formulario

El formulario implementa validaciones estrictas en tiempo de ejecución:
1. **Nombre del Participante:** No puede ser nulo ni estar compuesto únicamente de espacios en blanco (`name.trim().isBlank()`). Si es inválido, muestra `"El nombre del participante es obligatorio"` en rojo y resalta el borde de error de `OutlinedTextField`.
2. **Fecha de Encuesta:** Se inicializa en la fecha del sistema (`getCurrentEpochMillis()`). Si el usuario selecciona una fecha futura mediante el selector de calendario, el sistema detecta que `picked > currentMillis + 86_400_000L` y emite el mensaje `"No puedes seleccionar una fecha futura"`.
3. **Dispositivo Inteligente:** Campo obligatorio. Si el usuario intenta guardar sin seleccionar una opción del ActionSheet, se genera el error `"Debes seleccionar tu dispositivo inteligente favorito"`.
4. **Calificación por Estrellas:** Requiere seleccionar de 1 a 5 estrellas (`rating > 0`). Si no se selecciona, emite `"Por favor selecciona una calificación de 1 a 5 estrellas"`.
5. **Comentarios Adicionales:** Campo opcional con contador dinámico visible `"${comments.length}/250"` y límite rígido de 250 caracteres.

---

## 5. Diseño Visual y Estilo Material Design 3

La interfaz mantiene coherencia estética absoluta con el resto de la aplicación:
* **Modo Oscuro Predeterminado:** Tonos de fondo `PrimaryDark` (`#0F172A`) y `SurfaceDark` (`#1E293B`).
* **Acentos:** Color primario Índigo (`#6366F1`) para la temática de encuestas, Dorado (`#FBBF24`) para las estrellas de satisfacción, y Verde Esmeralda (`#10B981`) para confirmaciones y badges de registro.
* **Manejo del Teclado Virtual (IME):** Se implementó `Modifier.imePadding()` y `Modifier.verticalScroll(rememberScrollState())` dentro del modal para evitar que el teclado cubra los botones de acción en pantallas móviles.
