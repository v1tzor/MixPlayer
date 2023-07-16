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

package ru.aleshin.features.settings.impl.presentation.mappers

import androidx.compose.runtime.Composable
import ru.aleshin.core.ui.theme.material.ThemeUiType
import ru.aleshin.core.ui.theme.tokens.LanguageUiType
import ru.aleshin.features.settings.api.domain.entities.LanguageType
import ru.aleshin.features.settings.api.domain.entities.ThemeType
import ru.aleshin.features.settings.impl.presentation.theme.SettingsThemeRes

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
internal fun LanguageUiType.mapToDomain() = when (this) {
    LanguageUiType.DEFAULT -> LanguageType.DEFAULT
    LanguageUiType.EN -> LanguageType.EN
    LanguageUiType.RU -> LanguageType.RU
}

@Composable
internal fun LanguageUiType.mapToMessage() = when (this) {
    LanguageUiType.DEFAULT -> SettingsThemeRes.strings.defaultTitle
    LanguageUiType.EN -> SettingsThemeRes.strings.englishLanguage
    LanguageUiType.RU -> SettingsThemeRes.strings.russianLanguage
}

internal fun ThemeUiType.mapToDomain() = when (this) {
    ThemeUiType.DEFAULT -> ThemeType.DEFAULT
    ThemeUiType.LIGHT -> ThemeType.LIGHT
    ThemeUiType.DARK -> ThemeType.DARK
}

@Composable
internal fun ThemeUiType.mapToMessage() = when (this) {
    ThemeUiType.DEFAULT -> SettingsThemeRes.strings.defaultTitle
    ThemeUiType.LIGHT -> SettingsThemeRes.strings.lightTheme
    ThemeUiType.DARK -> SettingsThemeRes.strings.darkTheme
}

internal fun LanguageType.mapToUi() = when (this) {
    LanguageType.DEFAULT -> LanguageUiType.DEFAULT
    LanguageType.EN -> LanguageUiType.EN
    LanguageType.RU -> LanguageUiType.RU
}

internal fun ThemeType.mapToUi() = when (this) {
    ThemeType.DEFAULT -> ThemeUiType.DEFAULT
    ThemeType.LIGHT -> ThemeUiType.LIGHT
    ThemeType.DARK -> ThemeUiType.DARK
}