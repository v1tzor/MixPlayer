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

package ru.aleshin.features.home.api.data.repository

import ru.aleshin.features.home.api.data.datasources.SystemAudioLocalDataSource
import ru.aleshin.features.home.api.data.mappers.mapToDomain
import ru.aleshin.core.common.functional.audio.AudioPlayListType
import ru.aleshin.features.home.api.domain.entities.AudioPlayList
import ru.aleshin.features.home.api.domain.repositories.SystemAudioRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
class SystemAudioRepositoryImpl @Inject constructor(
    private val localDataSource: SystemAudioLocalDataSource,
) : SystemAudioRepository {

    override suspend fun fetchPlaylist(): AudioPlayList {
        val audioList = localDataSource.fetchAllSystemTracks().map { audioInfo ->
            audioInfo.mapToDomain()
        }
        return AudioPlayList(type = AudioPlayListType.SYSTEM, audioList = audioList)
    }
}
