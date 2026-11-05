package data

import data.model.Survey
import data.repository.SurveyRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SurveyRepositoryTest {

    @BeforeTest
    fun setUp() {
        SurveyRepository.clearAll()
    }

    @Test
    fun testEmptyInitialState() {
        assertEquals(0, SurveyRepository.surveys.size)
    }

    @Test
    fun testAddSurveySuccessfully() {
        val survey = Survey(
            id = "SRV-100",
            name = "Carlos Fuentes",
            dateMillis = 1730822400000L,
            formattedDate = "05/11/2026",
            favoriteDevice = "Iluminación inteligente",
            rating = 5,
            comments = "Excelente automatización de luces",
            createdAt = 1730822400000L
        )

        val added = SurveyRepository.addSurvey(survey)
        assertTrue(added)
        assertEquals(1, SurveyRepository.surveys.size)
        assertEquals("Carlos Fuentes", SurveyRepository.surveys[0].name)
        assertEquals(5, SurveyRepository.surveys[0].rating)
    }

    @Test
    fun testDuplicatePrevention() {
        val survey1 = Survey(
            id = "SRV-SAME",
            name = "Prueba 1",
            dateMillis = 1730822400000L,
            formattedDate = "05/11/2026",
            favoriteDevice = "Cámaras de seguridad",
            rating = 4,
            comments = "",
            createdAt = 1730822400000L
        )
        val survey2 = Survey(
            id = "SRV-SAME",
            name = "Prueba 2 Duplicada",
            dateMillis = 1730822400000L,
            formattedDate = "05/11/2026",
            favoriteDevice = "Enchufes inteligentes",
            rating = 3,
            comments = "",
            createdAt = 1730822400000L
        )

        assertTrue(SurveyRepository.addSurvey(survey1))
        assertFalse(SurveyRepository.addSurvey(survey2), "No debe permitir encuestas con ID duplicado")
        assertEquals(1, SurveyRepository.surveys.size)
    }

    @Test
    fun testDeleteSurvey() {
        val survey = Survey(
            id = "SRV-DEL",
            name = "Mariana Ramos",
            dateMillis = 1730822400000L,
            formattedDate = "05/11/2026",
            favoriteDevice = "Aire acondicionado",
            rating = 5,
            comments = "Funciona perfecto",
            createdAt = 1730822400000L
        )

        SurveyRepository.addSurvey(survey)
        assertEquals(1, SurveyRepository.surveys.size)

        val deleted = SurveyRepository.deleteSurvey("SRV-DEL")
        assertTrue(deleted)
        assertEquals(0, SurveyRepository.surveys.size)
    }

    @Test
    fun testRecentSurveysAppearFirst() {
        val survey1 = Survey(
            id = "SRV-1",
            name = "Primero",
            dateMillis = 1000L,
            formattedDate = "01/11/2026",
            favoriteDevice = "Otro",
            rating = 3,
            comments = "",
            createdAt = 1000L
        )
        val survey2 = Survey(
            id = "SRV-2",
            name = "Segundo",
            dateMillis = 2000L,
            formattedDate = "02/11/2026",
            favoriteDevice = "Cerraduras inteligentes",
            rating = 5,
            comments = "",
            createdAt = 2000L
        )

        SurveyRepository.addSurvey(survey1)
        SurveyRepository.addSurvey(survey2)

        assertEquals("Segundo", SurveyRepository.surveys[0].name)
        assertEquals("Primero", SurveyRepository.surveys[1].name)
    }
}
