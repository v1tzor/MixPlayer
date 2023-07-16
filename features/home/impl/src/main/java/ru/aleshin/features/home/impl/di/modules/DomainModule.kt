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

import dagger.Binds
import dagger.Module
import ru.aleshin.core.common.di.FeatureScope
import ru.aleshin.features.home.impl.domain.common.HomeEitherWrapper
import ru.aleshin.features.home.impl.domain.common.HomeErrorHandler
import ru.aleshin.features.home.impl.domain.interactors.AudioInteractor
import ru.aleshin.features.home.impl.domain.interactors.VideosInteractor

/**
 * @author Stanislav Aleshin on 11.07.2023.
 */
@Module
internal interface DomainModule {

    @Binds
    @FeatureScope
    fun bindHomeErrorHandler(handler: HomeErrorHandler.Base): HomeErrorHandler

    @Binds
    @FeatureScope
    fun bindHomeEitherWrapper(wrapper: HomeEitherWrapper.Base): HomeEitherWrapper


    @Binds
    fun bindAudioInteractor(interactor: AudioInteractor.Base): AudioInteractor

    @Binds
    fun bindVideosInteractor(interactor: VideosInteractor.Base): VideosInteractor
}