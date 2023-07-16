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

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
internal data class PlayerStrings(
    val playerHeader: String,
    val otherError: String,
    val backDesk: String?,
    val systemAudioTitle: String,
    val appAudioTitle: String,
    val previousAudioDesk: String,
    val nextAudioDesk: String,
    val playPauseAudioDesk: String,
    val orientationDesk: String,
) {
    companion object {
        val RUSSIAN = PlayerStrings(
            playerHeader = "Проигрыватель",
            otherError = "Ошибка! Обратитесь к разработчику!",
            backDesk = "Назад",
            systemAudioTitle = "Системные треки",
            appAudioTitle = "Треки приложения",
            previousAudioDesk = "Предыдущий трек",
            nextAudioDesk = "Следующий трек",
            playPauseAudioDesk = "Плей/Пауза",
            orientationDesk = "Сменить ориентацию",
        )
        val ENGLISH = PlayerStrings(
            playerHeader = "Player",
            otherError = "Error! Contact the developer!",
            backDesk = "Back",
            systemAudioTitle = "System tracks",
            appAudioTitle = "App tracks",
            previousAudioDesk = "Previous track",
            nextAudioDesk = "Next track",
            playPauseAudioDesk = "Play/Pause",
            orientationDesk = "Change orientation"
        )
    }
}

internal val LocalPlayerStrings = staticCompositionLocalOf<PlayerStrings> {
    error("Player Strings is not provided")
}
