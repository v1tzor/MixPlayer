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

package ru.aleshin.features.player.impl.presentation.audio.contract

import kotlinx.parcelize.Parcelize
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.core.common.functional.audio.PlayerInfo
import ru.aleshin.core.common.platform.screenmodel.contract.*
import ru.aleshin.features.player.impl.domain.entities.PlayerFailures

/**
 * @author Stanislav Aleshin on 11.07.2023
 */
@Parcelize
internal data class AudioViewState(
    val isPlaying: Boolean = false,
    val currentAudio: AudioInfoUi? = null,
    val playList: AudioPlayListUi? = null,
    val lostTime: Long = 0L,
    val nextTime: Long = 0L,
    val volume: Float = 1f,
) : BaseViewState

internal sealed class AudioEvent : BaseEvent {
    object Init : AudioEvent()
    object PressBackButton : AudioEvent()
    object PressNextButton : AudioEvent()
    object PressPreviousButton : AudioEvent()
    object PressPlayOrPauseButton : AudioEvent()
    data class ChangeVolume(val value: Float) : AudioEvent()
    data class UpdatePosition(val value: Float) : AudioEvent()
}

internal sealed class AudioEffect : BaseUiEffect {
    data class ShowError(val failures: PlayerFailures) : AudioEffect()
    object NextAudio : AudioEffect()
}

internal sealed class AudioAction : BaseAction {
    data class UpdateVolume(val value: Float) : AudioAction()
    data class UpdatePlayerInfo(val playerInfo: PlayerInfo) : AudioAction()
    data class UpdatePlayList(val playlist: AudioPlayListUi?) : AudioAction()
    object Navigate : AudioAction()
}
