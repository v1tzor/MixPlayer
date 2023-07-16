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

package ru.aleshin.features.home.impl.presentation.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.extensions.isAllowPermission
import ru.aleshin.core.common.platform.screen.ScreenContent
import ru.aleshin.core.ui.views.ErrorSnackbar
import ru.aleshin.features.home.impl.presentation.home.contract.HomeEffect
import ru.aleshin.features.home.impl.presentation.home.contract.HomeEvent
import ru.aleshin.features.home.impl.presentation.home.contract.HomeViewState
import ru.aleshin.features.home.impl.presentation.home.screenmodel.rememberHomeScreenModel
import ru.aleshin.features.home.impl.presentation.mappers.mapToMessage
import ru.aleshin.features.home.impl.presentation.theme.HomeTheme
import ru.aleshin.features.home.impl.presentation.theme.HomeThemeRes
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 09.07.2023
 */
internal class HomeScreen @Inject constructor() : Screen {

    @Composable
    override fun Content() = ScreenContent(
        screenModel = rememberHomeScreenModel(),
        initialState = HomeViewState(),
    ) { state ->
        HomeTheme {
            val context = LocalContext.current
            val strings = HomeThemeRes.strings
            val snackbarState = remember { SnackbarHostState() }
            val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) dispatchEvent(HomeEvent.Refresh)
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { paddingValues ->
                    HomeContent(
                        state = state,
                        modifier = Modifier.padding(paddingValues),
                        onSettingsClick = { dispatchEvent(HomeEvent.PressSettingsButton) },
                        onRefreshClick = { dispatchEvent(HomeEvent.Refresh) },
                        onSearchMedia = { dispatchEvent(HomeEvent.SearchRequest(it)) },
                        onAudioClick = { track, list -> dispatchEvent(HomeEvent.PressAudioItem(track, list)) },
                        onVideoClick = { dispatchEvent(HomeEvent.PressVideoItem(it)) },
                        onMoreSystemAudioPress = { dispatchEvent(HomeEvent.PressMoreButton(it)) },
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarState) { ErrorSnackbar(snackbarData = it) }
                },
            )

            handleEffect { effect ->
                when (effect) {
                    is HomeEffect.ShowError -> {
                        snackbarState.showSnackbar(
                            message = effect.failures.mapToMessage(strings),
                            withDismissAction = true,
                        )
                    }
                }
            }

            SideEffect {
                if (!context.isAllowPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }
}
