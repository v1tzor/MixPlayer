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
package ru.aleshin.mixplayer.presentation.ui.main.viewmodel

import kotlinx.coroutines.flow.flow
import ru.aleshin.core.common.functional.audio.PlayerInfo
import ru.aleshin.core.common.functional.handle
import ru.aleshin.core.common.platform.screenmodel.work.ActionResult
import ru.aleshin.core.common.platform.screenmodel.work.EffectResult
import ru.aleshin.core.common.platform.screenmodel.work.FlowWorkProcessor
import ru.aleshin.core.common.platform.screenmodel.work.WorkCommand
import ru.aleshin.core.common.platform.screenmodel.work.WorkResult
import ru.aleshin.features.player.api.presentation.common.MediaController
import ru.aleshin.features.player.api.presentation.common.PlaybackManager
import ru.aleshin.mixplayer.presentation.ui.mapper.mapToUi
import ru.aleshin.mixplayer.domain.interactors.SettingsInteractor
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainAction
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainEffect
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
interface MainWorkProcessor : FlowWorkProcessor<MainWorkCommand, MainAction, MainEffect> {

    class Base @Inject constructor(
        private val settingsInteractor: SettingsInteractor,
        private val mediaController: MediaController,
        private val playbackManager: PlaybackManager,
    ) : MainWorkProcessor {

        override suspend fun work(command: MainWorkCommand) = when (command) {
            is MainWorkCommand.LoadGeneralMain -> loadGeneralSettingsWork()
            is MainWorkCommand.ReceiveMediaCommand -> receiveMediaCommandWork()
            is MainWorkCommand.SendPlayerInfo -> sendPlayerInfoWork(command.info)
        }

        private fun loadGeneralSettingsWork() = flow {
            settingsInteractor.fetchThemeSettings().collect { settingsEither ->
                settingsEither.handle(
                    onLeftAction = { error(RuntimeException("Error get GeneralSettings -> $it")) },
                    onRightAction = {
                        val action = MainAction.ChangeGeneralSettings(it.general.mapToUi())
                        emit(ActionResult(action))
                    },
                )
            }
        }

        private fun receiveMediaCommandWork() = flow {
            mediaController.collectCommands { command ->
                emit(EffectResult(MainEffect.WorkMediaCommand(command)))
            }
        }

        private fun sendPlayerInfoWork(info: PlayerInfo) = flow<WorkResult<MainAction, MainEffect>> {
            playbackManager.sendInfo(info)
        }
    }
}

sealed class MainWorkCommand : WorkCommand {
    object LoadGeneralMain : MainWorkCommand()
    object ReceiveMediaCommand : MainWorkCommand()
    data class SendPlayerInfo(val info: PlayerInfo) : MainWorkCommand()
}
