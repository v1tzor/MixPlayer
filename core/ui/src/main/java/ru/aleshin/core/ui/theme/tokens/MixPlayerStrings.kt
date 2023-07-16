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

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
data class MixPlayerStrings(
    val appName: String,
    val alertDialogDismissTitle: String,
    val alertDialogSelectConfirmTitle: String,
    val alertDialogOkConfirmTitle: String,
    val warningDialogTitle: String,
    val warningDeleteConfirmTitle: String,
    val minutesSymbol: String,
    val hoursSymbol: String,
    val separator: String,
    val amFormatTitle: String,
    val pmFormatTitle: String,
    val purchasesListTitle: String,
    val appNameSplash: String,
    val dataSourcePlayerError: String,
    val audioFocusPlayerError: String,
    val otherPlayerError: String,
)

internal val russianMixPlayerString = MixPlayerStrings(
    appName = "MixPlayer",
    alertDialogDismissTitle = "Отменить",
    alertDialogSelectConfirmTitle = "Выбрать",
    alertDialogOkConfirmTitle = "ОК",
    warningDialogTitle = "Предупреждение!",
    warningDeleteConfirmTitle = "Удалить",
    minutesSymbol = "м",
    hoursSymbol = "ч",
    separator = ":",
    amFormatTitle = "AM",
    pmFormatTitle = "PM",
    purchasesListTitle = "Список",
    appNameSplash = "MIX PLAYER",
    dataSourcePlayerError = "Медиа файл повреждён!",
    audioFocusPlayerError = "Аудио фокус потерян.",
    otherPlayerError = "Ошибка воспроизведения аудио! Попробуйте снова."
)

internal val englishMixPlayerString = MixPlayerStrings(
    appName = "MixPlayer",
    alertDialogDismissTitle = "Cancel",
    alertDialogSelectConfirmTitle = "Select",
    alertDialogOkConfirmTitle = "OK",
    warningDialogTitle = "Warning!",
    warningDeleteConfirmTitle = "Delete",
    minutesSymbol = "m",
    hoursSymbol = "h",
    separator = ":",
    amFormatTitle = "AM",
    pmFormatTitle = "PM",
    purchasesListTitle = "List",
    appNameSplash = "MIX PLAYER",
    dataSourcePlayerError = "The media file is corrupted!",
    audioFocusPlayerError = "Audio focus is lost.",
    otherPlayerError = "Audio playback error! Try again.",
)

val LocalMixPlayerStrings = staticCompositionLocalOf<MixPlayerStrings> {
    error("Core Strings is not provided")
}

fun fetchCoreStrings(language: MixPlayerLanguage) = when (language) {
    MixPlayerLanguage.EN -> englishMixPlayerString
    MixPlayerLanguage.RU -> russianMixPlayerString
}
