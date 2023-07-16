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

package ru.aleshin.mixplayer.domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.aleshin.core.common.functional.Either
import ru.aleshin.features.settings.api.domain.entities.MixPlayerSettings
import ru.aleshin.features.settings.api.domain.repositories.SettingsRepository
import ru.aleshin.mixplayer.domain.common.MainEitherWrapper
import ru.aleshin.mixplayer.domain.entity.MainFailures
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
interface SettingsInteractor {

    suspend fun fetchThemeSettings(): Flow<Either<MainFailures, MixPlayerSettings>>

    class Base @Inject constructor(
        private val settingsRepository: SettingsRepository,
        private val eitherWrapper: MainEitherWrapper,
    ) : SettingsInteractor {

        override suspend fun fetchThemeSettings() = eitherWrapper.wrapFlow {
            settingsRepository.fetchSettingsFlow()
        }
    }
}
