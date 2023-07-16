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

package ru.aleshin.features.player.impl.navigation

import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.di.FeatureRouter
import ru.aleshin.core.common.functional.MediaCommand
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.core.common.navigation.Router
import ru.aleshin.features.home.api.di.HomeScreens
import ru.aleshin.features.home.api.navigation.HomeFeatureStarter
import ru.aleshin.features.player.api.navigation.PlayerScreens
import ru.aleshin.features.player.api.presentation.common.MediaController
import ru.aleshin.features.player.impl.presentation.audio.AudioScreen
import ru.aleshin.features.player.impl.presentation.audio.screenmodel.AudioInfoCommunicator
import ru.aleshin.features.player.impl.presentation.video.VideoScreen
import ru.aleshin.features.player.impl.presentation.video.screenmodel.VideoInfoCommunicator
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 04.07.2023.
 */
internal interface PlayerNavigationManager {

    suspend fun navigateToDetails(playlist: AudioPlayListUi)
    fun navigateToLocalScreen(navScreen: PlayerScreens, isRoot: Boolean)
    fun navigateToBack()

    class Base @Inject constructor(
        @FeatureRouter private val localRouter: Router,
        private val globalRouter: Router,
        private val homeStarter: Provider<HomeFeatureStarter>,
        private val audioInfoCommunicator: AudioInfoCommunicator,
        private val videoInfoCommunicator: VideoInfoCommunicator,
        private val mediaController: MediaController,
    ) : PlayerNavigationManager {

        override fun navigateToLocalScreen(navScreen: PlayerScreens, isRoot: Boolean) {
            when (navScreen) {
                is PlayerScreens.Audio -> {
                    mediaController.work(MediaCommand.SelectAudio(navScreen.audio, navScreen.playListType))
                    audioInfoCommunicator.update(navScreen.playListType)
                    localNav(AudioScreen(), isRoot)
                }
                is PlayerScreens.Video -> {
                    videoInfoCommunicator.update(navScreen.videoInfoModel)
                    localNav(VideoScreen(), isRoot)
                }
            }
        }

        override suspend fun navigateToDetails(playlist: AudioPlayListUi) {
            val screen = homeStarter.get().fetchHomeScreen(HomeScreens.Details(playlist))
            globalRouter.navigateTo(screen)
        }

        override fun navigateToBack() = globalRouter.navigateBack()

        private fun localNav(screen: Screen, isRoot: Boolean) = with(localRouter) {
            if (isRoot) replaceTo(screen, true) else navigateTo(screen)
        }
    }
}
