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

import ru.aleshin.features.home.api.data.models.VideoInfoModel
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.07.2023.
 */
interface VideosLocalDataSource {

    fun fetchAllVideos(): List<VideoInfoModel>

    class Base @Inject constructor(
        private val videoStoreManager: VideosStoreManager
    ) : VideosLocalDataSource {

        override fun fetchAllVideos(): List<VideoInfoModel> {
            return checkNotNull(videoStoreManager.fetchAllMedia())
        }
    }

}