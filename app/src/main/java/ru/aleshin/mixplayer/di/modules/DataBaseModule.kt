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

import android.content.Context
import android.media.MediaMetadataRetriever
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.aleshin.features.settings.api.data.datasource.SettingsDao
import ru.aleshin.features.settings.api.data.datasource.SettingsDataBase
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 15.06.2023.
 */
@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideSettingsDataBase(applicationContext: Context): SettingsDataBase {
        return Room.databaseBuilder(
            context = applicationContext,
            klass = SettingsDataBase::class.java,
            name = SettingsDataBase.NAME,
        ).createFromAsset("database/mixplayer_prepopulated_settings.db").build()
    }

    @Provides
    @Singleton
    fun provideSettingsDao(dataBase: SettingsDataBase): SettingsDao {
        return dataBase.fetchSettingsDao()
    }
}
