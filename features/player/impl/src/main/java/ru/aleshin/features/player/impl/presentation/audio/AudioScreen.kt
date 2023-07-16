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

package ru.aleshin.features.player.impl.presentation.audio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.aleshin.core.common.platform.screen.ScreenContent
import ru.aleshin.core.ui.theme.MixPlayerRes
import ru.aleshin.core.ui.views.ErrorSnackbar
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioEffect
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioEvent
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioViewState
import ru.aleshin.features.player.impl.presentation.mappers.mapToMessage
import ru.aleshin.features.player.impl.presentation.theme.PlayerTheme
import ru.aleshin.features.player.impl.presentation.theme.PlayerThemeRes
import ru.aleshin.features.player.impl.presentation.audio.screenmodel.rememberAudioScreenModel
import ru.aleshin.features.player.impl.presentation.audio.views.AudioTopBar
import ru.aleshin.features.player.impl.presentation.mappers.mapToString
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 11.07.2023
 */
internal class AudioScreen @Inject constructor() : Screen {

    @Composable
    override fun Content() = ScreenContent(
        screenModel = rememberAudioScreenModel(),
        initialState = AudioViewState(),
    ) { state ->
        PlayerTheme {
            val strings = PlayerThemeRes.strings
            val snackbarState = remember { SnackbarHostState() }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { paddingValues ->
                    AudioContent(
                        state = state,
                        modifier = Modifier.padding(paddingValues),
                        onChangePosition = { dispatchEvent(AudioEvent.UpdatePosition(it)) },
                        onPlayPauseClick = { dispatchEvent(AudioEvent.PressPlayOrPauseButton) },
                        onNextClick = { dispatchEvent(AudioEvent.PressNextButton) },
                        onPreviousClick = { dispatchEvent(AudioEvent.PressPreviousButton) },
                    )
                },
                topBar = {
                    AudioTopBar(
                        onBackPress = { dispatchEvent(AudioEvent.PressBackButton) },
                        playlistName = state.playList?.listType?.mapToString(),
                        volume = state.volume,
                        onVolumeChange = { dispatchEvent(AudioEvent.ChangeVolume(it)) }
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarState) { ErrorSnackbar(it) }
                },
            )

            handleEffect { effect ->
                when (effect) {
                    is AudioEffect.ShowError -> {
                        snackbarState.showSnackbar(
                            message = effect.failures.mapToMessage(strings),
                            withDismissAction = true,
                        )
                    }
                    AudioEffect.NextAudio -> { dispatchEvent(AudioEvent.PressNextButton) }
                }
            }
            AudioSystemUi()
        }
    }
    
    @Composable
    private fun AudioSystemUi() {
        val systemUiController = rememberSystemUiController()
        val navBarColor = MaterialTheme.colorScheme.background
        val statusBarColor = MaterialTheme.colorScheme.background
        val isDarkIcons = MixPlayerRes.colorsType.isDark

        SideEffect {
            systemUiController.setNavigationBarColor(color = navBarColor, darkIcons = !isDarkIcons)
            systemUiController.setStatusBarColor(color = statusBarColor, darkIcons = !isDarkIcons)
        }
    }
}