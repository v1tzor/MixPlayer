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

package ru.aleshin.features.settings.impl.presentation.settings.contract

import kotlinx.parcelize.Parcelize
import ru.aleshin.core.common.platform.screenmodel.contract.*
import ru.aleshin.features.settings.impl.domain.entities.SettingsFailures
import ru.aleshin.features.settings.impl.presentation.models.GeneralSettingsUi

/**
 * @author Stanislav Aleshin on 15.06.2023
 */
@Parcelize
internal data class SettingsViewState(
    val generalSettings: GeneralSettingsUi? = null,
) : BaseViewState

internal sealed class SettingsEvent : BaseEvent {
    object Init : SettingsEvent()
    object PressBackButton : SettingsEvent()
    data class ChangedGeneralSettings(val settings: GeneralSettingsUi?) : SettingsEvent()
}

internal sealed class SettingsEffect : BaseUiEffect {
    data class ShowError(val failures: SettingsFailures) : SettingsEffect()
}

internal sealed class SettingsAction : BaseAction {
    object Navigate : SettingsAction()
    data class UpdateSettings(val general: GeneralSettingsUi?) : SettingsAction()
}
