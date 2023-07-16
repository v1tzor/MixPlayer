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

import kotlinx.coroutines.flow.flow
import ru.aleshin.core.common.functional.handle
import ru.aleshin.core.common.platform.screenmodel.work.ActionResult
import ru.aleshin.core.common.platform.screenmodel.work.EffectResult
import ru.aleshin.core.common.platform.screenmodel.work.FlowWorkProcessor
import ru.aleshin.core.common.platform.screenmodel.work.WorkCommand
import ru.aleshin.features.settings.impl.domain.interactors.SettingsInteractor
import ru.aleshin.features.settings.impl.presentation.mappers.mapToDomain
import ru.aleshin.features.settings.impl.presentation.mappers.mapToUi
import ru.aleshin.features.settings.impl.presentation.models.GeneralSettingsUi
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsAction
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsEffect
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 11.07.2023.
 */
internal interface SettingsWorkProcessor : FlowWorkProcessor<SettingsWorkCommand, SettingsAction, SettingsEffect> {

    class Base @Inject constructor(
        private val settingsInteractor: SettingsInteractor,
    ) : SettingsWorkProcessor {

        override suspend fun work(command: SettingsWorkCommand) = when (command) {
            is SettingsWorkCommand.LoadSettings -> loadSettingsWork()
            is SettingsWorkCommand.ChangeGeneralSettings -> changeGeneralSettingsWork(command.settings)
        }

        private fun changeGeneralSettingsWork(settings: GeneralSettingsUi) = flow {
            settingsInteractor.updateGeneralSettings(settings.mapToDomain()).handle(
                onLeftAction = { emit(EffectResult(SettingsEffect.ShowError(it))) }
            )
        }

        private fun loadSettingsWork() = flow {
            settingsInteractor.fetchSettings().collect { settingsEither ->
                settingsEither.handle(
                    onRightAction = {
                        emit(ActionResult(SettingsAction.UpdateSettings(general = it.general.mapToUi())))
                    },
                    onLeftAction = { emit(EffectResult(SettingsEffect.ShowError(it))) }
                )
            }
        }
    }
}

internal sealed class SettingsWorkCommand : WorkCommand {
    object LoadSettings : SettingsWorkCommand()
    data class ChangeGeneralSettings(val settings: GeneralSettingsUi) : SettingsWorkCommand()
}