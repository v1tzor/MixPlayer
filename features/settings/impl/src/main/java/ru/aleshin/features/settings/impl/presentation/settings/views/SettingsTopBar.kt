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

package ru.aleshin.features.settings.impl.presentation.settings.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.aleshin.core.ui.views.TopAppBarAction
import ru.aleshin.core.ui.views.TopAppBarMoreActions
import ru.aleshin.core.ui.views.TopAppBarTitle
import ru.aleshin.features.settings.impl.presentation.theme.SettingsThemeRes

/**
 * @author Stanislav Aleshin on 15.06.2023.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun SettingsTopBar(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
    onResetClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            TopAppBarTitle(text = SettingsThemeRes.strings.settingsHeader)
        },
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = SettingsThemeRes.strings.backDesk,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            TopAppBarMoreActions(
                items = SettingsTopBarActions.values(),
                onItemClick = {
                    when (it) {
                        SettingsTopBarActions.RESET -> onResetClick()
                    }
                },
                moreIconDescription = SettingsThemeRes.strings.moreDesk,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    )
}

internal enum class SettingsTopBarActions : TopAppBarAction {
    RESET {
        override val icon: Int @Composable get() = SettingsThemeRes.icons.reset
        override val title: String @Composable get() = SettingsThemeRes.strings.resetTitle
        override val isAlwaysShow: Boolean get() = false
    },
}
