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

package ru.aleshin.features.home.impl.presentation.mappers

import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.features.home.api.domain.entities.AudioInfo
import ru.aleshin.features.home.api.domain.entities.AudioPlayList

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
internal fun AudioInfo.mapToUi() = AudioInfoUi(
    id = id,
    path = path,
    title = title,
    artist = artist,
    album = album,
    duration = durationInMillis,
    imagePath = imagePath,
    date = date,
)

internal fun AudioPlayList.mapToUi() = AudioPlayListUi(
    listType = type,
    audioList = audioList.map { it.mapToUi() }
)

internal fun AudioInfoUi.mapToDomain() = AudioInfo(
    id = id,
    path = path,
    title = title,
    artist = artist,
    album = album,
    imagePath = imagePath,
    durationInMillis = duration,
    date = date,
)

internal fun AudioPlayListUi.mapToDomain() = AudioPlayList(
    type = listType,
    audioList = audioList.map { it.mapToDomain() }
)
