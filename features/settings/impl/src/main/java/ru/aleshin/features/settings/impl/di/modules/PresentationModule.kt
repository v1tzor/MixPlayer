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

package ru.aleshin.features.settings.impl.di.modules

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dagger.Binds
import dagger.Module
import ru.aleshin.core.common.di.FeatureScope
import ru.aleshin.features.settings.api.navigation.SettingsFeatureStarter
import ru.aleshin.features.settings.impl.navigation.SettingsFeatureStarterImpl
import ru.aleshin.features.settings.impl.navigation.SettingsNavigationManager
import ru.aleshin.features.settings.impl.presentation.settings.SettingsScreen
import ru.aleshin.features.settings.impl.presentation.settings.screenmodel.SettingsEffectCommunicator
import ru.aleshin.features.settings.impl.presentation.settings.screenmodel.SettingsScreenModel
import ru.aleshin.features.settings.impl.presentation.settings.screenmodel.SettingsStateCommunicator
import ru.aleshin.features.settings.impl.presentation.settings.screenmodel.SettingsWorkProcessor

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
@Module
internal interface PresentationModule {

    // Common

    @Binds
    @FeatureScope
    fun bindSettingsScreen(screen: SettingsScreen): Screen

    @Binds
    @FeatureScope
    fun bindSettingsFeatureStarter(starter: SettingsFeatureStarterImpl): SettingsFeatureStarter

    @Binds
    @FeatureScope
    fun bindSettingsNavigationManager(manager: SettingsNavigationManager.Base): SettingsNavigationManager

    // Settings

    @Binds
    fun bindSettingsScreenModel(screenModel: SettingsScreenModel): ScreenModel

    @Binds
    @FeatureScope
    fun bindSettingsStateCommunicator(communicator: SettingsStateCommunicator.Base): SettingsStateCommunicator

    @Binds
    fun bindSettingsEffectCommunicator(communicator: SettingsEffectCommunicator.Base): SettingsEffectCommunicator

    @Binds
    fun bindSettingsWorkProcessor(processor: SettingsWorkProcessor.Base): SettingsWorkProcessor
}
