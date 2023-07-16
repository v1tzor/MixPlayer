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

package ru.aleshin.features.home.impl.presentation.home.screenmodel

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.platform.screenmodel.BaseScreenModel
import ru.aleshin.core.common.platform.screenmodel.work.WorkScope
import ru.aleshin.features.home.impl.di.holder.HomeComponentHolder
import ru.aleshin.features.home.impl.navigation.HomeNavigationManager
import ru.aleshin.features.home.impl.presentation.home.contract.HomeAction
import ru.aleshin.features.home.impl.presentation.home.contract.HomeEffect
import ru.aleshin.features.home.impl.presentation.home.contract.HomeEvent
import ru.aleshin.features.home.impl.presentation.home.contract.HomeViewState
import ru.aleshin.features.player.api.navigation.PlayerScreens
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 09.07.2023
 */
internal class HomeScreenModel @Inject constructor(
    private val homeWorkProcessor: HomeWorkProcessor,
    private val navigationManager: HomeNavigationManager,
    stateCommunicator: HomeStateCommunicator,
    effectCommunicator: HomeEffectCommunicator,
    coroutineManager: CoroutineManager,
) : BaseScreenModel<HomeViewState, HomeEvent, HomeAction, HomeEffect>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager,
) {

    override fun init() {
        if (!isInitialize.get()) {
            super.init()
            dispatchEvent(HomeEvent.Init)
        }
    }

    override suspend fun WorkScope<HomeViewState, HomeAction, HomeEffect>.handleEvent(
        event: HomeEvent,
    ) {
        when (event) {
            is HomeEvent.Init, HomeEvent.Refresh -> {
                sendAction(HomeAction.UpdateLoading(true))
                homeWorkProcessor.work(HomeWorkCommand.LoadTracks).handleWork()
            }
            is HomeEvent.SearchRequest -> {
                val playLists = state().playLists
                val videos = state().videos
                homeWorkProcessor.work(HomeWorkCommand.SearchRequest(playLists, videos, event.request)).handleWork()
            }
            is HomeEvent.PressMoreButton -> {
                homeWorkProcessor.work(HomeWorkCommand.OpenDetails(event.playlistType, state().playLists)).handleWork()
            }
            is HomeEvent.PressAudioItem -> {
                homeWorkProcessor.work(HomeWorkCommand.OpenAudio(event.item, event.playlist.listType)).handleWork()
            }
            is HomeEvent.PressVideoItem -> {
                homeWorkProcessor.work(HomeWorkCommand.OpenVideo(event.item)).handleWork()
            }
            is HomeEvent.PressSettingsButton -> navigationManager.navigateToSettings()
        }
    }

    override suspend fun reduce(
        action: HomeAction,
        currentState: HomeViewState,
    ) = when (action) {
        is HomeAction.Navigate -> currentState.copy(
            searchResponse = null,
        )
        is HomeAction.UpdateLoading -> currentState.copy(
            isLoading = action.isLoading,
        )
        is HomeAction.UpdateMedia -> currentState.copy(
            videos = action.videos,
            playLists = action.playLists,
            isLoading = false,
        )
        is HomeAction.UpdateResponse -> currentState.copy(
            searchResponse = action.searchResponse,
        )
    }
}

@Composable
internal fun Screen.rememberHomeScreenModel(): HomeScreenModel {
    val component = HomeComponentHolder.fetchComponent()
    return rememberScreenModel { component.fetchHomeScreenModel() }
}
