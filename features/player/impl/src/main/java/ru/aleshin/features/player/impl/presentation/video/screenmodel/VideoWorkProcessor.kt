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
import kotlinx.coroutines.flow.flow
import ru.aleshin.core.common.platform.screenmodel.work.ActionResult
import ru.aleshin.core.common.platform.screenmodel.work.FlowWorkProcessor
import ru.aleshin.core.common.platform.screenmodel.work.WorkCommand
import ru.aleshin.features.player.impl.navigation.PlayerNavigationManager
import ru.aleshin.features.player.impl.presentation.video.contract.VideoAction
import ru.aleshin.features.player.impl.presentation.video.contract.VideoEffect
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 05.07.2023.
 */
internal interface VideoWorkProcessor : FlowWorkProcessor<VideoWorkCommand, VideoAction, VideoEffect> {

    class Base @Inject constructor(
        private val videoInfoCommunicator: VideoInfoCommunicator,
    ) : VideoWorkProcessor {

        override suspend fun work(command: VideoWorkCommand) = when (command) {
            is VideoWorkCommand.LoadVideo -> loadVideoWork()
        }

        private fun loadVideoWork() = flow {
            videoInfoCommunicator.collect { videoInfo ->
                if (videoInfo != null) {
                    emit(ActionResult(VideoAction.UpdateVideo(videoInfo)))
                    videoInfoCommunicator.update(null)
                }
            }
        }
    }
}

internal sealed class VideoWorkCommand : WorkCommand {
    object LoadVideo : VideoWorkCommand()
}
