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

import dagger.Binds
import dagger.Module
import ru.aleshin.mixplayer.domain.interactors.SettingsInteractor
import ru.aleshin.mixplayer.domain.common.MainEitherWrapper
import ru.aleshin.mixplayer.domain.common.MainErrorHandler
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Module
interface DomainModules {

    @Binds
    @Singleton
    fun bindSettingsInteractor(interactor: SettingsInteractor.Base): SettingsInteractor

    @Binds
    @Singleton
    fun bindMainEitherWrapper(wrapper: MainEitherWrapper.Base): MainEitherWrapper

    @Binds
    @Singleton
    fun bindMainErrorHandler(handler: MainErrorHandler.Base): MainErrorHandler
}