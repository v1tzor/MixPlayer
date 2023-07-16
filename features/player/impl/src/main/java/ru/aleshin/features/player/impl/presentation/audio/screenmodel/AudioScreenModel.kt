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

package ru.aleshin.features.player.impl.presentation.audio.screenmodel

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.functional.MediaCommand
import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.platform.screenmodel.BaseScreenModel
import ru.aleshin.core.common.platform.screenmodel.work.WorkScope
import ru.aleshin.features.player.impl.navigation.PlayerNavigationManager
import ru.aleshin.features.player.impl.di.holder.PlayerComponentHolder
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioAction
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioEffect
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioEvent
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioViewState
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 11.07.2023
 */
internal class AudioScreenModel @Inject constructor(
    private val navigationManager: PlayerNavigationManager,
    private val audioWorkProcessor: AudioWorkProcessor,
    stateCommunicator: AudioStateCommunicator,
    effectCommunicator: AudioEffectCommunicator,
    coroutineManager: CoroutineManager,
) : BaseScreenModel<AudioViewState, AudioEvent, AudioAction, AudioEffect>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager,
) {

    override fun init() {
        if (!isInitialize.get()) {
            super.init()
            dispatchEvent(AudioEvent.Init)
        }
    }

    override suspend fun WorkScope<AudioViewState, AudioAction, AudioEffect>.handleEvent(
        event: AudioEvent,
    ) {
        when (event) {
            AudioEvent.Init -> {
                launchBackgroundWork(AudioWorkCommand.LoadPlayList) {
                    audioWorkProcessor.work(AudioWorkCommand.LoadPlayList).collectAndHandleWork()
                }
                launchBackgroundWork(AudioWorkCommand.ReceivePlayerInfo) {
                    audioWorkProcessor.work(AudioWorkCommand.ReceivePlayerInfo).collectAndHandleWork()
                }
                audioWorkProcessor.work(AudioWorkCommand.SetUpParameters).collectAndHandleWork()
            }
            is AudioEvent.ChangeVolume -> {
                AudioWorkCommand.SaveVolume(state().volume).apply {
                    audioWorkProcessor.work(this).collectAndHandleWork()
                }
                AudioWorkCommand.SendMediaCommand(MediaCommand.ChangeVolume(event.value)).apply {
                    audioWorkProcessor.work(this).collectAndHandleWork()
                }
            }
            is AudioEvent.PressNextButton -> {
                val playList = state().playList
                if (playList != null) {
                    val currentAudio = state().currentAudio
                    val audioIndex = playList.audioList.indexOfFirst { it.id == currentAudio?.id }
                    val audio = playList.audioList.getOrNull(audioIndex + 1) ?: return
                    val command = AudioWorkCommand.SendMediaCommand(MediaCommand.SelectAudio(audio, playList.listType))
                    audioWorkProcessor.work(command).collectAndHandleWork()
                }
            }
            is AudioEvent.PressPreviousButton -> {
                val playList = state().playList
                if (playList != null) {
                    val currentAudio = state().currentAudio
                    val audioIndex = playList.audioList.indexOfFirst { it.id == currentAudio?.id }
                    val audio = playList.audioList.getOrNull(audioIndex - 1) ?: return
                    val command = AudioWorkCommand.SendMediaCommand(MediaCommand.SelectAudio(audio, playList.listType))
                    audioWorkProcessor.work(command).collectAndHandleWork()
                }
            }
            is AudioEvent.PressPlayOrPauseButton -> {
                val command = AudioWorkCommand.SendMediaCommand(MediaCommand.PlayOrPause)
                audioWorkProcessor.work(command).collectAndHandleWork()
            }
            is AudioEvent.UpdatePosition -> {
                val position = event.value * checkNotNull(state().currentAudio?.duration)
                val command = AudioWorkCommand.SendMediaCommand(MediaCommand.SeekTo(position.toInt()))
                audioWorkProcessor.work(command).collectAndHandleWork()
            }
            is AudioEvent.PressBackButton -> navigationManager.navigateToBack()
        }
    }

    override suspend fun reduce(
        action: AudioAction,
        currentState: AudioViewState,
    ) = when (action) {
        is AudioAction.Navigate -> currentState
        is AudioAction.UpdatePlayerInfo -> currentState.copy(
            currentAudio = action.playerInfo.playback.currentAudio,
            isPlaying = action.playerInfo.playback.isPlay,
            lostTime = action.playerInfo.playback.position.toLong(),
            nextTime = action.playerInfo.playback.currentAudio?.duration?.minus(action.playerInfo.playback.position) ?: 0L,
            volume = action.playerInfo.playback.volume,
        )
        is AudioAction.UpdatePlayList -> currentState.copy(
            playList = action.playlist,
        )
        is AudioAction.UpdateVolume -> currentState.copy(
            volume = action.value,
        )

    }

    override fun onDispose() {
        super.onDispose()
        PlayerComponentHolder.clear()
    }
}

@Composable
internal fun Screen.rememberAudioScreenModel(): AudioScreenModel {
    val component = PlayerComponentHolder.fetchComponent()
    return rememberScreenModel { component.fetchAudioScreenModel() }
}
