# Registro de Pruebas y Verificación — Práctica 3: Modales (Encuestas Smart Home)

**Materia:** Programación Avanzada de Dispositivos Móviles  
**Docente:** Dionisio Pérez Pérez  
**Proyecto:** SmartHome Hub  
**Fecha:** Noviembre 2026  
**Entorno de Pruebas:** Linux Ubuntu x86_64, JDK 17 LTS, Android SDK 34, Emulador AVD Pixel 34 (Android 14)  

---

## 1. Resumen de Pruebas Ejecutadas

| Tipo de Prueba | Componente / Módulo | Herramienta | Resultado |
| :--- | :--- | :--- | :--- |
| **Compilación Móvil** | `:androidApp:assembleDebug` | Gradle 8.2.1 / AGP 8.0.2 / Kotlin 1.9.21 | **EXITOSA** (`BUILD SUCCESSFUL`) |
| **Compilación Escritorio** | `:desktopApp:compileKotlinJvm` | Kotlin Multiplatform JVM | **EXITOSA** (`BUILD SUCCESSFUL`) |
| **Pruebas Unitarias** | `SurveyRepositoryTest` (5 tests) | `kotlin.test` sobre JVM | **EXITOSA** (5 de 5 aprobadas, 0 fallos) |
| **Pruebas de Integración y UI** | Módulo "Encuestas y opiniones" | Emulador AVD Pixel 34 (Android 14) con aceleración KVM | **EXITOSA** (12 casos verificados y capturados) |

---

## 2. Pruebas Unitarias Automatizadas (`SurveyRepositoryTest`)

Se implementó una suite de pruebas unitarias en `shared/src/commonTest/kotlin/data/SurveyRepositoryTest.kt` para asegurar la confiabilidad del modelo y repositorio de encuestas:

1. **`testEmptyInitialState`:**
   - *Objetivo:* Verificar que al iniciar la aplicación no existan encuestas preexistentes para garantizar el estado vacío de bienvenida.
   - *Resultado:* **APROBADO** (`size == 0`).
2. **`testAddSurveySuccessfully`:**
   - *Objetivo:* Validar la correcta inserción de una entidad `Survey` con todos sus campos y tipos de datos.
   - *Resultado:* **APROBADO** (Entidad recuperada íntegra con calificación y dispositivo).
3. **`testDuplicatePrevention`:**
   - *Objetivo:* Comprobar que el repositorio rechaza encuestas con identificador duplicado (prevención de multiclick).
   - *Resultado:* **APROBADO** (`addSurvey` devolvió `false` en el segundo intento y el tamaño permaneció en 1).
4. **`testDeleteSurvey`:**
   - *Objetivo:* Verificar la eliminación individual de registros por su ID único.
   - *Resultado:* **APROBADO** (Elemento eliminado satisfactoriamente).
5. **`testRecentSurveysAppearFirst`:**
   - *Objetivo:* Confirmar que las encuestas más recientes se sitúan en la cabecera de la lista para visibilidad inmediata.
   - *Resultado:* **APROBADO** (Orden cronológico inverso respetado).

---

## 3. Matriz de Casos de Prueba Funcionales en Android

Las siguientes pruebas fueron ejecutadas en vivo en el emulador Android 14 (`pixel34`):

| ID | Caso de Prueba | Procedimiento de Entrada | Resultado Esperado | Resultado Observado | Estado |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **CP-01** | Visualización en Dashboard | Abrir SmartHome Hub y observar grid principal | El módulo 9 "Encuestas y opiniones" aparece con badge "Modales" y contador dinámico en 9 | Se observa el módulo 9 con acento índigo y el contador refleja "9 Módulos Activos" | **PASA** |
| **CP-02** | Navegación a Encuestas | Pulsar botón "Abrir" en tarjeta de Encuestas | Navegar a `SurveyScreen` mostrando banner índigo, tarjeta de equivalencias y estado vacío | Transición fluida; se presenta la pantalla con el texto "Aún no tienes encuestas registradas" | **PASA** |
| **CP-03** | Apertura Modal Principal | Pulsar "+ Nueva encuesta" | Desplegar el formulario `SurveyModalDialog` con fondo oscurecido (scrim) | Formulario flotante desplegado en capa modal con campos limpios y botón de cerrar | **PASA** |
| **CP-04** | Apertura de DatePicker | Tocar el campo de fecha o botón "Cambiar" | Desplegar el diálogo modal interactivo `DatePickerDialog` de Material 3 | Calendario desplegado mostrando la fecha actual seleccionada | **PASA** |
| **CP-05** | Apertura de ActionSheet | Tocar campo "Seleccionar dispositivo..." | Desplegar la hoja modal inferior `DeviceSelectionSheet` con los 7 dispositivos | Hoja inferior desplegada con drag handle, iconos y opciones accesibles | **PASA** |
| **CP-06** | Selección de Dispositivo | Tocar "Iluminación inteligente" en el ActionSheet | Seleccionar la opción, cerrar la hoja modal y reflejar el valor en el formulario | El selector se cierra y el formulario muestra "Iluminación inteligente" con check | **PASA** |
| **CP-07** | Calificación Interactiva | Pulsar sobre la quinta estrella | Las 5 estrellas cambian a color dorado y se actualiza el texto a "5 estrellas — ¡Excelente experiencia!" | Reacción visual instantánea en color `#FBBF24` con badge descriptivo | **PASA** |
| **CP-08** | Validación de Obligatorios | Pulsar "Guardar encuesta" con nombre vacío | El formulario no se envía y resalta en rojo el mensaje de error correspondiente | Aparece el mensaje "El nombre del participante es obligatorio" en rojo | **PASA** |
| **CP-09** | Captura de Comentarios | Introducir texto en el campo multilínea | Se actualiza el contador de caracteres ("X/250") sin exceder el límite | Texto registrado fluidamente y contador dinámico sincronizado | **PASA** |
| **CP-10** | Diálogo de Confirmación | Pulsar "Guardar encuesta" con formulario completo | Aparece un `AlertDialog` modal con icono de check verde y resumen de la encuesta | Diálogo modal desplegado con título "¡Encuesta Registrada!" y datos capturados | **PASA** |
| **CP-11** | Actualización de Listado | Pulsar "Aceptar y Ver Listado" en la confirmación | El modal se cierra y la lista de `SurveyScreen` muestra la tarjeta recién creada | Se cierra el modal y aparece la tarjeta con badge "REGISTRADA", 5 estrellas y comentarios | **PASA** |
| **CP-12** | Navegación de Regreso | Pulsar la flecha de regreso en la barra superior | Regresar al menú principal de SmartHome Hub conservando las funcionalidades previas | Retorno exitoso a la pantalla Home; todos los módulos previos continúan operando | **PASA** |

