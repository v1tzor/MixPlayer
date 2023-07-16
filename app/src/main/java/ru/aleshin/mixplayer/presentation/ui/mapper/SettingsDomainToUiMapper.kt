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

package ru.aleshin.mixplayer.presentation.ui.mapper

import ru.aleshin.core.ui.theme.material.ThemeUiType
import ru.aleshin.core.ui.theme.tokens.LanguageUiType
import ru.aleshin.features.settings.api.domain.entities.GeneralSettings
import ru.aleshin.features.settings.api.domain.entities.LanguageType
import ru.aleshin.features.settings.api.domain.entities.ThemeType
import ru.aleshin.mixplayer.presentation.ui.models.GeneralSettingsUi

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
fun GeneralSettings.mapToUi() = GeneralSettingsUi(
    languageUiType = when (languageType) {
        LanguageType.DEFAULT -> LanguageUiType.DEFAULT
        LanguageType.EN -> LanguageUiType.EN
        LanguageType.RU -> LanguageUiType.RU
    },
    themeUiType = when (themeType) {
        ThemeType.DEFAULT -> ThemeUiType.DEFAULT
        ThemeType.LIGHT -> ThemeUiType.LIGHT
        ThemeType.DARK -> ThemeUiType.DARK
    },
)
