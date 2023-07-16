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

package ru.aleshin.features.home.impl.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import ru.aleshin.core.common.extensions.mapAudioPathToPreview
import ru.aleshin.core.common.extensions.toSecondsAndMinutesString
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.managers.toImageBitmap
import ru.aleshin.features.home.impl.presentation.details.contract.DetailsViewState
import ru.aleshin.features.home.impl.presentation.home.views.EmptyAudioItem
import ru.aleshin.features.home.impl.presentation.home.views.AudioItem
import ru.aleshin.features.home.impl.presentation.home.views.AudioItemPlaceholder
import java.text.SimpleDateFormat

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
@Composable
internal fun DetailsContent(
    modifier: Modifier = Modifier,
    state: DetailsViewState,
    onItemClick: (AudioInfoUi) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        userScrollEnabled = !state.isLoading,
    ) {
        val playlist = state.playlist
        if (playlist != null && playlist.audioList.isNotEmpty()) {
            items(playlist.audioList, key = { it.id }) { track ->
                AudioItem(
                    modifier = Modifier.fillMaxWidth(),
                    image = track.imagePath?.mapAudioPathToPreview(),
                    title = track.title,
                    authorOrAlbum = track.artist ?: track.album ?: "",
                    duration = track.duration.toSecondsAndMinutesString(),
                    onClick = { onItemClick(track) },
                )
            }
        } else if (playlist != null) {
            item { EmptyAudioItem() }
        } else {
            items(15) { AudioItemPlaceholder(modifier = Modifier.fillMaxWidth()) }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//private fun DetailsContent_PreviewLight() {
//    MixPlayerTheme(themeType = ThemeUiType.LIGHT) {
//        HomeTheme {
//            DetailsContent(
//                state = DetailsViewState(),
//                onItemClick = {}
//            )
//        }
//    }
//}
//
//@Composable
//@Preview(showBackground = true)
//private fun DetailsContent_PreviewDark() {
//    MixPlayerTheme(themeType = ThemeUiType.DARK) {
//        HomeTheme {
//            DetailsContent(
//                state = DetailsViewState(),
//                onItemClick = {}
//            )
//        }
//    }
//}
