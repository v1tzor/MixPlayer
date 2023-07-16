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
package ru.aleshin.core.ui.theme.tokens

import androidx.compose.runtime.staticCompositionLocalOf
import ru.aleshin.core.ui.R

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
data class MixPlayerIcons(
    val launcher: Int,
    val arrowUp: Int,
    val arrowDown: Int,
    val time: Int,
    val videos: Int,
)

internal val baseMixPlayerIcons = MixPlayerIcons(
    launcher = R.drawable.ic_launcer,
    arrowUp = R.drawable.ic_arrow_up,
    arrowDown = R.drawable.ic_arrow_down,
    time = R.drawable.ic_time,
    videos = R.drawable.ic_video,
)

val LocalMixPlayerIcons = staticCompositionLocalOf<MixPlayerIcons> {
    error("Core Icons is not provided")
}

fun fetchCoreIcons() = baseMixPlayerIcons
