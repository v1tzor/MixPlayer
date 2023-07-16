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

package ru.aleshin.features.player.impl.di.holder

import ru.aleshin.features.player.api.di.PlayerFeatureApi
import ru.aleshin.features.player.impl.di.PlayerFeatureDependencies
import ru.aleshin.features.player.impl.di.component.PlayerComponent
import ru.aleshin.module_injector.BaseComponentHolder

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
object PlayerComponentHolder : BaseComponentHolder<PlayerFeatureApi, PlayerFeatureDependencies> {

    private var component: PlayerComponent? = null

    override fun init(dependencies: PlayerFeatureDependencies) {
        if (component == null) component = PlayerComponent.create(dependencies)
    }

    override fun clear() {
        component = null
    }

    override fun fetchApi(): PlayerFeatureApi {
        return fetchComponent()
    }

    internal fun fetchComponent() = checkNotNull(component) {
        "PlayerComponent is not initialized"
    }
}
