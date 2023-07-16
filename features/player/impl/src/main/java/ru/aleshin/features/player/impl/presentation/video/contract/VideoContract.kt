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

package ru.aleshin.features.player.impl.presentation.video.contract

import kotlinx.parcelize.Parcelize
import ru.aleshin.core.common.functional.video.VideoInfoUi
import ru.aleshin.core.common.platform.screenmodel.contract.*
import ru.aleshin.features.player.impl.domain.entities.PlayerFailures

/**
 * @author Stanislav Aleshin on 15.06.2023
 */
@Parcelize
internal data class VideoViewState(
    val videoInfo: VideoInfoUi? = null,
    val isPortraitOrientation: Boolean = true,
) : BaseViewState

internal sealed class VideoEvent : BaseEvent {
    object Init : VideoEvent()
    object PressBackButton : VideoEvent()
    object PressOrientationButton : VideoEvent()
}

internal sealed class VideoEffect : BaseUiEffect {
    data class ShowError(val failures: PlayerFailures) : VideoEffect()
}

internal sealed class VideoAction : BaseAction {
    object Navigate : VideoAction()
    data class UpdateVideo(val videoInfo: VideoInfoUi?) : VideoAction()
    data class UpdateOrientation(val isPortrait: Boolean) : VideoAction()
}
