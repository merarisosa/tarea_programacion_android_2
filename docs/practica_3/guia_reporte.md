# Guía Académica para Elaboración del Reporte en Word — Práctica 3: Modales

**Institución:** Tecnológico Nacional de México / Instituto Tecnológico de Veracruz  
**Posgrado:** Maestría en Sistemas Computacionales  
**Asignatura:** Programación Avanzada de Dispositivos Móviles  
**Catedrático:** Dr. / Mtro. Dionisio Pérez Pérez  
**Estudiante:** Ing. Merari May Sosa  
**Ciclo Académico:** Semestre 2026-B  

---

## 1. Título de la Práctica
**IMPLEMENTACIÓN DE INTERFACES MODALES Y FORMULARIOS REACTIVOS MEDIANTE COMPOSE MULTIPLATFORM EN UNA APLICACIÓN DOMÓTICA MÓVIL (SMARTHOME HUB)**

---

## 2. Objetivo General
Diseñar, implementar y evaluar un módulo interactivo de encuestas de satisfacción y opinión en la aplicación multiplataforma **SmartHome Hub**, empleando componentes de presentación modal, hojas inferiores de selección de acciones y comunicación reactiva de eventos en **Compose Multiplatform**, estableciendo una comparativa directa con los patrones homólogos de **Xamarin.Forms / .NET MAUI** estudiados en el Capítulo 4 del programa de la asignatura.

---

## 3. Objetivos Específicos
1. Analizar la correspondencia técnica entre los componentes clásicos de Xamarin.Forms (`PushModalAsync`, `DisplayActionSheet`, `DatePicker`, `Entry`, `MessagingCenter`) y las APIs modernas de Compose Multiplatform 1.5.11 con Material Design 3.
2. Desarrollar un formulario transaccional modal superpuesto (`SurveyModalDialog`) que soporte captura de texto con validaciones, calificación interactiva de 1 a 5 estrellas, selección de calendario y hoja modal de dispositivos.
3. Implementar un selector de dispositivos inteligentes basado en el patrón ActionSheet modal (`DeviceSelectionSheet`) con anclaje inferior y oscurecimiento de fondo.
4. Establecer un canal de comunicación desacoplado entre el diálogo modal y la pantalla anfitriona mediante elevación de estado (*State Hoisting*) y callbacks tipados.
5. Verificar el comportamiento integral de la aplicación en el emulador Android con aceleración por hardware (KVM) en entorno Linux, generando un catálogo de 12 evidencias visuales auténticas.

---

## 4. Introducción
En el desarrollo de software para dispositivos móviles contemporáneos, las interfaces de usuario no se limitan a la navegación jerárquica lineal entre pantallas completas. Frecuentemente, los flujos transaccionales —tales como la captura de encuestas, la confirmación de acciones críticas o la selección rápida de parámetros contextuales— exigen presentaciones modales que interrumpan temporalmente el flujo principal sin destruir el estado de la vista de origen.

Históricamente, entornos como Xamarin.Forms resolvían estas necesidades mediante la pila de navegación modal (`PushModalAsync`) y utilidades globales bloqueantes (`DisplayActionSheet`). Con la evolución hacia marcos de trabajo declarativos como Jetpack Compose y Kotlin Multiplatform (KMP), el paradigma evoluciona hacia una composición reactiva donde la visibilidad modal es una propiedad pura del estado observable. El presente reporte documenta la integración de esta funcionalidad dentro del proyecto domótico **SmartHome Hub**, cumpliendo con los estándares de diseño de Material Design 3.

---

## 5. Marco Teórico
* **Presentación Modal:** Patrón de interfaz donde una ventana secundaria se dibuja sobre el contenido previo, capturando el foco de interacción del usuario mediante una capa semitransparente (*scrim*).
* **Compose Multiplatform y Recomposición:** Motor gráfico declarativo desarrollado por JetBrains que traduce código Kotlin común a primitivas gráficas nativas en Android (canvas Skia/Vulkan) y escritorio.
* **Componentes Material Design 3 (M3):**
  - `Dialog` y `DialogProperties`: Control de ventana flotante y políticas de dimensionamiento (`usePlatformDefaultWidth`).
  - `DatePickerDialog` y `DatePickerState`: Calendario reactivo con soporte de restricciones de fechas pasadas y futuras.
  - `OutlinedTextField`: Campo de entrada de texto enriquecido con soporte de estados de error reactivos (`isError`) y mensajes de asistencia (`supportingText`).
* **Equivalencia de Mensajería:** Comparación entre el modelo de bus de eventos global (`MessagingCenter`) de Xamarin y el modelo funcional reactivo basado en *State Hoisting* y *Callbacks* de Compose.

---

## 6. Materiales y Herramientas

| Categoría | Especificación / Herramienta |
| :--- | :--- |
| **Equipo de Cómputo** | Laptop x86_64, Procesador Intel Core i7 con soporte de virtualización VT-x / KVM |
| **Sistema Operativo** | Linux Ubuntu (kernel 6.x) |
| **Kit de Desarrollo Java** | OpenJDK 17 LTS (`jdk-17.0.20.1+1`) |
| **Kit de Desarrollo Móvil** | Android SDK Platform 34 (Android 14), Build-Tools 34.0.0, Platform-Tools 35.0.2 |
| **Emulador Android** | Android Virtual Device (AVD) `pixel34`, Google APIs x86_64 con aceleración KVM |
| **Compilador y Framework** | Kotlin 1.9.21, Compose Multiplatform 1.5.11, Android Gradle Plugin 8.0.2 |
| **Control de Versiones** | Git 2.43 y repositorio remoto en GitHub |

