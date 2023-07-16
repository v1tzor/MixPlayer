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
import ru.aleshin.features.player.api.navigation.PlayerFeatureStarter
import ru.aleshin.features.player.api.navigation.PlayerScreens
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
internal class PlayerFeatureStarterImpl @Inject constructor(
    private val localNavScreen: Screen,
    private val navigationManager: PlayerNavigationManager,
) : PlayerFeatureStarter {

    override fun fetchPlayerScreen(navScreen: PlayerScreens) = navigationManager.navigateToLocalScreen(
        navScreen = navScreen,
        isRoot = true
    ).let {
        return@let localNavScreen
    }
}
