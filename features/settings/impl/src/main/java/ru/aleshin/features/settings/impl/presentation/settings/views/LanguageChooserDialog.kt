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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.aleshin.core.ui.theme.tokens.LanguageUiType
import ru.aleshin.core.ui.views.DialogButtons
import ru.aleshin.features.settings.impl.presentation.mappers.mapToMessage
import ru.aleshin.features.settings.impl.presentation.theme.SettingsThemeRes

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun LanguageChooserDialog(
    modifier: Modifier = Modifier,
    initLanguage: LanguageUiType,
    onCloseDialog: () -> Unit,
    onChooseLanguage: (LanguageUiType) -> Unit,
) {
    var currentLanguage by remember { mutableStateOf(initLanguage) }

    AlertDialog(onDismissRequest = onCloseDialog) {
        Surface(
            modifier = modifier.width(280.dp).wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
        ) {
            Column {
                Box(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = SettingsThemeRes.strings.appLanguageTitle,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    items(LanguageUiType.values()) { language ->
                        SettingsDialogItem(
                            modifier = Modifier.fillMaxWidth(),
                            selected = language == currentLanguage,
                            title = language.mapToMessage(),
                            onSelectChange = { currentLanguage = language },
                        )
                    }
                }
                DialogButtons(
                    onCancelClick = onCloseDialog,
                    onConfirmClick = { onChooseLanguage(currentLanguage) },
                )
            }
        }
    }
}
