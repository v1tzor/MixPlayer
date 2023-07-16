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

package ru.aleshin.features.home.impl.domain.interactors

import ru.aleshin.core.common.functional.DomainResult
import ru.aleshin.features.home.impl.domain.common.HomeEitherWrapper
import ru.aleshin.features.home.impl.domain.entities.HomeFailures
import ru.aleshin.features.home.api.domain.entities.AudioPlayList
import ru.aleshin.features.home.api.domain.repositories.AppAudioRepository
import ru.aleshin.features.home.api.domain.repositories.SystemAudioRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
internal interface AudioInteractor {

    suspend fun fetchPlaylists(): DomainResult<HomeFailures, List<AudioPlayList>>

    class Base @Inject constructor(
        private val systemAudioRepository: SystemAudioRepository,
        private val appAudioRepository: AppAudioRepository,
        private val eitherWrapper: HomeEitherWrapper,
    ) : AudioInteractor {

        override suspend fun fetchPlaylists() = eitherWrapper.wrap {
            mutableListOf<AudioPlayList>().apply {
                add(systemAudioRepository.fetchPlaylist())
                add(appAudioRepository.fetchPlaylist())
            }
        }
    }
}
