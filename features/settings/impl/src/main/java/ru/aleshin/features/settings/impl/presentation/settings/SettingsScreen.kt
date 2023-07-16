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

package ru.aleshin.features.settings.impl.presentation.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.platform.screen.ScreenContent
import ru.aleshin.core.ui.views.ErrorSnackbar
import ru.aleshin.features.settings.impl.presentation.mappers.mapToMessage
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsEffect
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsEvent
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsViewState
import ru.aleshin.features.settings.impl.presentation.settings.screenmodel.rememberSettingsScreenModel
import ru.aleshin.features.settings.impl.presentation.settings.views.SettingsTopBar
import ru.aleshin.features.settings.impl.presentation.theme.SettingsTheme
import ru.aleshin.features.settings.impl.presentation.theme.SettingsThemeRes
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.06.2023
 */
internal class SettingsScreen @Inject constructor() : Screen {

    @Composable
    override fun Content() = ScreenContent(
        screenModel = rememberSettingsScreenModel(),
        initialState = SettingsViewState(),
    ) { state ->
        SettingsTheme {
            val strings = SettingsThemeRes.strings
            val snackbarState = remember { SnackbarHostState() }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { paddingValues ->
                    SettingsContent(
                        state = state,
                        modifier = Modifier.padding(paddingValues),
                        onChangeGeneralSettings = { dispatchEvent(SettingsEvent.ChangedGeneralSettings(it)) },
                    )
                },
                topBar = {
                    SettingsTopBar(
                        onBackPress = { dispatchEvent(SettingsEvent.PressBackButton) },
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarState) { ErrorSnackbar(it) }
                },
            )

            handleEffect { effect ->
                when (effect) {
                    is SettingsEffect.ShowError -> {
                        snackbarState.showSnackbar(
                            message = effect.failures.mapToMessage(strings),
                            withDismissAction = true,
                        )
                    }
                }
            }
        }
    }
}
