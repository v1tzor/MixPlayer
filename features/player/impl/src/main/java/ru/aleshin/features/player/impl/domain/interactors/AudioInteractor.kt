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
import ru.aleshin.core.common.functional.audio.AudioPlayListType
import ru.aleshin.features.home.api.domain.entities.AudioPlayList
import ru.aleshin.features.home.api.domain.repositories.AppAudioRepository
import ru.aleshin.features.home.api.domain.repositories.SystemAudioRepository
import ru.aleshin.features.player.impl.domain.common.PlayerEitherWrapper
import ru.aleshin.features.player.impl.domain.entities.PlayerFailures
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
internal interface AudioInteractor {

    suspend fun fetchPlaylist(type: AudioPlayListType): DomainResult<PlayerFailures, AudioPlayList?>

    class Base @Inject constructor(
        private val systemAudioRepository: SystemAudioRepository,
        private val appAudioRepository: AppAudioRepository,
        private val eitherWrapper: PlayerEitherWrapper,
    ) : AudioInteractor {

        override suspend fun fetchPlaylist(type: AudioPlayListType) = eitherWrapper.wrap {
            return@wrap when (type) {
                AudioPlayListType.SYSTEM -> systemAudioRepository.fetchPlaylist()
                AudioPlayListType.APP -> appAudioRepository.fetchPlaylist()
                AudioPlayListType.OTHER -> null
            }
        }
    }
}
