package com.amarchaud.templateresume.data

import android.content.Context
import androidx.annotation.RawRes
import kotlinx.serialization.json.Json

private val resumeJson = Json { ignoreUnknownKeys = true }

/** Parses the content of a resume JSON file. */
fun parseResume(content: String): ResumeModel = resumeJson.decodeFromString(content)

/** Reads and parses a resume JSON file stored in `res/raw`. */
fun Context.loadResume(@RawRes resId: Int): ResumeModel =
    resources.openRawResource(resId).bufferedReader().use { parseResume(it.readText()) }
