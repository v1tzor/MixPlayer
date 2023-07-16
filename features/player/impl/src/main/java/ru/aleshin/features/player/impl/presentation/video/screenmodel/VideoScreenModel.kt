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

package ru.aleshin.features.player.impl.presentation.video.screenmodel

import android.util.Log
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.platform.screenmodel.BaseScreenModel
import ru.aleshin.core.common.platform.screenmodel.work.WorkScope
import ru.aleshin.features.player.impl.navigation.PlayerNavigationManager
import ru.aleshin.features.player.impl.di.holder.PlayerComponentHolder
import ru.aleshin.features.player.impl.presentation.video.contract.VideoAction
import ru.aleshin.features.player.impl.presentation.video.contract.VideoEffect
import ru.aleshin.features.player.impl.presentation.video.contract.VideoEvent
import ru.aleshin.features.player.impl.presentation.video.contract.VideoViewState
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.06.2023
 */
internal class VideoScreenModel @Inject constructor(
    private val navigationManager: PlayerNavigationManager,
    private val videoWorkProcessor: VideoWorkProcessor,
    stateCommunicator: VideoStateCommunicator,
    effectCommunicator: VideoEffectCommunicator,
    coroutineManager: CoroutineManager,
) : BaseScreenModel<VideoViewState, VideoEvent, VideoAction, VideoEffect>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager,
) {

    override fun init() {
        if (!isInitialize.get()) {
            super.init()
            dispatchEvent(VideoEvent.Init)
        }
    }

    override suspend fun WorkScope<VideoViewState, VideoAction, VideoEffect>.handleEvent(
        event: VideoEvent,
    ) {
        when (event) {
            is VideoEvent.Init -> launchBackgroundWork(VideoWorkCommand.LoadVideo) {
                videoWorkProcessor.work(VideoWorkCommand.LoadVideo).collectAndHandleWork()
            }
            is VideoEvent.PressOrientationButton -> {
                sendAction(VideoAction.UpdateOrientation(!state().isPortraitOrientation))
            }
            is VideoEvent.PressBackButton -> navigationManager.navigateToBack()
        }
    }

    override suspend fun reduce(
        action: VideoAction,
        currentState: VideoViewState,
    ) = when (action) {
        is VideoAction.Navigate -> currentState.copy()
        is VideoAction.UpdateVideo -> currentState.copy(
            videoInfo = action.videoInfo,
        )
        is VideoAction.UpdateOrientation -> currentState.copy(
            isPortraitOrientation = action.isPortrait,
        )
    }

    override fun onDispose() {
        Log.d("test", "dispose")
        PlayerComponentHolder.clear()
        super.onDispose()
    }
}

@Composable
internal fun Screen.rememberVideoScreenModel(): VideoScreenModel {
    val component = PlayerComponentHolder.fetchComponent()
    return rememberScreenModel { component.fetchVideoScreenModel() }
}
