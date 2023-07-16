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

package ru.aleshin.features.home.impl.presentation.theme.tokens

import androidx.compose.runtime.staticCompositionLocalOf
import ru.aleshin.features.home.impl.R

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
internal data class HomeIcons(
    val emptySearch: Int,
    val emptyList: Int,
    val music: Int,
    val more: Int,
) {
    companion object {
        val DEFAULT = HomeIcons(
            emptySearch = R.drawable.ic_content_search,
            emptyList = R.drawable.ic_magnify_scan,
            music = R.drawable.ic_music,
            more = R.drawable.ic_more,
        )
    }
}

internal val LocalHomeIcons = staticCompositionLocalOf<HomeIcons> {
    error("Home Icons is not provided")
}
