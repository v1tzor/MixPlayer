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

import dagger.Binds
import dagger.Module
import ru.aleshin.core.common.di.FeatureRouter
import ru.aleshin.core.common.di.FeatureScope
import ru.aleshin.core.common.navigation.CommandBuffer
import ru.aleshin.core.common.navigation.NavigationProcessor
import ru.aleshin.core.common.navigation.Router
import ru.aleshin.core.common.navigation.navigator.NavigatorManager

/**
 * @author Stanislav Aleshin on 15.06.2023.
 */
@Module
interface NavigationModule {

    @Binds
    @FeatureScope
    fun bindLocalCommandBuffer(buffer: CommandBuffer.Base): CommandBuffer

    @Binds
    @FeatureScope
    fun bindLocalNavigationProcessor(processor: NavigationProcessor.Base): NavigationProcessor

    @Binds
    @FeatureScope
    fun bindLocalNavigatorManager(manager: NavigatorManager.Base): NavigatorManager

    @Binds
    @FeatureRouter
    @FeatureScope
    fun bindLocalRouter(router: Router.Base): Router
}
