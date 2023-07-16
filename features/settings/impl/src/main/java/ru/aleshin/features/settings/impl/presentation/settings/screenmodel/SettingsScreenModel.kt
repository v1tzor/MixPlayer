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

package ru.aleshin.features.settings.impl.presentation.settings.screenmodel

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.platform.screenmodel.BaseScreenModel
import ru.aleshin.core.common.platform.screenmodel.work.WorkScope
import ru.aleshin.features.settings.impl.di.holder.SettingsComponentHolder
import ru.aleshin.features.settings.impl.navigation.SettingsNavigationManager
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsAction
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsEffect
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsEvent
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsViewState
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.06.2023
 */
internal class SettingsScreenModel @Inject constructor(
    private val settingsWorkProcessor: SettingsWorkProcessor,
    private val navigationManager: SettingsNavigationManager,
    stateCommunicator: SettingsStateCommunicator,
    effectCommunicator: SettingsEffectCommunicator,
    coroutineManager: CoroutineManager,
) : BaseScreenModel<SettingsViewState, SettingsEvent, SettingsAction, SettingsEffect>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager,
) {

    override fun init() {
        if (!isInitialize.get()) {
            super.init()
            dispatchEvent(SettingsEvent.Init)
        }
    }

    override suspend fun WorkScope<SettingsViewState, SettingsAction, SettingsEffect>.handleEvent(
        event: SettingsEvent,
    ) {
        when (event) {
            is SettingsEvent.Init -> launchBackgroundWork(SettingsWorkCommand.LoadSettings) {
                settingsWorkProcessor.work(SettingsWorkCommand.LoadSettings).collectAndHandleWork()
            }
            is SettingsEvent.ChangedGeneralSettings -> {
                val settings = checkNotNull(event.settings)
                settingsWorkProcessor.work(SettingsWorkCommand.ChangeGeneralSettings(settings)).collectAndHandleWork()
            }
            is SettingsEvent.PressBackButton -> navigationManager.navigateToBack()
        }
    }

    override suspend fun reduce(
        action: SettingsAction,
        currentState: SettingsViewState,
    ) = when (action) {
        is SettingsAction.Navigate -> currentState.copy()
        is SettingsAction.UpdateSettings -> currentState.copy(
            generalSettings = action.general,
        )
    }
}

@Composable
internal fun Screen.rememberSettingsScreenModel(): SettingsScreenModel {
    val component = SettingsComponentHolder.fetchComponent()
    return rememberScreenModel { component.fetchSettingsScreenModel() }
}
