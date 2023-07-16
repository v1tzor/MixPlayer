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

package ru.aleshin.features.player.impl.presentation.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import ru.aleshin.core.common.navigation.navigator.AppNavigator
import ru.aleshin.core.common.navigation.navigator.rememberNavigatorManager
import ru.aleshin.features.player.impl.presentation.theme.PlayerTheme
import ru.aleshin.features.player.impl.di.holder.PlayerComponentHolder
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
internal class NavScreen @Inject constructor() : Screen {

    @Composable
    override fun Content() = PlayerTheme {
        AppNavigator(
            navigatorManager = rememberNavigatorManager {
                PlayerComponentHolder.fetchComponent().fetchLocalNavigatorManager()
            },
            content = { CurrentScreen() },
        )
    }
}
