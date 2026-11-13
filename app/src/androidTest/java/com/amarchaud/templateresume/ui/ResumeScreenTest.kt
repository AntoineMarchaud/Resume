package com.amarchaud.templateresume.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.amarchaud.templateresume.ui.preview.PreviewData
import com.amarchaud.templateresume.ui.resume.ResumeScreen
import com.amarchaud.templateresume.ui.theme.TemplateResumeTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ResumeScreenTest {

    private fun tab(label: String) = isSelectable() and (hasText(label) or hasContentDescription(label))

    @Test
    fun opensOnAboutAndNavigatesBetweenSections() = runComposeUiTest {
        setContent {
            TemplateResumeTheme {
                ResumeScreen(resume = PreviewData.resume, dynamicColor = null)
            }
        }

        onNodeWithText("About me").assertIsDisplayed()
        onNode(tab("About")).assertIsSelected()

        onNode(tab("Experience")).performClick()
        onNodeWithText("Tap a position to see the details").assertIsDisplayed()
        onNodeWithText("Acme Mobile").assertIsDisplayed()

        onNode(tab("Skills")).performClick()
        onNodeWithText("Technologies and practices I work with").assertIsDisplayed()

        onNode(tab("Contact")).performClick()
        onNodeWithText("Let's work together").assertIsDisplayed()
    }
}
