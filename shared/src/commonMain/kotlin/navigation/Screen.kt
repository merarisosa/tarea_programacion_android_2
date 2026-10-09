package navigation

enum class Screen(
    val title: String,
    val controlName: String,
    val mauiEquivalent: String,
    val description: String,
    val badge: String
) {
    HOME(
        title = "SmartHome Hub",
        controlName = "Dashboard Principal",
        mauiEquivalent = "Grid Layout",
        description = "Centro de control domótico y accesos a dispositivos.",
        badge = "Inicio"
    ),
    DATE_PICKER(
        title = "Modo Vacaciones",
        controlName = "DatePicker",
        mauiEquivalent = "Xamarin/MAUI DatePicker",
        description = "Agenda la fecha de ausencia y bloqueo de seguridad.",
        badge = "Fecha"
    ),
    TIME_PICKER(
        title = "Rutinas de Encendido",
        controlName = "TimePicker",
        mauiEquivalent = "Xamarin/MAUI TimePicker",
        description = "Configura la hora exacta de automatización matutina.",
        badge = "Horario"
    ),
    SLIDER(
        title = "Regulador de Luz",
        controlName = "Slider",
        mauiEquivalent = "Xamarin/MAUI Slider",
        description = "Ajusta la intensidad del brillo y la temperatura del hogar.",
        badge = "Atenuador"
    ),
    IMAGE(
        title = "Cámara Exterior",
        controlName = "Image",
        mauiEquivalent = "Xamarin/MAUI Image (Aspect)",
        description = "Previsualiza captura en vivo con modos de escala y recorte.",
        badge = "Vigilancia"
    ),
    BOX_VIEW(
        title = "Luces RGB y Decoración",
        controlName = "Box (BoxView)",
        mauiEquivalent = "Xamarin/MAUI BoxView",
        description = "Contenedor de color sólido, gradientes, bordes y separadores.",
        badge = "RGB / Box"
    ),
    SWITCH(
        title = "Sistema de Alarma",
        controlName = "Switch",
        mauiEquivalent = "Xamarin/MAUI Switch",
        description = "Activa y desactiva sensores perimetrales y de presencia.",
        badge = "Seguridad"
    ),
    PROGRESS(
        title = "Monitor de Energía",
        controlName = "ProgressIndicator",
        mauiEquivalent = "Xamarin/MAUI ProgressBar / ActivityIndicator",
        description = "Monitorea el consumo eléctrico en tiempo real y la meta.",
        badge = "Consumo"
    )
}
