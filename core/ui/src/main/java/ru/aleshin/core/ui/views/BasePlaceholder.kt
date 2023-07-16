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

package ru.aleshin.core.ui.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

/**
 * @author Stanislav Aleshin on 10.07.2023.
 */
@Composable
fun Modifier.highPlaceholder(
    visible: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
) = placeholder(
    visible = visible,
    color = MaterialTheme.colorScheme.surfaceContainerHigh,
    shape = shape,
    highlight = PlaceholderHighlight.shimmer(
        highlightColor = MaterialTheme.colorScheme.surfaceVariant,
    ),
)

@Composable
fun Modifier.highestPlaceholder(
    visible: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
) = placeholder(
    visible = visible,
    color = MaterialTheme.colorScheme.surfaceContainerHighest,
    shape = shape,
    highlight = PlaceholderHighlight.shimmer(
        highlightColor = MaterialTheme.colorScheme.surfaceContainer,
    ),
)
