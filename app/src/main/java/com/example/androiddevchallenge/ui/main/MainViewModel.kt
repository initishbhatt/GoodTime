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

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

private const val TEN_MINS_IN_MILLIS = 600000L
private const val TIMER_FORMAT = "%02d"

class MainViewModel : ViewModel() {

    var timerValue by mutableStateOf(TEN_MINS_IN_MILLIS)
    var minutes by mutableStateOf(String.format(TIMER_FORMAT, 10))
    var seconds by mutableStateOf(String.format(TIMER_FORMAT, 0))
    var isPlaying by mutableStateOf(false)
    var progress by mutableStateOf(1.00F)
    var goodTimeText by mutableStateOf("Ready!!")
    private var isPaused by mutableStateOf(false)
    private var countDownTimer: CountDownTimer? = null

    fun startCountDown() {
        if (!isPaused) {
            start()
        } else {
            pauseCountdown()
        }
    }

    private fun start() {
        isPaused = true
        countDownTimer = object : CountDownTimer(timerValue, 1000) {
            override fun onTick(millisRemaining: Long) {
                isPlaying = true
                timerValue = millisRemaining
                minutes =
                    String.format(
                        TIMER_FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisRemaining)
                    )
                seconds = String.format(
                    TIMER_FORMAT,
                    TimeUnit.MILLISECONDS.toSeconds(millisRemaining) % 60
                )
                val progressValue = millisRemaining.toFloat() / TEN_MINS_IN_MILLIS
                progress = String.format("%1.2f", progressValue).toFloat()

                if (TimeUnit.MILLISECONDS.toSeconds(millisRemaining) % 15 == 0L) {
                    goodTimeText = goodTextList.random()
                }
            }

            override fun onFinish() {
                isPlaying = false
                resetTimerValues()
            }
        }.start()
    }

    private fun pauseCountdown() {
        if (countDownTimer != null) {
            isPlaying = false
            isPaused = false
            countDownTimer?.cancel()
        }
    }

    fun resetCountDown() {
        if (countDownTimer != null) {
            isPlaying = false
            countDownTimer?.cancel()
            resetTimerValues()
            goodTimeText = "Ready!"
        }
    }

    private fun resetTimerValues() {
        timerValue = TEN_MINS_IN_MILLIS
        minutes =
            String.format(TIMER_FORMAT, TimeUnit.MILLISECONDS.toMinutes(TEN_MINS_IN_MILLIS))
        seconds = String.format(
            TIMER_FORMAT,
            TimeUnit.MILLISECONDS.toSeconds(TEN_MINS_IN_MILLIS) % 60
        )
        progress = 1.0f
    }

    val goodTextList = listOf(
        "Look up and smile.",
        "Keep stillness inside of you.",
        "There is no room for anger when the mind is calm.",
        "Quiet the mind, and the soul will speak.",
        "The best meditation is effortless.",
        "The best meditation is a gentle awareness.",
        "Learn to slow down.",
        "Meditation is the art of doing nothing.",
        "Relax!"
    )
}
