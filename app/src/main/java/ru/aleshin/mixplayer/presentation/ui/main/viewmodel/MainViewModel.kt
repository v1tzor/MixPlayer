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
package ru.aleshin.mixplayer.presentation.ui.main.viewmodel

import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.platform.screenmodel.BaseViewModel
import ru.aleshin.core.common.platform.screenmodel.work.WorkScope
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainAction
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainEffect
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainEvent
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainViewState
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
class MainViewModel @Inject constructor(
    private val mainWorkProcessor: MainWorkProcessor,
    private val navigationWorkProcessor: NavigationWorkProcessor,
    stateCommunicator: MainStateCommunicator,
    effectCommunicator: MainEffectCommunicator,
    coroutineManager: CoroutineManager,
) : BaseViewModel<MainViewState, MainEvent, MainAction, MainEffect>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager,
) {

    override fun init() {
        if (!isInitialize.get()) {
            super.init()
            dispatchEvent(MainEvent.Init)
        }
    }

    override suspend fun WorkScope<MainViewState, MainAction, MainEffect>.handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.Init -> {
                launchBackgroundWork(MainWorkCommand.LoadGeneralMain) {
                    mainWorkProcessor.work(MainWorkCommand.LoadGeneralMain).collectAndHandleWork()
                }
                launchBackgroundWork(MainWorkCommand.ReceiveMediaCommand) {
                    mainWorkProcessor.work(MainWorkCommand.ReceiveMediaCommand).collectAndHandleWork()
                }
                navigationWorkProcessor.work(NavigationWorkCommand.NavigateToHome).handleWork()
            }
            is MainEvent.SendPlayerInfo -> {
                mainWorkProcessor.work(MainWorkCommand.SendPlayerInfo(event.info)).collectAndHandleWork()
            }
            is MainEvent.OnPlayerError -> sendEffect(MainEffect.ShowPlayerError(event.error))
        }
    }

    override suspend fun reduce(
        action: MainAction,
        currentState: MainViewState,
    ) = when (action) {
        is MainAction.Navigate -> currentState
        is MainAction.ChangeGeneralSettings -> currentState.copy(
            generalSettings = action.generalSettings,
        )
    }

    class Factory @Inject constructor(viewModel: Provider<MainViewModel>) :
        BaseViewModel.Factory(viewModel)
}
