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

package ru.aleshin.features.home.impl.navigation

import android.util.Log
import cafe.adriel.voyager.core.screen.Screen
import ru.aleshin.core.common.di.FeatureRouter
import ru.aleshin.core.common.navigation.Router
import ru.aleshin.features.home.api.di.HomeScreens
import ru.aleshin.features.home.impl.presentation.home.HomeScreen
import ru.aleshin.features.home.impl.presentation.details.DetailsScreen
import ru.aleshin.features.home.impl.presentation.details.screenmodel.DetailsInfoCommunicator
import ru.aleshin.features.home.impl.presentation.mappers.mapToDomain
import ru.aleshin.features.player.api.navigation.PlayerFeatureStarter
import ru.aleshin.features.player.api.navigation.PlayerScreens
import ru.aleshin.features.settings.api.navigation.SettingsFeatureStarter
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 11.07.2023.
 */
internal interface HomeNavigationManager {

    suspend fun navigateToLocalScreen(navScreen: HomeScreens, isRoot: Boolean = false)
    fun navigateToPlayer(screen: PlayerScreens)
    fun navigateToSettings()
    fun navigateToLocalBack()

    class Base @Inject constructor(
        @FeatureRouter private val localRouter: Router,
        private val globalRouter: Router,
        private val playerFeatureStarter: Provider<PlayerFeatureStarter>,
        private val settingsFeatureStarter: Provider<SettingsFeatureStarter>,
        private val detailsInfoCommunicator: DetailsInfoCommunicator,
    ) : HomeNavigationManager {

        override suspend fun navigateToLocalScreen(navScreen: HomeScreens, isRoot: Boolean) = when (navScreen) {
            is HomeScreens.Home -> {
                localNav(HomeScreen(), isRoot)
            }
            is HomeScreens.Details -> {
                localNav(DetailsScreen(), isRoot)
                detailsInfoCommunicator.update(navScreen.playList)
            }
        }

        override fun navigateToPlayer(screen: PlayerScreens) {
            globalRouter.navigateTo(playerFeatureStarter.get().fetchPlayerScreen(screen))
        }

        override fun navigateToSettings() {
            globalRouter.navigateTo(settingsFeatureStarter.get().fetchMainScreen())
        }

        override fun navigateToLocalBack() = localRouter.navigateBack()

        private fun localNav(screen: Screen, isRoot: Boolean) = with(localRouter) {
            if (isRoot) replaceTo(screen, true) else navigateTo(screen)
        }
    }
}
