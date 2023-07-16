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
 * limitations under the License.
*/
package ru.aleshin.core.common.extensions

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun String.mapAudioPathToPreview(): ImageBitmap? {
    val metadataRetriever = remember { MediaMetadataRetriever() }
    val bitmap = try {
        metadataRetriever.setDataSource(this)
        val embedPic = metadataRetriever.embeddedPicture
        BitmapFactory.decodeByteArray(embedPic, 0, embedPic!!.size)
    } catch (e: Exception) {
        e.printStackTrace().let { null }
    }
    return bitmap?.asImageBitmap()
}

@Composable
fun String.mapVideoPathToPreview(): ImageBitmap? {
    val metadataRetriever = remember { MediaMetadataRetriever() }
    val bitmap = try {
        metadataRetriever.setDataSource(this)
        metadataRetriever.getFrameAtTime(5, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    } catch (e: Exception) {
        e.printStackTrace().let { null }
    }
    return bitmap?.asImageBitmap()
}


