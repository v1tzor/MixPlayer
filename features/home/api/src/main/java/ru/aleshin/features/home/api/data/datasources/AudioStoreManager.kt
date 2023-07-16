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

package ru.aleshin.features.home.api.data.datasources

import android.content.Context
import android.provider.MediaStore
import ru.aleshin.features.home.api.data.models.AudioInfoModel
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
interface AudioStoreManager {

    fun fetchAllMedia(): List<AudioInfoModel>?

    class Base @Inject constructor(
        private val applicationContext: Context,
        private val queryParser: MediaQueryParser,
    ) : AudioStoreManager {

        private val resolver get() = applicationContext.contentResolver

        override fun fetchAllMedia(): List<AudioInfoModel>? {
            val mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val query = resolver.query(mediaUri, null, null, null, null)
            return queryParser.parseAudio(query)?.sortedByDescending { it.duration }
        }
    }
}
