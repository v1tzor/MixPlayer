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

package ru.aleshin.mixplayer.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.aleshin.features.player.api.presentation.common.MediaCommunicator
import ru.aleshin.features.player.api.presentation.common.MediaController
import ru.aleshin.features.player.api.presentation.common.PlaybackCommunicator
import ru.aleshin.features.player.api.presentation.common.PlaybackManager
import ru.aleshin.mixplayer.navigation.GlobalNavigationManager
import ru.aleshin.mixplayer.presentation.ui.main.viewmodel.MainEffectCommunicator
import ru.aleshin.mixplayer.presentation.ui.main.viewmodel.MainStateCommunicator
import ru.aleshin.mixplayer.presentation.ui.main.viewmodel.MainViewModel
import ru.aleshin.mixplayer.presentation.ui.main.viewmodel.MainWorkProcessor
import ru.aleshin.mixplayer.presentation.ui.main.viewmodel.NavigationWorkProcessor
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Module
interface PresentationModule {

    // Common

    @Binds
    @Singleton
    fun bindNavigationManager(manager: GlobalNavigationManager.Base): GlobalNavigationManager

    @Binds
    @Singleton
    fun bindMediaCommunicator(communicator: MediaCommunicator.Base): MediaCommunicator

    @Binds
    @Singleton
    fun bindPlaybackCommunicator(communicator: PlaybackCommunicator.Base): PlaybackCommunicator

    @Binds
    @Singleton
    fun bindPlaybackManager(manager: PlaybackManager.Base): PlaybackManager

    @Binds
    @Singleton
    fun bindMediaController(controller: MediaController.Base): MediaController

    // Main Activity

    @Binds
    fun bindMainViewModelFactory(factory: MainViewModel.Factory): ViewModelProvider.Factory

    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @Singleton
    fun bindMainStateCommunicator(communicator: MainStateCommunicator.Base): MainStateCommunicator

    @Binds
    @Singleton
    fun bindMainEffectCommunicator(communicator: MainEffectCommunicator.Base): MainEffectCommunicator

    @Binds
    fun bindMainWorkProcessor(processor: MainWorkProcessor.Base): MainWorkProcessor

    @Binds
    fun bindNavigationWorkProcessor(processor: NavigationWorkProcessor.Base): NavigationWorkProcessor
}
