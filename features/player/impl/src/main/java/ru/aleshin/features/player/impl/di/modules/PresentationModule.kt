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

package ru.aleshin.features.player.impl.di.modules

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dagger.Binds
import dagger.Module
import ru.aleshin.core.common.di.FeatureScope
import ru.aleshin.core.common.di.ScreenModelKey
import ru.aleshin.features.player.api.navigation.PlayerFeatureStarter
import ru.aleshin.features.player.impl.navigation.PlayerNavigationManager
import ru.aleshin.features.player.impl.navigation.PlayerFeatureStarterImpl
import ru.aleshin.features.player.impl.presentation.audio.screenmodel.AudioEffectCommunicator
import ru.aleshin.features.player.impl.presentation.audio.screenmodel.AudioInfoCommunicator
import ru.aleshin.features.player.impl.presentation.audio.screenmodel.AudioScreenModel
import ru.aleshin.features.player.impl.presentation.audio.screenmodel.AudioStateCommunicator
import ru.aleshin.features.player.impl.presentation.audio.screenmodel.AudioWorkProcessor
import ru.aleshin.features.player.impl.presentation.nav.NavScreen
import ru.aleshin.features.player.impl.presentation.video.screenmodel.VideoEffectCommunicator
import ru.aleshin.features.player.impl.presentation.video.screenmodel.VideoInfoCommunicator
import ru.aleshin.features.player.impl.presentation.video.screenmodel.VideoScreenModel
import ru.aleshin.features.player.impl.presentation.video.screenmodel.VideoStateCommunicator
import ru.aleshin.features.player.impl.presentation.video.screenmodel.VideoWorkProcessor

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
@Module
internal interface PresentationModule {

    // Common

    @Binds
    @FeatureScope
    fun bindAuthFeatureStarter(starter: PlayerFeatureStarterImpl): PlayerFeatureStarter

    @Binds
    @FeatureScope
    fun bindLocalNavigationManager(manager: PlayerNavigationManager.Base): PlayerNavigationManager

    @Binds
    @FeatureScope
    fun bindNavScreen(screen: NavScreen): Screen

    // Audio

    @Binds
    @ScreenModelKey(AudioScreenModel::class)
    fun bindAudioScreenModel(screenModel: AudioScreenModel): ScreenModel

    @Binds
    fun bindAudioStateCommunicator(communicator: AudioStateCommunicator.Base): AudioStateCommunicator

    @Binds
    fun bindAudioEffectCommunicator(communicator: AudioEffectCommunicator.Base): AudioEffectCommunicator

    @Binds
    @FeatureScope
    fun bindAudioInfoCommunicator(communicator: AudioInfoCommunicator.Base): AudioInfoCommunicator

    @Binds
    fun bindAudioWorkProcessor(workProcessor: AudioWorkProcessor.Base): AudioWorkProcessor

    // Video

    @Binds
    @ScreenModelKey(VideoScreenModel::class)
    fun bindVideoScreenModel(screenModel: VideoScreenModel): ScreenModel

    @Binds
    fun bindVideoStateCommunicator(communicator: VideoStateCommunicator.Base): VideoStateCommunicator

    @Binds
    fun bindVideoEffectCommunicator(communicator: VideoEffectCommunicator.Base): VideoEffectCommunicator

    @Binds
    @FeatureScope
    fun bindVideoInfoCommunicator(communicator: VideoInfoCommunicator.Base): VideoInfoCommunicator

    @Binds
    fun bindVideoWorkProcessor(workProcessor: VideoWorkProcessor.Base): VideoWorkProcessor
}
