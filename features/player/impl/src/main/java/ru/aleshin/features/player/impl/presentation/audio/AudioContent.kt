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

package ru.aleshin.features.player.impl.presentation.audio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import ru.aleshin.core.common.extensions.mapAudioPathToPreview
import ru.aleshin.core.common.extensions.toSecondsAndMinutesString
import ru.aleshin.features.player.impl.presentation.audio.contract.AudioViewState
import ru.aleshin.features.player.impl.presentation.theme.PlayerThemeRes

/**
 * @author Stanislav Aleshin on 11.07.2023.
 */
@Composable
internal fun AudioContent(
    modifier: Modifier = Modifier,
    state: AudioViewState,
    onChangePosition: (Float) -> Unit,
    onPlayPauseClick: (Boolean) -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier.fillMaxSize().verticalScroll(scrollState), verticalArrangement = Arrangement.SpaceEvenly) {
        AudioImage(
            modifier = Modifier.padding(bottom = 48.dp).align(Alignment.CenterHorizontally),
            image = state.currentAudio?.imagePath?.mapAudioPathToPreview(),
        )
        Column {
            AudioNameSection(
                title = state.currentAudio?.title,
                authorOrAlbum = state.currentAudio?.artist ?: state.currentAudio?.album
            )
            AudioControlSection(
                position = state.lostTime * (1f / (state.currentAudio?.duration ?: 1)) ,
                lostTime = state.lostTime.toSecondsAndMinutesString(),
                nextTime = state.nextTime.toSecondsAndMinutesString(),
                isPlayedMusic = state.isPlaying,
                onChangePosition = onChangePosition,
                onPlayPauseClick = onPlayPauseClick,
                onNextClick = onNextClick,
                onPreviousClick = onPreviousClick,
            )
        }
    }
}

@Composable
internal fun AudioImage(
    modifier: Modifier = Modifier,
    image: ImageBitmap?,
) {
    if (image != null) {
        Image(
            modifier = modifier
                .size(250.dp)
                .shadow(elevation = 8.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large),
            bitmap = image,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    } else {
        Box(
            modifier = modifier
                .size(250.dp)
                .shadow(elevation = 8.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = PlayerThemeRes.icons.music),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
internal fun AudioNameSection(
    modifier: Modifier = Modifier,
    title: String?,
    authorOrAlbum: String?,
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier,
            text = title ?: "",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        Text(
            modifier = Modifier,
            text = authorOrAlbum ?: "",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun AudioControlSection(
    modifier: Modifier = Modifier,
    position: Float,
    lostTime: String,
    nextTime: String,
    isPlayedMusic: Boolean,
    onChangePosition: (Float) -> Unit,
    onPlayPauseClick: (Boolean) -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(36.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            val interactionSource = remember { MutableInteractionSource() }
            Slider(
                value = position,
                onValueChange = onChangePosition,
                interactionSource = interactionSource,
                thumb = {
                    SliderDefaults.Thumb(
                        interactionSource = interactionSource,
                        thumbSize = DpSize(width = 15.dp, height = 15.dp),
                    )
                }
            )
            Row(modifier = Modifier.padding(horizontal = 8.dp).offset(y = (-8).dp)) {
                Text(
                    text = lostTime,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = nextTime,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        Row(
            modifier = Modifier.padding(bottom = 36 .dp),
            horizontalArrangement = Arrangement.spacedBy(42.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = onPreviousClick
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = PlayerThemeRes.icons.previous),
                    contentDescription = PlayerThemeRes.strings.previousAudioDesk,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                onClick = { onPlayPauseClick(!isPlayedMusic) }
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = when (isPlayedMusic) {
                        true -> painterResource(id = PlayerThemeRes.icons.pause)
                        false -> painterResource(id = PlayerThemeRes.icons.play)
                    },
                    contentDescription = PlayerThemeRes.strings.playPauseAudioDesk,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = onNextClick
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = PlayerThemeRes.icons.next),
                    contentDescription = PlayerThemeRes.strings.nextAudioDesk,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

//@Composable
//@Preview
//internal fun AudioContent_PreviewLight() {
//    MixPlayerTheme(themeType = ThemeUiType.LIGHT) {
//        PlayerTheme {
//            Box(Modifier.background(MaterialTheme.colorScheme.background)) {
//                AudioContent(
//                    state = AudioViewState(),
//                    onChangePosition = {},
//                    onPlayPauseClick = {},
//                    onNextClick = {},
//                    onPreviousClick = {},
//                )
//            }
//        }
//    }
//}
//
//@Composable
//@Preview
//internal fun AudioContent_PreviewDark() {
//    MixPlayerTheme(themeType = ThemeUiType.DARK) {
//        PlayerTheme {
//            Box {
//                AudioContent(
//                    state = AudioViewState(),
//                    onChangePosition = {},
//                    onPlayPauseClick = {},
//                    onNextClick = {},
//                    onPreviousClick = {},
//                )
//            }
//        }
//    }
//}
