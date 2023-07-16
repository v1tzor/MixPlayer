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

import android.database.Cursor
import ru.aleshin.core.common.managers.AudioStoreUtils
import ru.aleshin.core.common.managers.VideoStoreUtils
import ru.aleshin.features.home.api.data.models.AudioInfoModel
import ru.aleshin.features.home.api.data.models.VideoInfoModel
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
interface MediaQueryParser {

    fun parseAudio(cursor: Cursor?): List<AudioInfoModel>?
    fun parseVideo(cursor: Cursor?): List<VideoInfoModel>?

    class Base @Inject constructor() : MediaQueryParser {

        override fun parseAudio(cursor: Cursor?) = when {
            cursor == null -> null
            !cursor.moveToFirst() -> emptyList()
            else -> with(AudioStoreUtils) {
                mutableListOf<AudioInfoModel>().apply {
                    do {
                        val path = checkNotNull(fetchPath(cursor))
                        val info = AudioInfoModel(
                            id = checkNotNull(fetchId(cursor)),
                            path = path,
                            title = checkNotNull(fetchTitle(cursor)),
                            artist = fetchArtist(cursor),
                            album = fetchAlbum(cursor),
                            duration = checkNotNull(fetchDuration(cursor)),
                            imagePath = path,
                            date = checkNotNull(fetchDate(cursor)),
                        )
                        add(info)
                    } while (cursor.moveToNext())
                }
            }
        }.apply { cursor?.close() }

        override fun parseVideo(cursor: Cursor?) = when {
            cursor == null -> null
            !cursor.moveToFirst() -> emptyList()
            else -> with(VideoStoreUtils) {
                mutableListOf<VideoInfoModel>().apply {
                    do {
                        val path = checkNotNull(fetchPath(cursor))
                        val info = VideoInfoModel(
                            id = checkNotNull(fetchId(cursor)),
                            path = path,
                            title = checkNotNull(fetchTitle(cursor)),
                            imagePath = path,
                            duration = checkNotNull(fetchDuration(cursor)),
                        )
                        add(info)
                    } while (cursor.moveToNext())
                }
            }
        }.apply { cursor?.close() }
    }
}
