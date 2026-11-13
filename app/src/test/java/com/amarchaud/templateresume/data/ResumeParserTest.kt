package com.amarchaud.templateresume.data

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ResumeParserTest {

    /** The resume bundled with the app must always be valid. */
    @Test
    fun `bundled resume is valid`() {
        val json = SystemFileSystem.source(Path("src/main/res/raw/resume_template.json")).buffered().use { it.readString() }
        val resume = parseResume(json)

        assertTrue(resume.info.name.isNotBlank())
        assertTrue(resume.experiences.isNotEmpty())
        assertTrue(resume.skills.isNotEmpty())
        resume.experiences.forEach { experience ->
            assertTrue(
                experience.startMonth != null || experience.period != null,
                "${experience.company} needs a valid start month or a period",
            )
            if (experience.end != null) assertNotNull(experience.endMonth, "${experience.company} has an invalid end")
        }
        assertEquals(1, resume.experiences.count { it.isCurrent }, "Exactly one position should be ongoing")
    }

    /** JSON files written for the first version of the template keep working. */
    @Test
    fun `legacy resume format is still supported`() {
        val legacy = """
            {
              "info": { "name": "Jane Doe", "job": "Android Developer" },
              "contact": {
                "email": "jane@example.com",
                "phone": "+33 6 00 00 00 00",
                "githubUrl": "https://github.com/jane",
                "linkedInUrl": "https://www.linkedin.com/in/jane"
              },
              "skills": ["Kotlin"],
              "experiences": [
                {
                  "company": "Acme",
                  "period": "Since 2020",
                  "role": "Android Developer",
                  "highlights": ["Did things"],
                  "technologies": ["Compose"],
                  "unknownField": true
                }
              ]
            }
        """.trimIndent()

        val resume = parseResume(legacy)

        assertEquals("Jane Doe", resume.info.name)
        assertNull(resume.about)
        assertTrue(resume.education.isEmpty())
        assertTrue(resume.experiences.single().isCurrent)
        assertEquals("+33 6 00 00 00 00", resume.contact.phone)
    }
}
