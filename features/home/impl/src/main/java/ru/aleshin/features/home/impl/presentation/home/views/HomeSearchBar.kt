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

package ru.aleshin.features.home.impl.presentation.home.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.aleshin.core.common.extensions.mapAudioPathToPreview
import ru.aleshin.core.common.extensions.mapVideoPathToPreview
import ru.aleshin.core.common.extensions.toSecondsAndMinutesString
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.core.common.functional.video.VideoInfoUi
import ru.aleshin.core.ui.theme.MixPlayerRes
import ru.aleshin.features.home.impl.presentation.models.MediaSearchResponse
import ru.aleshin.features.home.impl.presentation.theme.HomeThemeRes

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun HomeSearchBar(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    response: MediaSearchResponse?,
    onSearch: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onChooseAudio: (AudioInfoUi, AudioPlayListUi) -> Unit,
    onChooseVideo: (VideoInfoUi) -> Unit,
) {
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    DockedSearchBar(
        modifier = modifier,
        enabled = !isLoading,
        query = query,
        onQueryChange = { query = it; onSearch(it) },
        onSearch = onSearch,
        active = isActive,
        onActiveChange = { isActive = it },
        placeholder = {
            Text(text = HomeThemeRes.strings.searchPlaceholder)
        },
        leadingIcon = {
            AnimatedContent(
                targetState = isActive,
                label = "SearchBarLeadingIcon",
            ) { targetState ->
                if (targetState) {
                    IconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = { isActive = false; query = "" },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = HomeThemeRes.strings.closeSearchBarDesk,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else {
                    IconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = { isActive = true },
                        enabled = !isLoading,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = HomeThemeRes.strings.searchPlaceholder,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        },
        trailingIcon = {
            AnimatedContent(
                targetState = isActive,
                label = "SearchBarTrailingIcons",
            ) { targetState ->
                if (targetState) {
                    IconButton(modifier = Modifier.size(48.dp), onClick = { query = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = HomeThemeRes.strings.clearSearchBarDesk,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else {
                    IconButton(onClick = onSettingsClick, enabled = !isLoading) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = HomeThemeRes.strings.settingsDesk,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
    ) {
        if (response != null) {
            LazyColumn {
                items(response.videos) { video ->
                    SearchVideoResponseItem(
                        image = video.imagePath?.mapVideoPathToPreview(),
                        title = video.title,
                        duration = video.duration.toSecondsAndMinutesString(),
                        onClick = { onChooseVideo(video) },
                    )
                }
                response.playLists.forEach { playlist ->
                    items(playlist.audioList) { audio ->
                        SearchAudioResponseItem(
                            image = audio.imagePath?.mapAudioPathToPreview(),
                            title = audio.title,
                            authorOrAlbum = audio.artist ?: audio.album ?: "",
                            duration = audio.duration.toSecondsAndMinutesString(),
                            onClick = { onChooseAudio(audio, playlist) },
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = HomeThemeRes.icons.emptySearch),
                    contentDescription = HomeThemeRes.strings.emptySearchTitle,
                    tint = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    text = HomeThemeRes.strings.emptySearchTitle,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
    LaunchedEffect(key1 = isActive, block = { query = "" })
}

@Composable
internal fun SearchAudioResponseItem(
    modifier: Modifier = Modifier,
    image: ImageBitmap?,
    title: String,
    authorOrAlbum: String,
    duration: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.clickable(onClick = onClick).padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (image != null) {
            Image(
                modifier = Modifier.size(40.dp).clip(MaterialTheme.shapes.small),
                bitmap = image,
                contentDescription = title,
            )
        } else {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = HomeThemeRes.icons.music),
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = authorOrAlbum,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Text(
            text = duration,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
internal fun SearchVideoResponseItem(
    modifier: Modifier = Modifier,
    image: ImageBitmap?,
    title: String,
    duration: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.clickable(onClick = onClick).padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (image != null) {
            Image(
                modifier = Modifier.size(width = 60.dp, height = 40.dp).clip(MaterialTheme.shapes.small),
                bitmap = image,
                contentDescription = title,
            )
        } else {
            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 40.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = MixPlayerRes.icons.videos),
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Text(
            text = duration,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}
