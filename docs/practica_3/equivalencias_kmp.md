# Matriz de Equivalencias Técnicas: Xamarin.Forms / .NET MAUI vs. Kotlin Multiplatform (Compose UI)

**Materia:** Programación Avanzada de Dispositivos Móviles  
**Docente:** Dionisio Pérez Pérez  
**Proyecto:** SmartHome Hub — Práctica 3: Modales  
**Autor:** Merari May Sosa  
**Fecha:** Noviembre 2026  

---

## 1. Tabla Comparativa de Componentes y Mecanismos

La siguiente matriz documenta la correspondencia directa entre los elementos del ejercicio original del libro de trabajo (Capítulo 4: Modales y Formularios) y los componentes implementados en Compose Multiplatform 1.5.11:

| Componente / Mecanismo en Xamarin.Forms | Componente / Mecanismo en Compose Multiplatform | Rol en SmartHome Hub (Práctica 3) | Diferencias Técnicas y de Paradigma |
| :--- | :--- | :--- | :--- |
| **`StackLayout`** (`Orientation="Vertical"`) | **`Column`** (`verticalArrangement = ...`) | Contenedor principal del formulario y de las secciones verticales. | En Xamarin el layout se recalcula mediante un motor de doble pase XAML (measure/layout). En Compose se compila como funciones composables en árbol unidireccional con paso único eficiente. |
| **`Entry`** (`Placeholder`, `Text`) | **`OutlinedTextField`** (`label`, `isError`) | Campo para capturar el nombre del participante de la encuesta. | `Entry` utiliza widgets nativos de plataforma envueltos por renderers. `OutlinedTextField` dibuja un componente reactivo Material 3 con label flotante animado, manejo de error en rojo y soporte nativo de IME/teclado. |
| **`Editor`** (`AutoSize`, `HeightRequest`) | **`OutlinedTextField`** (`minLines = 3`, `maxLines = 4`) | Campo multilínea para comentarios adicionales y sugerencias. | En Compose no se requiere un control distinto para multilínea; se parametriza `minLines`, `maxLines` y `singleLine = false`, gestionando un contador dinámico (`length/250`). |
| **`Label`** (`Text`, `FontSize`, `TextColor`) | **`Text`** (`style = MaterialTheme.typography...`) | Títulos, subtítulos, instrucciones, badges y textos descriptivos. | El sistema tipográfico de Compose escala automáticamente con `sp` según las preferencias del sistema operativo, respetando la paleta dinámica `colorScheme`. |
| **`DatePicker`** (`Date`, `Format`) | **`DatePickerDialog` + `DatePicker`** (Material 3) | Selección de fecha de la encuesta con bloqueo de fechas futuras. | En Xamarin el `DatePicker` abre un diálogo nativo dependiente del SO (Spinner en Android, rueda en iOS). En Compose se utiliza un calendario reactivo Material 3 uniforme y personalizable con validación temporal. |
| **`DisplayActionSheet`** | **`DeviceSelectionSheet`** (Modal ActionSheet) | Menú modal emergente para seleccionar el dispositivo favorito. | `DisplayActionSheet` en Xamarin muestra un menú modal bloqueante basado en promises (`Task<string>`). En Compose se implementa un diálogo modal anclado abajo con scrim, barra de agarre y tarjetas interactivas. |
| **`Button`** (`Clicked`, `Text`) | **`Button` / `OutlinedButton`** (`onClick`) | Acciones de "Guardar encuesta", "Cancelar", "Cambiar" y "+ Nueva encuesta". | Los botones en Compose reciben lambdas puras, soportan contenido composable arbitrario (iconos + texto en Row) y elevan el estado sin acoplamiento a eventos C#. |
| **`PushModalAsync`** | **`Dialog(properties = DialogProperties(...))`** | Apertura del formulario de encuesta sobre la pantalla actual. | En Xamarin `PushModalAsync` apila una página completa en la pila modal (`ModalStack`). En Compose se renderiza una ventana modal superpuesta con scrim oscurecido y cierre controlado por callback (`onDismissRequest`). |
| **`PopModalAsync`** | **`onDismiss()`** (Cierre de estado modal) | Cierre del formulario tras cancelar o tras confirmar el guardado. | En Xamarin se desapila la página de la pila de navegación. En Compose el padre simplemente conmuta `showModalForm = false`, destruyendo la subcomposición de manera segura. |
| **`DisplayAlert`** | **`AlertDialog`** (Material 3) | Diálogo de confirmación con icono verde tras guardar la encuesta. | En Xamarin devuelve un `Task<bool>`. En Compose es un componente declarativo con slots `icon`, `title`, `text`, `confirmButton` y `dismissButton`. |
| **`MessagingCenter.Send<T>`** | **State Hoisting + Callbacks** (`onSurveySaved`) | Notificación de nueva encuesta desde el modal hacia el listado principal. | `MessagingCenter` implementa un bus de eventos global basado en cadenas, susceptible a fugas de memoria si no se desuscribe. Compose emplea paso de funciones de primera clase con tipos estrictos. |

---

## 2. Análisis Comparativo Profundo

### 2.1. Paradigma XAML vs. Composición Declarativa
En el modelo clásico de Xamarin.Forms, los controles son instancias de objetos mutables (`new Entry()`) cuyo ciclo de vida está gobernado por el runtime de C# y puenteado mediante *Renderers* hacia las vistas nativas de Android (`EditText`) o iOS (`UITextField`).

En contraste, Compose Multiplatform opera bajo el principio de **Recomposición Declarativa**:
* La interfaz de usuario es una función directa del estado: `UI = f(State)`.
* Cuando el usuario escribe su nombre o selecciona una estrella, no se "modifica la propiedad de un widget", sino que se muta un valor observable (`mutableStateOf`), haciendo que el compilador de Compose invoque únicamente las regiones del árbol visual que leen dicho estado.

### 2.2. Manejo de Diálogos Modales y el "Window Token" en Móviles
En aplicaciones móviles Android, la presentación de diálogos y hojas emergentes puede presentar problemas cuando se anidan múltiples ventanas modales (por ejemplo, intentar abrir un menú contextual sobre un modal existente).

En SmartHome Hub se resolvió esta problemática utilizando un diseño modal coordinado:
1. El modal principal (`SurveyModalDialog`) utiliza `DialogProperties(usePlatformDefaultWidth = false)` con `Modifier.imePadding()`, asegurando que el teclado virtual empuje el contenido desplazable sin distorsionar la geometría.
2. Los sub-modales (`DatePickerDialog` y `DeviceSelectionSheet`) son activados condicionalmente y se dibujan en la capa superior inmediata, garantizando que el usuario nunca experimente bloqueos de foco ni inconsistencias visuales.

### 2.3. Desacoplamiento de la Persistencia y Reactividad
En lugar de depender de singletons globales o almacenamiento estático, el repositorio `SurveyRepository` expone un `SnapshotStateList`:
```kotlin
object SurveyRepository {
    private val _surveys = mutableStateListOf<Survey>()
    val surveys: List<Survey> get() = _surveys
    ...
}
```
Cualquier modificación en esta lista notifica de inmediato a los observadores composables (`SurveyScreen`), logrando que la tarjeta de la nueva encuesta aparezca en pantalla sin requerir llamadas explícitas a métodos como `adapter.notifyDataSetChanged()` o recargas de página.
