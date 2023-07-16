package ru.aleshin.features.home.api.di

import ru.aleshin.core.common.functional.audio.AudioPlayListUi


/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
sealed class HomeScreens {
    object Home : HomeScreens()
    data class Details(val playList: AudioPlayListUi) : HomeScreens()
}
