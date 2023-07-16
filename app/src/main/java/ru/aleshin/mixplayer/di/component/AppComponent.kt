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

package ru.aleshin.mixplayer.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.aleshin.core.common.notifications.NotificationCreator
import ru.aleshin.mixplayer.presentation.ui.main.MainActivity
import ru.aleshin.features.home.impl.di.HomeFeatureDependencies
import ru.aleshin.features.player.impl.di.PlayerFeatureDependencies
import ru.aleshin.features.settings.impl.di.SettingsFeatureDependencies
import ru.aleshin.mixplayer.di.modules.CoreModule
import ru.aleshin.mixplayer.di.modules.DataBaseModule
import ru.aleshin.mixplayer.di.modules.DataModule
import ru.aleshin.mixplayer.di.modules.DependenciesModule
import ru.aleshin.mixplayer.di.modules.DomainModules
import ru.aleshin.mixplayer.di.modules.FeatureModule
import ru.aleshin.mixplayer.di.modules.NavigationModule
import ru.aleshin.mixplayer.di.modules.PresentationModule
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Singleton
@Component(
    modules = [
        DataBaseModule::class,
        DataModule::class,
        NavigationModule::class,
        CoreModule::class,
        PresentationModule::class,
        DomainModules::class,
        DependenciesModule::class,
        FeatureModule::class,
    ],
)
interface AppComponent : AppDependencies {

    fun inject(activity: MainActivity)
    fun fetchNotificationCreator(): NotificationCreator

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun dataBaseModule(module: DataBaseModule): Builder
        fun navigationModule(module: NavigationModule): Builder
        fun featureModule(module: FeatureModule): Builder
        fun build(): AppComponent
    }

    companion object {
        fun create(context: Context): AppComponent {
            return DaggerAppComponent.builder()
                .applicationContext(context)
                .dataBaseModule(DataBaseModule())
                .navigationModule(NavigationModule())
                .featureModule(FeatureModule())
                .build()
        }
    }
}

interface AppDependencies :
    HomeFeatureDependencies,
    PlayerFeatureDependencies,
    SettingsFeatureDependencies
