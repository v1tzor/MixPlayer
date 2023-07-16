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

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.aleshin.features.settings.api.data.models.MixPlayerSettingsEntity

/**
 * @author Stanislav Aleshin on 15.06.2023.
 */
@Dao
interface SettingsDao {

    @Query("SELECT * FROM MixPlayerSettings WHERE id = 0")
    fun fetchSettingsFlow(): Flow<MixPlayerSettingsEntity>

    @Query("SELECT * FROM MixPlayerSettings WHERE id = 0")
    suspend fun fetchSettings(): MixPlayerSettingsEntity

    @Update(entity = MixPlayerSettingsEntity::class)
    suspend fun updateSettings(entity: MixPlayerSettingsEntity)
}
