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

package ru.aleshin.features.player.impl.presentation.audio.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ru.aleshin.core.ui.views.TopAppBarAction
import ru.aleshin.core.ui.views.TopAppBarEmptyButton
import ru.aleshin.core.ui.views.TopAppBarMoreActions
import ru.aleshin.core.ui.views.TopAppBarTitle
import ru.aleshin.features.player.impl.R
import ru.aleshin.features.player.impl.presentation.theme.PlayerThemeRes

/**
 * @author Stanislav Aleshin on 13.07.2023.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun AudioTopBar(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
    playlistName: String?,
    volume: Float,
    onVolumeChange: (Float) -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            TopAppBarTitle(text = playlistName ?: "")
        },
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = PlayerThemeRes.strings.backDesk,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            val expanded = rememberSaveable { mutableStateOf(false) }
            Box(modifier = modifier.wrapContentSize(Alignment.TopEnd)) {
                IconButton(onClick = { expanded.value = true }) {
                    Icon(
                        painter = painterResource(id = PlayerThemeRes.icons.volume),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                DropdownMenu(
                    modifier = Modifier.align(Alignment.TopCenter).background(MaterialTheme.colorScheme.surfaceContainerHigh),
                    expanded = expanded.value,
                    offset = DpOffset(0.dp, 10.dp),
                    onDismissRequest = { expanded.value = false },
                ) {
                    Slider(
                        modifier = Modifier.width(200.dp),
                        value = volume,
                        onValueChange = onVolumeChange
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    )
}