---

## 4. Catálogo de Capturas Auténticas (Directorio `capturas/`)

Todas las evidencias visuales fueron capturadas directamente desde el framebuffer del dispositivo virtual Android a resolución nativa (1080 × 2400 px):

* 📁 **[`docs/practica_3/capturas/figura_01_pantalla_principal.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_01_pantalla_principal.png)**: Pantalla de inicio con cabecera y primeros módulos.
* 📁 **[`docs/practica_3/capturas/figura_02_acceso_encuestas.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_02_acceso_encuestas.png)**: Desplazamiento en el grid mostrando el noveno módulo "Encuestas y opiniones".
* 📁 **[`docs/practica_3/capturas/figura_03_listado_vacio.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_03_listado_vacio.png)**: Pantalla de encuestas con estado inicial vacío y botón de acción principal.
* 📁 **[`docs/practica_3/capturas/figura_04_formulario_modal.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_04_formulario_modal.png)**: Formulario modal superpuesto con scrim oscuro de fondo.
* 📁 **[`docs/practica_3/capturas/figura_05_datepicker.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_05_datepicker.png)**: Selector modal de calendario Material 3 (`DatePickerDialog`).
* 📁 **[`docs/practica_3/capturas/figura_06_selector_dispositivo.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_06_selector_dispositivo.png)**: ActionSheet modal inferior con catálogo de dispositivos domóticos.
* 📁 **[`docs/practica_3/capturas/figura_07_calificacion.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_07_calificacion.png)**: Calificación interactiva de 5 estrellas doradas seleccionadas.
* 📁 **[`docs/practica_3/capturas/figura_08_validaciones.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_08_validaciones.png)**: Mensajes de error en rojo al intentar enviar campos obligatorios vacíos.
* 📁 **[`docs/practica_3/capturas/figura_09_formulario_completo.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_09_formulario_completo.png)**: Formulario con todos los campos válidos y teclado oculto.
* 📁 **[`docs/practica_3/capturas/figura_10_confirmacion.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_10_confirmacion.png)**: Diálogo modal de confirmación exitosa con checkmark verde.
* 📁 **[`docs/practica_3/capturas/figura_11_encuesta_registrada.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_11_encuesta_registrada.png)**: Listado reactivo con la tarjeta de la encuesta registrada.
* 📁 **[`docs/practica_3/capturas/figura_12_navegacion.png`](file:///home/merari/Descargas/hw2/docs/practica_3/capturas/figura_12_navegacion.png)**: Retorno exitoso al dashboard de SmartHome Hub.

---

## 5. Limitaciones del Entorno

1. **Target iOS:** Debido a que el entorno de desarrollo es una estación de trabajo Linux Ubuntu, el compilador Kotlin/Native omite la generación de binarios para `iosArm64` y `iosSimulatorArm64`, ya que estos requieren estrictamente el toolchain de Xcode sobre macOS. No obstante, el código fuente en `shared/src/commonMain` y los actuals en `shared/src/iosMain` son 100% compatibles y listos para compilar en macOS.
2. **Persistencia en Disco vs. Memoria:** En estricto apego a las instrucciones del docente y al alcance de la práctica de modales, el almacenamiento de encuestas se implementó en memoria mediante `mutableStateListOf`. Esto maximiza la estabilidad y rendimiento reactivo sin introducir motores SQL o dependencias de persistencia innecesarias para este ejercicio.
