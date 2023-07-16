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

package ru.aleshin.features.player.impl.domain.interactors

import ru.aleshin.core.common.functional.DomainResult
import ru.aleshin.core.common.functional.UnitDomainResult
import ru.aleshin.features.player.impl.domain.common.PlayerEitherWrapper
import ru.aleshin.features.player.impl.domain.entities.PlayerFailures
import ru.aleshin.features.settings.api.domain.entities.PlayerSettings
import ru.aleshin.features.settings.api.domain.repositories.SettingsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.07.2023.
 */
internal interface PlayerSettingsInteractor {

    suspend fun updateSettings(settings: PlayerSettings): UnitDomainResult<PlayerFailures>
    suspend fun fetchSettings(): DomainResult<PlayerFailures, PlayerSettings>

    class Base @Inject constructor(
        private val settingsRepository: SettingsRepository,
        private val eitherWrapper: PlayerEitherWrapper,
    ) : PlayerSettingsInteractor {

        override suspend fun updateSettings(settings: PlayerSettings) = eitherWrapper.wrap {
            val appSettings = settingsRepository.fetchSettings()
            settingsRepository.updateSettings(model = appSettings.copy(player = settings))
        }

        override suspend fun fetchSettings() = eitherWrapper.wrap {
            settingsRepository.fetchSettings().player
        }
    }
}