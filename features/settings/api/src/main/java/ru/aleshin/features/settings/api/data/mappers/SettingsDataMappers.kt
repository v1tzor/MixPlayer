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

package ru.aleshin.features.settings.api.data.mappers

import ru.aleshin.features.settings.api.data.models.MixPlayerSettingsEntity
import ru.aleshin.features.settings.api.domain.entities.MixPlayerSettings
import ru.aleshin.features.settings.api.domain.entities.GeneralSettings
import ru.aleshin.features.settings.api.domain.entities.PlayerSettings

/**
 * @author Stanislav Aleshin on 15.06.2023.
 */
fun MixPlayerSettingsEntity.mapToDomain() = MixPlayerSettings(
    general = GeneralSettings(
        languageType = languageType,
        themeType = themeType,
    ),
    player = PlayerSettings(
        volume = volume,
    )
)

fun MixPlayerSettings.mapToData() = MixPlayerSettingsEntity(
    languageType = general.languageType,
    themeType = general.themeType,
    volume = player.volume,
)
