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
import ru.aleshin.core.common.di.FeatureScope
import ru.aleshin.features.player.impl.domain.common.PlayerEitherWrapper
import ru.aleshin.features.player.impl.domain.common.PlayerErrorHandler
import ru.aleshin.features.player.impl.domain.interactors.AudioInteractor
import ru.aleshin.features.player.impl.domain.interactors.PlayerSettingsInteractor

/**
 * @author Stanislav Aleshin on 05.07.2023.
 */
@Module
internal interface DomainModule {

    @Binds
    @FeatureScope
    fun bindAuthErrorHandler(handler: PlayerErrorHandler.Base): PlayerErrorHandler

    @Binds
    @FeatureScope
    fun bindAuthEiterWrapper(wrapper: PlayerEitherWrapper.Base): PlayerEitherWrapper

    @Binds
    fun bindPlayerSettingsInteractor(interactor: PlayerSettingsInteractor.Base): PlayerSettingsInteractor

    @Binds
    fun bindAudioInteractor(interactor: AudioInteractor.Base): AudioInteractor
}
