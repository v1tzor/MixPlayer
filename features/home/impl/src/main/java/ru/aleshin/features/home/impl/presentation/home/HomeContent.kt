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

package ru.aleshin.features.home.impl.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.aleshin.core.common.extensions.mapAudioPathToPreview
import ru.aleshin.core.common.extensions.mapVideoPathToPreview
import ru.aleshin.core.common.extensions.toSecondsAndMinutesString
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.core.ui.views.highPlaceholder
import ru.aleshin.core.common.functional.audio.AudioPlayListType
import ru.aleshin.core.common.functional.video.VideoInfoUi
import ru.aleshin.core.common.managers.toImageBitmap
import ru.aleshin.features.home.impl.presentation.home.contract.HomeViewState
import ru.aleshin.features.home.impl.presentation.home.views.HomeSearchBar
import ru.aleshin.features.home.impl.presentation.home.views.EmptyAudioItem
import ru.aleshin.features.home.impl.presentation.home.views.AudioItem
import ru.aleshin.features.home.impl.presentation.home.views.AudioItemPlaceholder
import ru.aleshin.features.home.impl.presentation.home.views.EmptyVideoItem
import ru.aleshin.features.home.impl.presentation.home.views.VideoItem
import ru.aleshin.features.home.impl.presentation.mappers.mapToString
import ru.aleshin.features.home.impl.presentation.theme.HomeThemeRes

/**
 * @author Stanislav Aleshin on 09.07.2023.
 */
@Composable
internal fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeViewState,
    onSettingsClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onSearchMedia: (String) -> Unit,
    onAudioClick: (AudioInfoUi, AudioPlayListUi) -> Unit,
    onVideoClick: (VideoInfoUi) -> Unit,
    onMoreSystemAudioPress: (AudioPlayListType) -> Unit,
) {
    val scrollState = rememberScrollState()
    // Material 3 is not support PullRefresh
    val refreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    Column {
        HomeSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            response = state.searchResponse,
            onSearch = onSearchMedia,
            onSettingsClick = onSettingsClick,
            onChooseAudio = onAudioClick,
            onChooseVideo = onVideoClick,
        )
        // Material 3 is not support PullRefresh
        SwipeRefresh(
            state = refreshState,
            onRefresh = onRefreshClick
        ) {
            Column(modifier = modifier.verticalScroll(scrollState)) {
                AudioTracksSection(
                    isLoading = state.isLoading,
                    playlists = state.playLists,
                    videos = state.videos,
                    onChooseAudio = onAudioClick,
                    onChooseVideo = onVideoClick,
                    onMorePress = onMoreSystemAudioPress,
                )
            }
        }
    }
}

@Composable
internal fun AudioTracksSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    playlists: List<AudioPlayListUi>,
    videos: List<VideoInfoUi>,
    onChooseAudio: (AudioInfoUi, AudioPlayListUi) -> Unit,
    onChooseVideo: (VideoInfoUi) -> Unit,
    onMorePress: (AudioPlayListType) -> Unit,
) {
    if (!isLoading) {
        playlists.forEach { playlist ->
            Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = playlist.listType.mapToString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelLarge,
                    )
                    IconButton(
                        modifier = Modifier.size(28.dp),
                        onClick = { onMorePress(playlist.listType) },
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = HomeThemeRes.icons.more),
                            contentDescription = HomeThemeRes.strings.moreDesk,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
                val rowState = rememberLazyListState()
                LazyRow(
                    state = rowState,
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (playlist.audioList.isNotEmpty()) {
                        items(playlist.audioList, key = { it.id }) { audio ->
                            AudioItem(
                                modifier = Modifier.width(170.dp),
                                image = audio.imagePath?.mapAudioPathToPreview(),
                                title = audio.title,
                                authorOrAlbum = audio.artist ?: audio.album ?: "",
                                duration = audio.duration.toSecondsAndMinutesString(),
                                onClick = { onChooseAudio(audio, playlist) },
                            )
                        }
                    } else {
                        item(content = { EmptyAudioItem(Modifier.width(170.dp)) })
                    }
                }
            }
        }
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 2.dp),
                text = HomeThemeRes.strings.videosHeader,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge,
            )
            val rowState = rememberLazyListState()
            LazyRow(
                state = rowState,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (videos.isNotEmpty()) {
                    items(videos, key = { it.id }) { video ->
                        VideoItem(
                            modifier = Modifier.width(250.dp),
                            image = video.imagePath?.mapVideoPathToPreview(),
                            title = video.title,
                            duration = video.duration.toSecondsAndMinutesString(),
                            onClick = { onChooseVideo(video) },
                        )
                    }
                } else {
                    item(content = { EmptyVideoItem(Modifier.width(250.dp)) })
                }
            }
        }
    } else {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 2.dp)
                    .size(width = 150.dp, height = 30.dp)
                    .highPlaceholder(shape = MaterialTheme.shapes.medium)
            )
            LazyRow(
                userScrollEnabled = false,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(10, itemContent = { AudioItemPlaceholder(Modifier.width(170.dp)) })
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//private fun HomeContent_PreviewLight() {
//    MixPlayerTheme(themeType = ThemeUiType.LIGHT) {
//        HomeTheme {
//            HomeContent(
//                state = HomeViewState(),
//                onSettingsClick = {},
//                onSearchMedia = {},
//                onTrackClick = { _, _ -> },
//                onMoreSystemAudioPress = {},
//            )
//        }
//    }
//}
//
//@Composable
//@Preview(showBackground = true)
//private fun HomeContent_PreviewDark() {
//    MixPlayerTheme(themeType = ThemeUiType.DARK) {
//        HomeTheme {
//            HomeContent(
//                state = HomeViewState(),
//                onSettingsClick = {},
//                onSearchMedia = {},
//                onTrackClick = { _, _ ->  },
//                onMoreSystemAudioPress = {},
//            )
//        }
//    }
//}
