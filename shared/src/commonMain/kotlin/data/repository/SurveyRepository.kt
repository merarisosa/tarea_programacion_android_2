package data.repository

import androidx.compose.runtime.mutableStateListOf
import data.model.Survey

/**
 * Repositorio centralizado para gestionar las encuestas de SmartHome Hub.
 * Proporciona un estado observable en memoria mediante mutableStateListOf,
 * garantizando reactividad instantánea con el ciclo de vida de Compose Multiplatform.
 */
object SurveyRepository {
    private val _surveys = mutableStateListOf<Survey>()

    /**
     * Lista observable inmutable de encuestas registradas.
     */
    val surveys: List<Survey> get() = _surveys

    /**
     * Registra una nueva encuesta verificando que no exista duplicado por ID.
     */
    fun addSurvey(survey: Survey): Boolean {
        if (_surveys.any { it.id == survey.id }) {
            return false
        }
        // Insertamos al inicio para que la más reciente aparezca arriba
        _surveys.add(0, survey)
        return true
    }

    /**
     * Elimina una encuesta por su identificador.
     */
    fun deleteSurvey(id: String): Boolean {
        return _surveys.removeAll { it.id == id }
    }

    /**
     * Reinicia el almacén de encuestas (útil para pruebas o demostraciones).
     */
    fun clearAll() {
        _surveys.clear()
    }
}
