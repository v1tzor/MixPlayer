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

package ru.aleshin.features.player.impl.di.component

import dagger.Component
import ru.aleshin.core.common.di.FeatureScope
import ru.aleshin.core.common.navigation.navigator.NavigatorManager
import ru.aleshin.features.player.api.di.PlayerFeatureApi
import ru.aleshin.features.player.impl.di.PlayerFeatureDependencies
import ru.aleshin.features.player.impl.di.modules.DataModule
import ru.aleshin.features.player.impl.di.modules.DomainModule
import ru.aleshin.features.player.impl.di.modules.NavigationModule
import ru.aleshin.features.player.impl.di.modules.PresentationModule
import ru.aleshin.features.player.impl.presentation.audio.screenmodel.AudioScreenModel
import ru.aleshin.features.player.impl.presentation.video.screenmodel.VideoScreenModel

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
@FeatureScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        NavigationModule::class,
    ],
    dependencies = [PlayerFeatureDependencies::class],
)
internal interface PlayerComponent : PlayerFeatureApi {

    fun fetchLocalNavigatorManager(): NavigatorManager
    fun fetchAudioScreenModel(): AudioScreenModel
    fun fetchVideoScreenModel(): VideoScreenModel

    @Component.Builder
    interface Builder {
        fun dependencies(deps: PlayerFeatureDependencies): Builder
        fun build(): PlayerComponent
    }

    companion object {
        fun create(deps: PlayerFeatureDependencies): PlayerComponent {
            return DaggerPlayerComponent.builder()
                .dependencies(deps)
                .build()
        }
    }
}
