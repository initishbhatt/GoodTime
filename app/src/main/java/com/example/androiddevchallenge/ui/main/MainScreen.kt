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
package com.example.androiddevchallenge.ui.main

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.components.GoodTimeCircularIconButton
import com.example.androiddevchallenge.ui.components.GoodTimeProgressBox
import com.example.androiddevchallenge.ui.components.GoodTimeText
import com.example.androiddevchallenge.ui.theme.malibu400
import com.example.androiddevchallenge.ui.theme.yellow200

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {

    MainContent(
        minutes = viewModel.minutes,
        seconds = viewModel.seconds,
        progress = viewModel.progress,
        isPlaying = viewModel.isPlaying,
        meditationText = viewModel.goodTimeText,
        onPlayClicked = viewModel::startCountDown,
        onStopClicked = viewModel::resetCountDown
    )
}

@Composable
fun MainContent(
    minutes: String,
    seconds: String,
    progress: Float,
    isPlaying: Boolean,
    meditationText: String,
    onPlayClicked: () -> Unit,
    onStopClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White, malibu400
                    )
                )
            ),
        content = {
            item {
                GoodTimeProgressBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    minutes, seconds, progress
                )
            }
            item { ActionButtons(onPlayClicked, onStopClicked, isPlaying) }
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_undraw_mindfulness_scgo),
                    contentDescription = null, modifier = Modifier.padding(16.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Crossfade(
                        targetState = meditationText,
                        animationSpec = tween(
                            durationMillis = 1000,
                            delayMillis = 1000,
                            easing = LinearOutSlowInEasing
                        )
                    ) {
                        GoodTimeText(
                            value = it,
                            style = MaterialTheme.typography.h4,
                            color = yellow200
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ActionButtons(onPlayClicked: () -> Unit, onStopClicked: () -> Unit, isPlaying: Boolean) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        val playPauseIcon = if (!isPlaying) {
            Icons.Default.PlayArrow
        } else {
            Icons.Default.Pause
        }
        GoodTimeCircularIconButton(
            icon = Icons.Default.Stop,
            contentDescription = "Stop",
            isEnabled = isPlaying,
            onClick = onStopClicked, modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        GoodTimeCircularIconButton(
            icon = playPauseIcon,
            contentDescription = "Play",
            onClick = onPlayClicked, modifier = Modifier.size(64.dp)
        )
    }
}
