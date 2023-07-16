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
 * limitations under the License.
*/
package ru.aleshin.mixplayer.di.modules

import dagger.Module
import dagger.Provides
import ru.aleshin.features.home.impl.di.HomeFeatureDependencies
import ru.aleshin.features.home.impl.di.holder.HomeComponentHolder
import ru.aleshin.features.player.impl.di.PlayerFeatureDependencies
import ru.aleshin.features.player.impl.di.holder.PlayerComponentHolder
import ru.aleshin.features.settings.impl.di.SettingsFeatureDependencies
import ru.aleshin.features.settings.impl.di.holder.SettingsComponentHolder

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Module
class FeatureModule {

    @Provides
    fun provideHomeStarter(
        dependencies: HomeFeatureDependencies,
    ) = HomeComponentHolder.let {
        it.init(dependencies)
        it.fetchApi().fetchStarter()
    }

    @Provides
    fun providePlayerStarter(
        dependencies: PlayerFeatureDependencies,
    ) = PlayerComponentHolder.let {
        it.init(dependencies)
        it.fetchApi().fetchStarter()
    }

    @Provides
    fun provideSettingsStarter(
        dependencies: SettingsFeatureDependencies,
    ) = SettingsComponentHolder.let {
        it.init(dependencies)
        it.fetchApi().fetchStarter()
    }
}
