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

package ru.aleshin.core.common.functional

import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListType
import ru.aleshin.core.common.functional.audio.AudioPlayListUi

/**
 * @author Stanislav Aleshin on 14.07.2023.
 */
sealed class MediaCommand {
    object PlayOrPause : MediaCommand()
    data class SeekTo(val value: Int) : MediaCommand()
    data class ChangeVolume(val value: Float) : MediaCommand()
    data class SelectAudio(val audio: AudioInfoUi, val type: AudioPlayListType) : MediaCommand()
}