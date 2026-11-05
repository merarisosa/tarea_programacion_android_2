package util

/**
 * Utilidades de plataforma multiplataforma para fecha y hora.
 */
expect fun getCurrentEpochMillis(): Long

expect fun formatEpochDate(millis: Long): String
