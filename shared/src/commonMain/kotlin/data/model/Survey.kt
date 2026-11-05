package data.model

/**
 * Modelo de datos inmutable que representa una respuesta de encuesta registrada.
 */
data class Survey(
    val id: String,
    val name: String,
    val dateMillis: Long,
    val formattedDate: String,
    val favoriteDevice: String,
    val rating: Int,
    val comments: String,
    val createdAt: Long
)
