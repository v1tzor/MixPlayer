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

package ru.aleshin.features.player.impl.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.aleshin.core.ui.theme.MixPlayerRes
import ru.aleshin.core.ui.theme.tokens.MixPlayerLanguage
import ru.aleshin.features.player.impl.presentation.theme.tokens.LocalPlayerIcons
import ru.aleshin.features.player.impl.presentation.theme.tokens.LocalPlayerStrings
import ru.aleshin.features.player.impl.presentation.theme.tokens.PlayerIcons
import ru.aleshin.features.player.impl.presentation.theme.tokens.PlayerStrings

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
@Composable
internal fun PlayerTheme(content: @Composable () -> Unit) {
    val icons = PlayerIcons.DEFAULT
    val strings = when (MixPlayerRes.language) {
        MixPlayerLanguage.EN -> PlayerStrings.ENGLISH
        MixPlayerLanguage.RU -> PlayerStrings.RUSSIAN
    }

    CompositionLocalProvider(
        LocalPlayerIcons provides icons,
        LocalPlayerStrings provides strings,
        content = content,
    )
}
