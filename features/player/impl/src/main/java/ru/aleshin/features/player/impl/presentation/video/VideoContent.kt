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

package ru.aleshin.features.player.impl.presentation.video

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import ru.aleshin.core.common.functional.video.VideoInfoUi
import ru.aleshin.features.player.impl.presentation.video.contract.VideoViewState

/**
 * @author Stanislav Aleshin on 15.06.2023.
 */
@Composable
internal fun VideoContent(
    state: VideoViewState,
    modifier: Modifier = Modifier,
) {
    val mContext = LocalContext.current
    var video by rememberSaveable { mutableStateOf<VideoInfoUi?>(null) }
    var position by rememberSaveable { mutableLongStateOf(0L) }
    val mExoPlayer = remember(mContext) {
        ExoPlayer.Builder(mContext).build().apply { seekTo(position); playWhenReady = true }
    }

    LaunchedEffect(state.videoInfo) {
        if (state.videoInfo != null) {
            if (state.videoInfo != video) {
                mExoPlayer.apply {
                    clearMediaItems()
                    addMediaItem(MediaItem.fromUri(state.videoInfo.path))
                    seekTo(0)
                    prepare()
                }
                video = state.videoInfo
                position = 0
            } else {
                mExoPlayer.apply {
                    addMediaItem(MediaItem.fromUri(state.videoInfo.path))
                    prepare()
                }
            }
        }
    }

    DisposableEffect(
        key1 = AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    player = mExoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
        )
    ) {
        onDispose {
            position = mExoPlayer.currentPosition
            mExoPlayer.release()
        }
    }
}

//@Composable
//@Preview
//internal fun VideoContent_PreviewLight() {
//    MixPlayerTheme(themeType = ThemeUiType.LIGHT) {
//        PlayerTheme {
//            VideoContent(
//                state = VideoViewState(),
//            )
//        }
//    }
//}
//
//@Composable
//@Preview
//internal fun VideoContent_PreviewDark() {
//    MixPlayerTheme(themeType = ThemeUiType.DARK) {
//        PlayerTheme {
//            VideoContent(
//                state = VideoViewState(),
//            )
//        }
//    }
//}
