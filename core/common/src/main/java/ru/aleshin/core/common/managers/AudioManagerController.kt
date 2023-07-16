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

package ru.aleshin.core.common.managers

import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @author Stanislav Aleshin on 13.07.2023.
 */
interface AudioManagerController {

    fun captureAudioFocus(listener: OnAudioFocusChangeListener): Boolean

    fun freeAudioFocus(listener: OnAudioFocusChangeListener): Boolean

    class Base(
        private val audioManager: AudioManager,
        private val audioAttributes: AudioAttributes,
    ) : AudioManagerController {

        @RequiresApi(Build.VERSION_CODES.O)
        private val focusRequesterBuilder = AudioFocusRequest.Builder(
            AudioManager.AUDIOFOCUS_GAIN
        ).apply {
            setAudioAttributes(audioAttributes)
            setAcceptsDelayedFocusGain(true)
            setWillPauseWhenDucked(true)
        }

        override fun captureAudioFocus(listener: OnAudioFocusChangeListener): Boolean {
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val focusRequest = focusRequesterBuilder.setOnAudioFocusChangeListener(listener)
                audioManager.requestAudioFocus(focusRequest.build())
            } else {
                audioManager.requestAudioFocus(
                    listener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN
                )
            }
            return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }

        override fun freeAudioFocus(listener: OnAudioFocusChangeListener): Boolean {
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val focusRequest = focusRequesterBuilder.setOnAudioFocusChangeListener(listener)
                audioManager.abandonAudioFocusRequest(focusRequest.build())
            } else {
                audioManager.abandonAudioFocus(listener)
            }
            return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }

}