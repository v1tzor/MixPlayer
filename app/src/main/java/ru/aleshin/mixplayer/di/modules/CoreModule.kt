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
import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.managers.DateManager
import ru.aleshin.core.common.notifications.NotificationCreator
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Module
interface CoreModule {

    @Binds
    fun bindNotificationCreator(creator: NotificationCreator.Base): NotificationCreator

    @Binds
    @Singleton
    fun bindCoroutineManager(manager: CoroutineManager.Base): CoroutineManager

    @Binds
    fun bindDateManager(manager: DateManager.Base): DateManager
}
