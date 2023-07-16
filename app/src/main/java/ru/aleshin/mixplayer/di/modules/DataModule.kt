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
package ru.aleshin.mixplayer.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.features.home.api.data.datasources.AppAudioLocalDataSource
import ru.aleshin.features.home.api.data.datasources.AudioStoreManager
import ru.aleshin.features.home.api.data.datasources.MediaQueryParser
import ru.aleshin.features.home.api.data.datasources.SystemAudioLocalDataSource
import ru.aleshin.features.home.api.data.datasources.VideosLocalDataSource
import ru.aleshin.features.home.api.data.datasources.VideosStoreManager
import ru.aleshin.features.home.api.data.repository.AppAudioRepositoryImpl
import ru.aleshin.features.home.api.data.repository.SystemAudioRepositoryImpl
import ru.aleshin.features.home.api.data.repository.VideosRepositoryImpl
import ru.aleshin.features.home.api.domain.repositories.AppAudioRepository
import ru.aleshin.features.home.api.domain.repositories.SystemAudioRepository
import ru.aleshin.features.home.api.domain.repositories.VideosRepository
import ru.aleshin.features.settings.api.data.datasource.SettingsLocalDataSource
import ru.aleshin.features.settings.api.data.repositories.SettingsRepositoryImpl
import ru.aleshin.features.settings.api.domain.repositories.SettingsRepository
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindSettingsLocalDataSource(dataSource: SettingsLocalDataSource.Base): SettingsLocalDataSource

    @Binds
    @Singleton
    fun bindSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    fun bindSystemAudioLocalDataSource(dataSource: SystemAudioLocalDataSource.Base): SystemAudioLocalDataSource

    @Binds
    @Singleton
    fun bindSystemAudioRepository(repository: SystemAudioRepositoryImpl): SystemAudioRepository

    @Binds
    @Singleton
    fun bindAppAudioLocalDataSource(dataSource: AppAudioLocalDataSource.Base): AppAudioLocalDataSource

    @Binds
    @Singleton
    fun bindAppAudioRepository(repository: AppAudioRepositoryImpl): AppAudioRepository

    @Binds
    @Singleton
    fun bindVideosRepository(repository: VideosRepositoryImpl): VideosRepository

    @Binds
    @Singleton
    fun bindVideosLocalDataSource(dataSource: VideosLocalDataSource.Base): VideosLocalDataSource

    @Binds
    @Singleton
    fun bindAudioStoreManager(manager: AudioStoreManager.Base): AudioStoreManager

    @Binds
    @Singleton
    fun bindVideosStoreManager(manager: VideosStoreManager.Base): VideosStoreManager

    @Binds
    @Singleton
    fun bindMediaQueryParser(manager: MediaQueryParser.Base): MediaQueryParser
}
