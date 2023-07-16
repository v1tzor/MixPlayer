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

package ru.aleshin.core.common.managers

import android.database.Cursor
import android.provider.MediaStore.Video.Media

/**
 * @author Stanislav Aleshin on 15.07.2023.
 */
object VideoStoreUtils {

    fun fetchId(cursor: Cursor): Long? = cursor.getColumnIndex(Media._ID).let {
        if (it != -1) cursor.getLong(it) else null
    }

    fun fetchPath(cursor: Cursor): String? = cursor.getColumnIndex(Media.DATA).let {
        if (it != -1) cursor.getString(it) else null
    }

    fun fetchTitle(cursor: Cursor): String? = cursor.getColumnIndex(Media.TITLE).let {
        if (it != -1) cursor.getString(it) else null
    }

    fun fetchDuration(cursor: Cursor): Long? = cursor.getColumnIndex(Media.DURATION).let {
        if (it != -1) cursor.getLong(it) else null
    }
}
