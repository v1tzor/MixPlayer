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

package ru.aleshin.mixplayer.presentation.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.aleshin.core.common.functional.Constants
import ru.aleshin.core.ui.theme.MixPlayerRes
import ru.aleshin.core.ui.theme.material.onSplash
import ru.aleshin.core.ui.theme.material.splash

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
@Composable
fun SplashContent(modifier: Modifier = Modifier) {
    var isVisible by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier.fillMaxSize().background(splash),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = MixPlayerRes.icons.launcher),
                contentDescription = MixPlayerRes.strings.appName,
                tint = onSplash,
            )
            AnimatedVisibility(
                enter = fadeIn() + expandVertically(),
                visible = isVisible,
            ) {
                Text(
                    text = MixPlayerRes.strings.appNameSplash,
                    color = onSplash,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                    ),
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(Constants.Delay.SPLASH_TEXT)
        isVisible = true
    }
}

//@Composable
//@Preview
//private fun SplashContent_Preview() {
//    MixPlayerTheme(themeType = ThemeUiType.LIGHT) {
//        SplashContent(
//            modifier = Modifier,
//        )
//    }
//}
