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

package ru.aleshin.features.home.impl.di.modules

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dagger.Binds
import dagger.Module
import ru.aleshin.core.common.di.FeatureScope
import ru.aleshin.core.common.di.ScreenModelKey
import ru.aleshin.features.home.api.navigation.HomeFeatureStarter
import ru.aleshin.features.home.impl.navigation.HomeFeatureStarterImpl
import ru.aleshin.features.home.impl.navigation.HomeNavigationManager
import ru.aleshin.features.home.impl.presentation.details.screenmodel.DetailsEffectCommunicator
import ru.aleshin.features.home.impl.presentation.details.screenmodel.DetailsInfoCommunicator
import ru.aleshin.features.home.impl.presentation.details.screenmodel.DetailsScreenModel
import ru.aleshin.features.home.impl.presentation.details.screenmodel.DetailsStateCommunicator
import ru.aleshin.features.home.impl.presentation.details.screenmodel.DetailsWorkProcessor
import ru.aleshin.features.home.impl.presentation.home.HomeScreen
import ru.aleshin.features.home.impl.presentation.home.screenmodel.HomeEffectCommunicator
import ru.aleshin.features.home.impl.presentation.home.screenmodel.HomeScreenModel
import ru.aleshin.features.home.impl.presentation.home.screenmodel.HomeStateCommunicator
import ru.aleshin.features.home.impl.presentation.home.screenmodel.HomeWorkProcessor
import ru.aleshin.features.home.impl.presentation.nav.NavScreen

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
@Module
internal interface PresentationModule {

    @Binds
    @FeatureScope
    fun bindHomeFeatureStarter(starter: HomeFeatureStarterImpl): HomeFeatureStarter

    @Binds
    @FeatureScope
    fun bindNavScreen(screen: NavScreen): Screen

    @Binds
    @FeatureScope
    fun bindNavigationManager(manager: HomeNavigationManager.Base): HomeNavigationManager

    // Home

    @Binds
    @ScreenModelKey(HomeScreenModel::class)
    fun bindHomeScreenModel(screenModel: HomeScreenModel): ScreenModel

    @Binds
    @FeatureScope
    fun bindHomeStateCommunicator(communicator: HomeStateCommunicator.Base): HomeStateCommunicator

    @Binds
    fun bindHomeEffectCommunicator(communicator: HomeEffectCommunicator.Base): HomeEffectCommunicator

    @Binds
    fun bindHomeWorkProcessor(processor: HomeWorkProcessor.Base): HomeWorkProcessor

    // Details

    @Binds
    @ScreenModelKey(DetailsScreenModel::class)
    fun bindDetailsScreenModel(screenModel: DetailsScreenModel): ScreenModel

    @Binds
    fun bindDetailsStateCommunicator(communicator: DetailsStateCommunicator.Base): DetailsStateCommunicator

    @Binds
    fun bindDetailsEffectCommunicator(communicator: DetailsEffectCommunicator.Base): DetailsEffectCommunicator

    @Binds
    @FeatureScope
    fun bindDetailsInfoCommunicator(communicator: DetailsInfoCommunicator.Base): DetailsInfoCommunicator

    @Binds
    fun bindDetailsWorkProcessor(processor: DetailsWorkProcessor.Base): DetailsWorkProcessor
}
