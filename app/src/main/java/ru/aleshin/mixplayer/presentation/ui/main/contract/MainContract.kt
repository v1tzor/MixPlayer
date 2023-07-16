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
package ru.aleshin.mixplayer.presentation.ui.main.contract

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.aleshin.core.common.functional.MediaCommand
import ru.aleshin.core.common.functional.audio.PlayerError
import ru.aleshin.core.common.functional.audio.PlayerInfo
import ru.aleshin.core.common.platform.screenmodel.contract.BaseAction
import ru.aleshin.core.common.platform.screenmodel.contract.BaseEvent
import ru.aleshin.core.common.platform.screenmodel.contract.BaseUiEffect
import ru.aleshin.core.common.platform.screenmodel.contract.BaseViewState
import ru.aleshin.mixplayer.presentation.ui.models.GeneralSettingsUi

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Parcelize
data class MainViewState(
    val generalSettings: GeneralSettingsUi = GeneralSettingsUi(),
) : BaseViewState, Parcelable

sealed class MainEvent : BaseEvent {
    object Init : MainEvent()
    data class SendPlayerInfo(val info: PlayerInfo) : MainEvent()
    data class OnPlayerError(val error: PlayerError) : MainEvent()
}

sealed class MainEffect : BaseUiEffect {
    data class WorkMediaCommand(val command: MediaCommand) : MainEffect()
    data class ShowPlayerError(val error: PlayerError) : MainEffect()
}

sealed class MainAction : BaseAction {
    data class ChangeGeneralSettings(val generalSettings: GeneralSettingsUi) : MainAction()
    object Navigate : MainAction()
}
