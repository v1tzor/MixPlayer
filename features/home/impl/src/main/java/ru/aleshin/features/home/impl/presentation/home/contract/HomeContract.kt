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

package ru.aleshin.features.home.impl.presentation.home.contract

import kotlinx.parcelize.Parcelize
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.core.common.platform.screenmodel.contract.*
import ru.aleshin.core.common.functional.audio.AudioPlayListType
import ru.aleshin.core.common.functional.video.VideoInfoUi
import ru.aleshin.features.home.impl.domain.entities.HomeFailures
import ru.aleshin.features.home.impl.presentation.models.MediaSearchResponse

/**
 * @author Stanislav Aleshin on 17.06.2023
 */
@Parcelize
internal data class HomeViewState(
    val isLoading: Boolean = true,
    val playLists: List<AudioPlayListUi> = emptyList(),
    val videos: List<VideoInfoUi> = emptyList(),
    val searchResponse: MediaSearchResponse? = null,
) : BaseViewState

internal sealed class HomeEvent : BaseEvent {
    object Init : HomeEvent()
    object Refresh : HomeEvent()
    data class PressMoreButton(val playlistType: AudioPlayListType) : HomeEvent()
    data class SearchRequest(val request: String) : HomeEvent()
    data class PressAudioItem(val item: AudioInfoUi, val playlist: AudioPlayListUi) : HomeEvent()
    data class PressVideoItem(val item: VideoInfoUi) : HomeEvent()
    object PressSettingsButton : HomeEvent()
}

internal sealed class HomeEffect : BaseUiEffect {
    data class ShowError(val failures: HomeFailures) : HomeEffect()
}

internal sealed class HomeAction : BaseAction {
    object Navigate : HomeAction()
    data class UpdateLoading(val isLoading: Boolean) : HomeAction()
    data class UpdateResponse(val searchResponse: MediaSearchResponse?) : HomeAction()
    data class UpdateMedia(val playLists: List<AudioPlayListUi>, val videos: List<VideoInfoUi>) : HomeAction()
}
