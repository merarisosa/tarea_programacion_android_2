package util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun getCurrentEpochMillis(): Long = System.currentTimeMillis()

actual fun formatEpochDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(millis))
}
