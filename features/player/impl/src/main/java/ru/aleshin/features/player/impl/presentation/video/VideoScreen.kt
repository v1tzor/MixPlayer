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

package ru.aleshin.features.player.impl.presentation.video

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.aleshin.core.common.platform.screen.ScreenContent
import ru.aleshin.core.ui.theme.MixPlayerRes
import ru.aleshin.core.ui.views.ErrorSnackbar
import ru.aleshin.features.player.impl.presentation.mappers.mapToMessage
import ru.aleshin.features.player.impl.presentation.theme.PlayerThemeRes
import ru.aleshin.features.player.impl.presentation.theme.PlayerTheme
import ru.aleshin.features.player.impl.presentation.video.contract.VideoEffect
import ru.aleshin.features.player.impl.presentation.video.contract.VideoEvent
import ru.aleshin.features.player.impl.presentation.video.contract.VideoViewState
import ru.aleshin.features.player.impl.presentation.video.screenmodel.rememberVideoScreenModel
import ru.aleshin.features.player.impl.presentation.video.views.VideoTopBar
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.06.2023
 */
internal class VideoScreen @Inject constructor() : Screen {

    @Composable
    override fun Content() = ScreenContent(
        screenModel = rememberVideoScreenModel(),
        initialState = VideoViewState(),
    ) { state ->
        PlayerTheme {
            val context = LocalContext.current
            val strings = PlayerThemeRes.strings
            val snackbarState = remember { SnackbarHostState() }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { paddingValues ->
                    VideoContent(
                        state = state,
                        modifier = Modifier
                            .padding(paddingValues)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                    )
                },
                topBar = {
                    VideoTopBar(
                        videoName = state.videoInfo?.title,
                        onBackPress = { dispatchEvent(VideoEvent.PressBackButton) },
                        onOrientationChanged = { dispatchEvent(VideoEvent.PressOrientationButton) }
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarState) { ErrorSnackbar(snackbarData = it) }
                },
            )

            handleEffect { effect ->
                when (effect) {
                    is VideoEffect.ShowError -> {
                        snackbarState.showSnackbar(
                            message = effect.failures.mapToMessage(strings),
                            withDismissAction = true,
                        )
                    }
                }
            }

            LaunchedEffect(key1 = state.isPortraitOrientation) {
                val activity = context as Activity
                if (state.isPortraitOrientation) {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                } else {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            }
        }
        VideoSystemUi()
    }

    @Composable
    private fun VideoSystemUi() {
        val systemUiController = rememberSystemUiController()
        val systemsBarColor = MaterialTheme.colorScheme.surfaceContainerHigh
        val isDarkIcons = MixPlayerRes.colorsType.isDark

        SideEffect {
            systemUiController.setSystemBarsColor(color = systemsBarColor, darkIcons = !isDarkIcons)
        }
    }
}
