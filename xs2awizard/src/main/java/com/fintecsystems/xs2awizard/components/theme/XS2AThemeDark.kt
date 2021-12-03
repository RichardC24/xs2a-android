package com.fintecsystems.xs2awizard.components.theme

import androidx.compose.ui.graphics.Color

object XS2AThemeDark : IXS2ATheme by XS2AThemeLight {
    override val textColor: Color = XS2AColors.textColorLight
    override val logoVariation: LogoVariation = LogoVariation.WHITE

    override val inputTextColor: Color = XS2AColors.textColorLight

    override val webViewIconColor: Color = XS2AColors.textColorLight
    override val webViewBackgroundColor: Color = Color.Black
    override val webViewTextColor: Color = XS2AColors.textColorLight
}