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

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.aleshin.core.common.functional.Constants
import ru.aleshin.core.common.functional.Either
import ru.aleshin.core.common.functional.MediaCommand
import ru.aleshin.core.common.platform.screenmodel.work.ActionResult
import ru.aleshin.core.common.platform.screenmodel.work.EffectResult
import ru.aleshin.core.common.platform.screenmodel.work.FlowWorkProcessor
import ru.aleshin.core.common.platform.screenmodel.work.WorkCommand
import ru.aleshin.core.common.platform.screenmodel.work.WorkResult
import ru.aleshin.features.player.api.presentation.common.MediaController
import ru.aleshin.features.player.api.presentation.common.PlaybackManager
import ru.aleshin.features.player.impl.domain.interactors.AudioInteractor
import ru.aleshin.features.player.impl.domain.interactors.PlayerSettingsInteractor
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioAction
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioEffect
import ru.aleshin.features.player.impl.presentation.mappers.mapToUi
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 11.07.2023.
 */
internal interface AudioWorkProcessor : FlowWorkProcessor<AudioWorkCommand, AudioAction, AudioEffect> {

    class Base @Inject constructor(
        private val playerSettingsInteractor: PlayerSettingsInteractor,
        private val audioInteractor: AudioInteractor,
        private val audioInfoCommunicator: AudioInfoCommunicator,
        private val mediaController: MediaController,
        private val playbackManager: PlaybackManager,
    ) : AudioWorkProcessor {

        override suspend fun work(command: AudioWorkCommand) = when (command) {
            is AudioWorkCommand.SendMediaCommand -> sendMediaCommandWork(command.command)
            is AudioWorkCommand.SaveVolume -> saveVolumeWork(command.volume)
            is AudioWorkCommand.SetUpParameters -> setUpParametersWork()
            is AudioWorkCommand.LoadPlayList -> loadPlayListWork()
            is AudioWorkCommand.ReceivePlayerInfo -> receivePlayerInfoWork()
        }

        private fun receivePlayerInfoWork() = flow {
            playbackManager.collectInfo { playerInfo ->
                emit(ActionResult(AudioAction.UpdatePlayerInfo(playerInfo)))
                if (playerInfo.playback.isComplete) emit(EffectResult(AudioEffect.NextAudio))
            }
        }

        private fun loadPlayListWork() = flow {
            audioInfoCommunicator.collect { type ->
                if (type != null) {
                    when (val result = audioInteractor.fetchPlaylist(type)) {
                        is Either.Right -> emit(ActionResult(AudioAction.UpdatePlayList(result.data?.mapToUi())))
                        is Either.Left -> emit(EffectResult(AudioEffect.ShowError(result.data)))
                    }
                }
            }
        }

        private fun setUpParametersWork() = flow {
            when (val result = playerSettingsInteractor.fetchSettings()) {
                is Either.Right -> emit(ActionResult(AudioAction.UpdateVolume(result.data.volume))).apply {
                    delay(Constants.Delay.SETUP)
                    mediaController.work(MediaCommand.ChangeVolume(result.data.volume))
                }
                is Either.Left -> emit(EffectResult(AudioEffect.ShowError(result.data)))
            }
        }

        private fun saveVolumeWork(volume: Float) = flow {
            val settings = playerSettingsInteractor.fetchSettings().let {
                if (it is Either.Right) it.data
                else return@flow emit(EffectResult(AudioEffect.ShowError((it as Either.Left).data)))
            }
            when (val result = playerSettingsInteractor.updateSettings(settings.copy(volume = volume))) {
                is Either.Right -> Unit
                is Either.Left -> emit(EffectResult(AudioEffect.ShowError(result.data)))
            }
        }

        private fun sendMediaCommandWork(
            command: MediaCommand
        ) = flow<WorkResult<AudioAction, AudioEffect>> {
            mediaController.work(command)
        }
    }
}

internal sealed class AudioWorkCommand : WorkCommand {
    object LoadPlayList : AudioWorkCommand()
    object ReceivePlayerInfo : AudioWorkCommand()
    object SetUpParameters : AudioWorkCommand()
    data class SaveVolume(val volume: Float) : AudioWorkCommand()
    data class SendMediaCommand(val command: MediaCommand) : AudioWorkCommand()
}
