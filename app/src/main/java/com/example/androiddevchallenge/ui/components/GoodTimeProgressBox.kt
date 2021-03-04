/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.ui.theme.malibu200

@Composable
fun GoodTimeProgressBox(
    modifier: Modifier = Modifier,
    minutes: String,
    seconds: String,
    progress: Float
) {
    ConstraintLayout(modifier = modifier) {
        val (box, progressBar, progressText) = createRefs()
        GoodTimeProgress(
            progress = progress,
            modifier = Modifier
                .size(220.dp)
                .constrainAs(progressBar) {
                    top.linkTo(parent.top, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .clip(CircleShape)
                .constrainAs(box) {
                    top.linkTo(progressBar.top)
                    start.linkTo(progressBar.start)
                    end.linkTo(progressBar.end)
                    bottom.linkTo(progressBar.bottom)
                }
                .background(color = malibu200)
        )
        Row(
            modifier = Modifier.constrainAs(progressText) {
                top.linkTo(box.top, margin = 4.dp)
                start.linkTo(box.start, margin = 4.dp)
                end.linkTo(box.end, margin = 4.dp)
                bottom.linkTo(box.bottom, margin = 4.dp)
            }
        ) {
            GoodTimeText(value = minutes, style = MaterialTheme.typography.h4)
            GoodTimeDivider(modifier = Modifier.padding(horizontal = 8.dp))
            GoodTimeText(value = seconds, style = MaterialTheme.typography.h4)
        }
    }
}
