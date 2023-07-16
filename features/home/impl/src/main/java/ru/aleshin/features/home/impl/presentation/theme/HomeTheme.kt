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
 * imitations under the License.
 */

package ru.aleshin.features.home.impl.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.aleshin.core.ui.theme.MixPlayerRes
import ru.aleshin.core.ui.theme.tokens.MixPlayerLanguage
import ru.aleshin.features.home.impl.presentation.theme.tokens.HomeIcons
import ru.aleshin.features.home.impl.presentation.theme.tokens.HomeStrings
import ru.aleshin.features.home.impl.presentation.theme.tokens.LocalHomeIcons
import ru.aleshin.features.home.impl.presentation.theme.tokens.LocalHomeStrings

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
@Composable
internal fun HomeTheme(content: @Composable () -> Unit) {
    val icons = HomeIcons.DEFAULT
    val strings = when (MixPlayerRes.language) {
        MixPlayerLanguage.EN -> HomeStrings.ENGLISH
        MixPlayerLanguage.RU -> HomeStrings.RUSSIAN
    }

    CompositionLocalProvider(
        LocalHomeIcons provides icons,
        LocalHomeStrings provides strings,
        content = content,
    )
    HomeSystemUi()
}

@Composable
private fun HomeSystemUi() {
    val systemUiController = rememberSystemUiController()
    val navBarColor = MaterialTheme.colorScheme.background
    val statusBarColor = MaterialTheme.colorScheme.background
    val isDarkIcons = MixPlayerRes.colorsType.isDark

    SideEffect {
        systemUiController.setNavigationBarColor(color = navBarColor, darkIcons = !isDarkIcons)
        systemUiController.setStatusBarColor(color = statusBarColor, darkIcons = !isDarkIcons)
    }
}
