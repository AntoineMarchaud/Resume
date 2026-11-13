package com.amarchaud.templateresume.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.amarchaud.templateresume.R

/**
 * Instance of the bundled variable font at the given weight. Resource fonts do not derive their
 * variation settings from the weight, so the `wght` axis has to be set explicitly.
 */
private fun plusJakartaSans(weight: FontWeight) = Font(
    resId = R.font.plus_jakarta_sans,
    weight = weight,
    variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
)

/** Plus Jakarta Sans (SIL Open Font License 1.1), bundled as a single variable font file. */
private val PlusJakartaSans = FontFamily(
    plusJakartaSans(FontWeight.Normal),
    plusJakartaSans(FontWeight.Medium),
    plusJakartaSans(FontWeight.SemiBold),
    plusJakartaSans(FontWeight.Bold),
    plusJakartaSans(FontWeight.ExtraBold),
)

private val Default = Typography()

private fun TextStyle.jakarta(
    weight: FontWeight,
    letterSpacing: TextUnit = this.letterSpacing,
    lineHeight: TextUnit = this.lineHeight,
) = copy(
    fontFamily = PlusJakartaSans,
    fontWeight = weight,
    letterSpacing = letterSpacing,
    lineHeight = lineHeight,
)

internal val AppTypography = Typography(
    displayLarge = Default.displayLarge.jakarta(FontWeight.ExtraBold, letterSpacing = (-1).sp),
    displayMedium = Default.displayMedium.jakarta(FontWeight.ExtraBold, letterSpacing = (-0.75).sp),
    displaySmall = Default.displaySmall.jakarta(FontWeight.Bold, letterSpacing = (-0.5).sp),
    headlineLarge = Default.headlineLarge.jakarta(FontWeight.Bold, letterSpacing = (-0.5).sp),
    headlineMedium = Default.headlineMedium.jakarta(FontWeight.Bold, letterSpacing = (-0.4).sp),
    headlineSmall = Default.headlineSmall.jakarta(FontWeight.Bold, letterSpacing = (-0.25).sp),
    titleLarge = Default.titleLarge.jakarta(FontWeight.Bold, letterSpacing = (-0.2).sp),
    titleMedium = Default.titleMedium.jakarta(FontWeight.Bold, letterSpacing = 0.sp),
    titleSmall = Default.titleSmall.jakarta(FontWeight.SemiBold, letterSpacing = 0.sp),
    bodyLarge = Default.bodyLarge.jakarta(FontWeight.Normal, letterSpacing = 0.1.sp, lineHeight = 26.sp),
    bodyMedium = Default.bodyMedium.jakarta(FontWeight.Normal, letterSpacing = 0.1.sp, lineHeight = 22.sp),
    bodySmall = Default.bodySmall.jakarta(FontWeight.Normal, letterSpacing = 0.2.sp),
    labelLarge = Default.labelLarge.jakarta(FontWeight.SemiBold, letterSpacing = 0.1.sp),
    labelMedium = Default.labelMedium.jakarta(FontWeight.SemiBold, letterSpacing = 0.2.sp),
    labelSmall = Default.labelSmall.jakarta(FontWeight.Bold, letterSpacing = 0.6.sp),
)
