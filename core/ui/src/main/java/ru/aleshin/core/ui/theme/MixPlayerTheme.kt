/*
 * Copyright 2023 Stanislav Aleshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package ru.aleshin.core.ui.theme

import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.aleshin.core.ui.theme.material.*
import ru.aleshin.core.ui.theme.tokens.*

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Composable
fun MixPlayerTheme(
    dynamicColor: Boolean = false,
    themeType: ThemeUiType = ThemeUiType.DEFAULT,
    language: LanguageUiType = LanguageUiType.DEFAULT,
    content: @Composable () -> Unit,
) {
    val colorsType = MixPlayerColorsType(themeType.isDarkTheme())
    val appLanguage = when (language) {
        LanguageUiType.DEFAULT -> fetchAppLanguage(Locale.current.language)
        LanguageUiType.EN -> MixPlayerLanguage.EN
        LanguageUiType.RU -> MixPlayerLanguage.RU
    }
    val appStrings = fetchCoreStrings(appLanguage)
    val appElevations = baseMixPlayerElevations
    val appIcons = baseMixPlayerIcons

    MaterialTheme(
        colorScheme = themeType.toColorScheme(dynamicColor),
        shapes = baseShapes,
        typography = baseTypography,
    ) {
        CompositionLocalProvider(
            LocalMixPlayerLanguage provides appLanguage,
            LocalMixPlayerColorsType provides colorsType,
            LocalMixPlayerElevations provides appElevations,
            LocalMixPlayerStrings provides appStrings,
            LocalMixPlayerIcons provides appIcons,
            content = content,
        )
        MixPlayerSystemUi(
            navigationBarColor = colorScheme.background,
            statusBarColor = colorScheme.background,
            isDarkIcons = themeType.isDarkTheme(),
        )
    }
}

@Composable
fun MixPlayerSystemUi(navigationBarColor: Color, statusBarColor: Color, isDarkIcons: Boolean) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = !isDarkIcons,
        )
        systemUiController.setStatusBarColor(color = statusBarColor, darkIcons = !isDarkIcons)
    }
}
