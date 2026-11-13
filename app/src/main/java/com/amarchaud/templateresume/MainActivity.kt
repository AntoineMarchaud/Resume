package com.amarchaud.templateresume

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.amarchaud.templateresume.data.loadResume
import com.amarchaud.templateresume.ui.resume.DynamicColorAction
import com.amarchaud.templateresume.ui.resume.ResumeScreen
import com.amarchaud.templateresume.ui.theme.TemplateResumeTheme
import com.amarchaud.templateresume.ui.theme.isDynamicColorAvailable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val resume = loadResume(R.raw.resume_template)

        setContent {
            var dynamicColor by rememberSaveable { mutableStateOf(false) }
            TemplateResumeTheme(dynamicColor = dynamicColor) {
                ResumeScreen(
                    resume = resume,
                    dynamicColor = if (isDynamicColorAvailable) {
                        DynamicColorAction(enabled = dynamicColor, onToggle = { dynamicColor = it })
                    } else {
                        null
                    },
                )
            }
        }
    }
}
