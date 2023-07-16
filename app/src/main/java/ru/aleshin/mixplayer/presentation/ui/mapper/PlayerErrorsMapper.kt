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

package ru.aleshin.mixplayer.presentation.ui.mapper

import ru.aleshin.core.common.functional.audio.PlayerError
import ru.aleshin.core.ui.theme.tokens.MixPlayerStrings

/**
 * @author Stanislav Aleshin on 15.07.2023.
 */
fun PlayerError.mapToMessage(string: MixPlayerStrings) = when (this) {
    PlayerError.DATA_SOURCE -> string.dataSourcePlayerError
    PlayerError.AUDIO_FOCUS -> string.audioFocusPlayerError
    PlayerError.OTHER -> string.otherPlayerError
}