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

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.platform.screenmodel.BaseScreenModel
import ru.aleshin.core.common.platform.screenmodel.work.WorkScope
import ru.aleshin.features.home.impl.di.holder.HomeComponentHolder
import ru.aleshin.features.home.impl.presentation.details.contract.DetailsAction
import ru.aleshin.features.home.impl.presentation.details.contract.DetailsEffect
import ru.aleshin.features.home.impl.presentation.details.contract.DetailsEvent
import ru.aleshin.features.home.impl.presentation.details.contract.DetailsViewState
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.07.2023
 */
internal class DetailsScreenModel @Inject constructor(
    private val detailsWorkProcessor: DetailsWorkProcessor,
    stateCommunicator: DetailsStateCommunicator,
    effectCommunicator: DetailsEffectCommunicator,
    coroutineManager: CoroutineManager,
) : BaseScreenModel<DetailsViewState, DetailsEvent, DetailsAction, DetailsEffect>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager,
) {

    override fun init() {
        if (!isInitialize.get()) {
            super.init()
            dispatchEvent(DetailsEvent.Init)
        }
    }

    override suspend fun WorkScope<DetailsViewState, DetailsAction, DetailsEffect>.handleEvent(
        event: DetailsEvent,
    ) {
        when (event) {
            is DetailsEvent.Init -> launchBackgroundWork(DetailsWorkCommand.LoadPlaylist) {
                val command = DetailsWorkCommand.LoadPlaylist
                detailsWorkProcessor.work(command).collectAndHandleWork()
            }
            is DetailsEvent.PressAudioItem -> {
                val playList = checkNotNull(state().playList)
                val command = DetailsWorkCommand.PlayAudio(event.track, playList)
                detailsWorkProcessor.work(command).collectAndHandleWork()
            }
            is DetailsEvent.PressBackButton -> {
                detailsWorkProcessor.work(DetailsWorkCommand.NavigateToBack).collectAndHandleWork()
            }
        }
    }

    override suspend fun reduce(
        action: DetailsAction,
        currentState: DetailsViewState,
    ) = when (action) {
        is DetailsAction.Navigation -> currentState.copy(
            playList = null,
        )
        is DetailsAction.UpdateLoading -> currentState.copy(
            isLoading = action.isLoading
        )
        is DetailsAction.UpdatePlayList -> currentState.copy(
            isLoading = false,
            playList = action.playList,
        )
    }
}

@Composable
internal fun Screen.rememberDetailsScreenModel(): DetailsScreenModel {
    val component = HomeComponentHolder.fetchComponent()
    return rememberScreenModel { component.fetchDetailsScreenModel() }
}
