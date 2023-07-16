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

package ru.aleshin.features.player.impl.presentation.theme.tokens

import androidx.compose.runtime.staticCompositionLocalOf
import ru.aleshin.features.player.impl.R

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
internal data class PlayerIcons(
    val next: Int,
    val previous: Int,
    val pause: Int,
    val play: Int,
    val music: Int,
    val volume: Int,
    val orientation: Int,
) {
    companion object {
        val DEFAULT = PlayerIcons(
            next = R.drawable.ic_next,
            previous = R.drawable.ic_previous,
            pause = R.drawable.ic_pause,
            play = R.drawable.ic_play,
            music = R.drawable.ic_music,
            volume = R.drawable.ic_volume,
            orientation = R.drawable.ic_orientation,
        )
    }
}

internal val LocalPlayerIcons = staticCompositionLocalOf<PlayerIcons> {
    error("Player Icons is not provided")
}
