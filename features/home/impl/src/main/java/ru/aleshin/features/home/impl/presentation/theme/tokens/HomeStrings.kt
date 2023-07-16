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

/**
 * @author Stanislav Aleshin on 14.06.2023.
 */
internal data class HomeStrings(
    val otherError: String,
    val searchPlaceholder: String,
    val settingsDesk: String,
    val menuDesk: String,
    val moreDesk: String,
    val systemAudioTitle: String,
    val appAudioTitle: String,
    val detailsHeader: String,
    val closeSearchBarDesk: String,
    val clearSearchBarDesk: String,
    val emptySearchTitle: String,
    val emptyTracksTitle: String,
    val emptyVideoTitle: String,
    val backDesk: String,
    val videosHeader: String,
    val videoNameTitle: String,
) {
    companion object {
        val RUSSIAN = HomeStrings(
            otherError = "Ошибка! Обратитесь к разработчику!",
            searchPlaceholder = "Поиск медиа",
            settingsDesk = "Настройки",
            menuDesk = "Меню",
            moreDesk = "Больше треков",
            systemAudioTitle = "Загруженные треки",
            appAudioTitle = "Треки приложения",
            detailsHeader = "Треки",
            closeSearchBarDesk = "Закрыть",
            clearSearchBarDesk = "Очстить",
            emptySearchTitle = "Нет результатов",
            emptyTracksTitle = "Треков нет",
            emptyVideoTitle = "Видео роликов нет",
            backDesk = "Назад",
            videosHeader = "Видео ролики",
            videoNameTitle = "Название",
        )
        val ENGLISH = HomeStrings(
            otherError = "Error! Contact the developer!",
            searchPlaceholder = "Media Search",
            settingsDesk = "Settings",
            menuDesk = "Menu",
            moreDesk = "More tracks",
            systemAudioTitle = "Uploaded tracks",
            appAudioTitle = "App Tracks",
            detailsHeader = "Tracks",
            closeSearchBarDesk = "Close",
            clearSearchBarDesk = "Clear",
            emptySearchTitle = "No results",
            emptyTracksTitle = "No tracks",
            emptyVideoTitle = "No videos",
            backDesk = "Back",
            videosHeader = "Videos",
            videoNameTitle = "Title",
        )
    }
}

internal val LocalHomeStrings = staticCompositionLocalOf<HomeStrings> {
    error("Home Strings is not provided")
}
