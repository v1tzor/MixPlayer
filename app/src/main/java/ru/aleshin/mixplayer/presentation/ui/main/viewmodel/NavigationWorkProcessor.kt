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

import kotlinx.coroutines.delay
import ru.aleshin.core.common.functional.Constants
import ru.aleshin.core.common.platform.screenmodel.work.ActionResult
import ru.aleshin.core.common.platform.screenmodel.work.WorkCommand
import ru.aleshin.core.common.platform.screenmodel.work.WorkProcessor
import ru.aleshin.mixplayer.navigation.GlobalNavigationManager
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainAction
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainEffect
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 04.07.2023.
 */
interface NavigationWorkProcessor : WorkProcessor<NavigationWorkCommand, MainAction, MainEffect> {

    class Base @Inject constructor(
        private val navigationManager: GlobalNavigationManager,
    ) : NavigationWorkProcessor {

        override suspend fun work(command: NavigationWorkCommand) = when (command) {
            NavigationWorkCommand.NavigateToHome -> {
                delay(Constants.Delay.SPLASH)
                ActionResult(MainAction.Navigate).apply { navigationManager.navigateToHome() }
            }
        }
    }
}

sealed class NavigationWorkCommand : WorkCommand {
    object NavigateToHome : NavigationWorkCommand()
}
