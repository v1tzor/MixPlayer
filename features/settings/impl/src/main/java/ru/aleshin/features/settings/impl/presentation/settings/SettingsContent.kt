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

package ru.aleshin.features.settings.impl.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.aleshin.core.ui.theme.MixPlayerTheme
import ru.aleshin.core.ui.theme.material.ThemeUiType
import ru.aleshin.features.settings.impl.presentation.mappers.mapToMessage
import ru.aleshin.features.settings.impl.presentation.models.GeneralSettingsUi
import ru.aleshin.features.settings.impl.presentation.settings.contract.SettingsViewState
import ru.aleshin.features.settings.impl.presentation.settings.views.LanguageChooserDialog
import ru.aleshin.features.settings.impl.presentation.settings.views.SettingsItem
import ru.aleshin.features.settings.impl.presentation.settings.views.ThemeChooserDialog
import ru.aleshin.features.settings.impl.presentation.theme.SettingsTheme
import ru.aleshin.features.settings.impl.presentation.theme.SettingsThemeRes

/**
 * @author Stanislav Aleshin on 15.06.2023.
 */
@Composable
internal fun SettingsContent(
    state: SettingsViewState,
    modifier: Modifier = Modifier,
    onChangeGeneralSettings: (GeneralSettingsUi?) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        Divider()
        GeneralSettingsSection(
            generalSettingsUi = state.generalSettings,
            onChangeSettings = onChangeGeneralSettings,
        )
    }
}

@Composable
internal fun GeneralSettingsSection(
    modifier: Modifier = Modifier,
    generalSettingsUi: GeneralSettingsUi?,
    onChangeSettings: (GeneralSettingsUi?) -> Unit,
) {
    var isOpenThemeDialog by rememberSaveable { mutableStateOf(false) }
    var isOpenLanguageDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            text = SettingsThemeRes.strings.generalSettingsHeader,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall,
        )
        SettingsItem(
            onClick = { isOpenThemeDialog = true },
            icon = painterResource(id = SettingsThemeRes.icons.palette),
            title = SettingsThemeRes.strings.themeTitle,
            value = generalSettingsUi?.themeUiType?.mapToMessage(),
        )
        SettingsItem(
            onClick = { isOpenLanguageDialog = true },
            icon = painterResource(id = SettingsThemeRes.icons.language),
            title = SettingsThemeRes.strings.appLanguageTitle,
            value = generalSettingsUi?.languageUiType?.mapToMessage(),
        )
        Divider()
    }

    if (isOpenThemeDialog) {
        ThemeChooserDialog(
            initTheme = checkNotNull(generalSettingsUi).themeUiType,
            onCloseDialog = { isOpenThemeDialog = false },
            onChooseTheme = {
                onChangeSettings(generalSettingsUi.copy(themeUiType = it))
                isOpenThemeDialog = false
            },
        )
    }
    if (isOpenLanguageDialog) {
        LanguageChooserDialog(
            initLanguage = checkNotNull(generalSettingsUi).languageUiType,
            onCloseDialog = { isOpenLanguageDialog = false },
            onChooseLanguage = {
                onChangeSettings(generalSettingsUi.copy(languageUiType = it))
                isOpenLanguageDialog = false
            },
        )
    }
}

//@Composable
//@Preview(showBackground = true)
//private fun SettingsContent_Preview() {
//    MixPlayerTheme(themeType = ThemeUiType.LIGHT) {
//        SettingsTheme {
//            SettingsContent(
//                state = SettingsViewState(GeneralSettingsUi()),
//                onChangeGeneralSettings = {},
//            )
//        }
//    }
//}