---

## 7. Metodología y Procedimiento de Desarrollo

1. **Auditoría del Código Preexistente:** Inspección del repositorio, preservación de los 8 módulos domóticos anteriores y creación de la rama aislada `feature/practica-3-modales`.
2. **Definición del Modelo de Dominio:** Creación de las entidades inmutables `Survey` y el catálogo de opciones `SmartDeviceOption`.
3. **Capa de Abstracción de Plataforma:** Implementación de utilidades multiplataforma (`PlatformUtils`) con `expect/actual` para fecha y hora en milisegundos sin dependencias externas.
4. **Construcción de Componentes Modales:**
   - Desarrollo de `DeviceSelectionSheet` como hoja modal anclada abajo.
   - Desarrollo de `SurveyModalDialog` con validaciones, estrellas interactivas, integración de `DatePickerDialog` y diálogo de éxito `AlertDialog`.
5. **Integración en la Navegación:** Declaración del enum `Screen.SURVEYS`, tarjeta en el grid de `HomeScreen` y conector en `App.kt`.
6. **Compilación y Pruebas Unitarias:** Ejecución de `./gradlew :shared:desktopTest` logrando 5 pruebas aprobadas al 100%.
7. **Despliegue y Verificación en Emulador Móvil:** Construcción del APK con `./gradlew :androidApp:assembleDebug`, instalación vía `adb` y captura de 12 evidencias visuales nativas.

---

## 8. Resultados Obtenidos y Explicación de Evidencias

Las 12 evidencias visuales capturadas demuestran el cumplimiento riguroso de cada requerimiento funcional:

* **Figura 1 (`figura_01_pantalla_principal.png`):** Muestra el panel principal de SmartHome Hub con la cabecera en modo oscuro y la cuadrícula de controles domóticos.
* **Figura 2 (`figura_02_acceso_encuestas.png`):** Evidencia el noveno módulo "Encuestas y opiniones" en la cuadrícula, reflejando el contador dinámico "9 Módulos Activos".
* **Figura 3 (`figura_03_listado_vacio.png`):** Presenta el estado inicial de la pantalla de encuestas con la tarjeta informativa de equivalencia y el mensaje ilustrado "Aún no tienes encuestas registradas".
* **Figura 4 (`figura_04_formulario_modal.png`):** Ilustra la apertura del formulario modal `SurveyModalDialog` sobre la capa de oscurecimiento (scrim), con el botón de cierre en la esquina superior derecha.
* **Figura 5 (`figura_05_datepicker.png`):** Muestra el selector modal de fecha de Material 3 (`DatePickerDialog`) desplegado para elegir el día de la encuesta.
* **Figura 6 (`figura_06_selector_dispositivo.png`):** Demuestra el ActionSheet modal anclado en la parte inferior (`DeviceSelectionSheet`) con la lista de los 7 dispositivos domóticos y sus iconos.
* **Figura 7 (`figura_07_calificacion.png`):** Muestra la interacción con el control de calificación, donde las 5 estrellas se iluminan en color dorado con el texto descriptivo correspondiente.
* **Figura 8 (`figura_08_validaciones.png`):** Valida la robustez del sistema al mostrar mensajes de error en color rojo cuando el usuario intenta enviar campos vacíos.
* **Figura 9 (`figura_09_formulario_completo.png`):** Exhibe el formulario con todos los campos válidos (nombre "Ana Martínez", dispositivo seleccionado, calificación y comentarios) con el teclado virtual oculto.
* **Figura 10 (`figura_10_confirmacion.png`):** Muestra el diálogo modal de confirmación (`AlertDialog`) con un icono verde de verificación y el resumen de la información guardada.
* **Figura 11 (`figura_11_encuesta_registrada.png`):** Evidencia la actualización reactiva instantánea del listado principal, donde aparece la tarjeta de la nueva encuesta con el badge "REGISTRADA".
* **Figura 12 (`figura_12_navegacion.png`):** Comprueba el retorno fluido al menú principal de SmartHome Hub mediante la flecha de la barra superior unificada.

---

## 9. Conclusiones
La realización de la Práctica 3 permitió consolidar el dominio de la arquitectura declarativa de **Compose Multiplatform** para la construcción de interfaces modales transaccionales. Se demostró que la separación de responsabilidades y el patrón de elevación de estado (*State Hoisting*) ofrecen una solución sustancialmente más limpia, mantenible y desacoplada que el uso de buses de mensajería globales como `MessagingCenter` en Xamarin.Forms.

Asimismo, la implementación de hojas de selección inferiores y calendarios modales conforme a los lineamientos de **Material Design 3** dota a la aplicación de una experiencia de usuario moderna, consistente y completamente operativa tanto en dispositivos móviles Android como en estaciones de escritorio.

---

## 10. Referencias Bibliográficas
1. JetBrains. (2024). *Compose Multiplatform documentation*. https://www.jetbrains.com/lp/compose-multiplatform/
2. Google Android Developers. (2024). *Material Design 3 in Jetpack Compose*. https://developer.android.com/jetpack/compose/designsystems/material3
3. Microsoft Learn. (2023). *Xamarin.Forms Modal Pages and DisplayActionSheet*. https://learn.microsoft.com/en-us/xamarin/xamarin-forms/user-interface/navigation/modal
4. Pérez Pérez, D. (2024). *Cuaderno de Trabajo: Programación Avanzada de Dispositivos Móviles*. Instituto Tecnológico de Veracruz.
