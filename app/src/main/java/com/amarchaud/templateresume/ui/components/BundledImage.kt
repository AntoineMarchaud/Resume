package com.amarchaud.templateresume.ui.components

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** State of an image bundled with the app: company logos, profile photo. */
sealed interface BundledImage {
    data object Loading : BundledImage
    data class Loaded(val image: ImageBitmap) : BundledImage
    data object Missing : BundledImage
}

/** Decodes an image of the app assets, off the main thread: [BundledImage.Missing] when there is none or it cannot be read. */
@Composable
fun rememberBundledImage(path: String?): BundledImage {
    val assets = LocalContext.current.assets
    val state by produceState<BundledImage>(
        initialValue = if (path == null) BundledImage.Missing else BundledImage.Loading,
        path,
        assets,
    ) {
        if (path != null) {
            value = withContext(Dispatchers.IO) {
                runCatching { assets.open(path).use { BitmapFactory.decodeStream(it) } }.getOrNull()
            }?.let { BundledImage.Loaded(it.asImageBitmap()) } ?: BundledImage.Missing
        }
    }
    return state
}

/** Profile photo of the resume, loaded once and shared by every avatar of the screen. */
val LocalProfilePhoto = compositionLocalOf<BundledImage> { BundledImage.Missing }
