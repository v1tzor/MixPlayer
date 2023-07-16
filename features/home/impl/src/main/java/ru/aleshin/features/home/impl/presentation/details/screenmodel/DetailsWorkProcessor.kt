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

package ru.aleshin.features.home.impl.presentation.details.screenmodel

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.aleshin.core.common.functional.Constants
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.core.common.platform.screenmodel.work.ActionResult
import ru.aleshin.core.common.platform.screenmodel.work.FlowWorkProcessor
import ru.aleshin.core.common.platform.screenmodel.work.WorkCommand
import ru.aleshin.core.common.platform.screenmodel.work.WorkResult
import ru.aleshin.features.home.impl.navigation.HomeNavigationManager
import ru.aleshin.features.home.impl.presentation.details.contract.DetailsAction
import ru.aleshin.features.home.impl.presentation.details.contract.DetailsEffect
import ru.aleshin.features.player.api.navigation.PlayerScreens
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
internal interface DetailsWorkProcessor :
    FlowWorkProcessor<DetailsWorkCommand, DetailsAction, DetailsEffect> {

    class Base @Inject constructor(
        private val detailsInfoCommunicator: DetailsInfoCommunicator,
        private val navigationManager: HomeNavigationManager,
    ) : DetailsWorkProcessor {

        override suspend fun work(command: DetailsWorkCommand) = when (command) {
            is DetailsWorkCommand.LoadPlaylist -> loadPlaylistWork()
            is DetailsWorkCommand.PlayAudio -> playAudioWork(command.audio, command.playlist)
            is DetailsWorkCommand.NavigateToBack -> navigateToBackWork()
        }


        private fun playAudioWork(
            audio: AudioInfoUi,
            playlists: AudioPlayListUi
        ) = flow<WorkResult<DetailsAction, DetailsEffect>> {
            navigationManager.navigateToPlayer(PlayerScreens.Audio(audio, playlists.listType))
        }

        private suspend fun loadPlaylistWork() = flow {
            emit(ActionResult(DetailsAction.UpdateLoading(true)))
            detailsInfoCommunicator.collect { details ->
                if (details != null) {
                    delay(Constants.Delay.LOAD_ANIMATION)
                    emit(ActionResult(DetailsAction.UpdatePlaylist(details)))
                    emit(ActionResult(DetailsAction.UpdateLoading(false)))
                    detailsInfoCommunicator.update(null)
                }
            }
        }

        private fun navigateToBackWork() = flow {
            emit(ActionResult(DetailsAction.Navigation))
            navigationManager.navigateToLocalBack()
        }
    }
}

internal sealed class DetailsWorkCommand : WorkCommand {
    object LoadPlaylist : DetailsWorkCommand()
    object NavigateToBack : DetailsWorkCommand()
    data class PlayAudio(val audio: AudioInfoUi, val playlist: AudioPlayListUi) :
        DetailsWorkCommand()
}
