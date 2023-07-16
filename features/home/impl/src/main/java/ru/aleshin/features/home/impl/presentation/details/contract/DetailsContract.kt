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

package ru.aleshin.features.home.impl.presentation.details.contract

import kotlinx.parcelize.Parcelize
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.core.common.platform.screenmodel.contract.*
import ru.aleshin.features.home.impl.domain.entities.HomeFailures

/**
 * @author Stanislav Aleshin on 12.07.2023
 */
@Parcelize
internal data class DetailsViewState(
    val isLoading: Boolean = true,
    val playlist: AudioPlayListUi? = null,
) : BaseViewState

internal sealed class DetailsEvent : BaseEvent {
    object Init : DetailsEvent()
    object PressBackButton : DetailsEvent()
    data class PressTrackItem(val track: AudioInfoUi) : DetailsEvent()
}

internal sealed class DetailsEffect : BaseUiEffect {
    data class ShowError(val failures: HomeFailures) : DetailsEffect()
}

internal sealed class DetailsAction : BaseAction {
    object Navigation : DetailsAction()
    data class UpdateLoading(val isLoading: Boolean) : DetailsAction()
    data class UpdatePlaylist(val playlist: AudioPlayListUi) : DetailsAction()
}
