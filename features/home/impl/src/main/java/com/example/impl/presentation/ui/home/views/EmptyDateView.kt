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
package com.example.impl.presentation.ui.home.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.impl.presentation.theme.HomeThemeRes
import com.example.presentation.ui.theme.TimePlannerRes

@Composable
internal fun EmptyDateView(
    modifier: Modifier = Modifier,
    emptyTitle: String,
    subTitle: String? = null,
    onActionButton: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(HomeThemeRes.icons.notFound),
            contentDescription = TimePlannerRes.strings.emptyScheduleTitle,
            tint = MaterialTheme.colorScheme.surfaceVariant,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = emptyTitle,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    style = MaterialTheme.typography.headlineSmall,
                )
                if (subTitle != null) {
                    Text(
                        text = subTitle,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
            onActionButton?.invoke()
        }
    }
}

/* ----------------------- Release Preview -----------------------
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun EmptyDateView_Preview() {
    TimePlannerTheme(l
        dynamicColor = false,
        themeColorsType = ThemeColorsUiType.LIGHT,
        language = LanguageUiType.RU,
    ) {
        HomeTheme {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                EmptyDateView(emptyTitle = HomeThemeRes.strings.emptyScheduleTitle)
            }
        }
    }
}*/
