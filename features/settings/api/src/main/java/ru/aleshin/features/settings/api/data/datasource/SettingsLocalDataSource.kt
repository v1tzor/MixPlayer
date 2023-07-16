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

package ru.aleshin.features.settings.api.data.datasource

import kotlinx.coroutines.flow.Flow
import ru.aleshin.features.settings.api.data.models.MixPlayerSettingsEntity
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.06.2023
 */
interface SettingsLocalDataSource {

    fun fetchSettingsFlow(): Flow<MixPlayerSettingsEntity>
    suspend fun fetchSettings(): MixPlayerSettingsEntity
    suspend fun updateSettings(entity: MixPlayerSettingsEntity)

    class Base @Inject constructor(
        private val settingsDao: SettingsDao,
    ) : SettingsLocalDataSource {

        override fun fetchSettingsFlow(): Flow<MixPlayerSettingsEntity> {
            return settingsDao.fetchSettingsFlow()
        }

        override suspend fun fetchSettings(): MixPlayerSettingsEntity {
            return settingsDao.fetchSettings()
        }

        override suspend fun updateSettings(entity: MixPlayerSettingsEntity) {
            settingsDao.updateSettings(entity)
        }
    }
}
