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

package ru.aleshin.features.player.api.presentation.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListType
import ru.aleshin.core.common.functional.audio.PlaybackInfoUi
import ru.aleshin.core.common.functional.audio.PlayerError

/**
 * @author Stanislav Aleshin on 13.07.2023.
 */
interface MediaPlayerManager : MediaPlayerVolume, MediaPlayerListeners {

    fun setAudio(audio: AudioInfoUi, type: AudioPlayListType)
    fun play()
    fun pause()
    fun stop()
    fun release()
    fun seekTo(position: Int)
    fun fetchPosition(): Int
    fun isPlaying(): Boolean

    class Base(private val context: Context, private val store: PlayerInfoStore) : MediaPlayerManager {

        private var mediaPlayer: MediaPlayer? = prepareMediaPlayer()
        private var volume: Float = 1f

        override fun setAudio(audio: AudioInfoUi, type: AudioPlayListType) = try {
            if (audio.id != store.fetchInfo().playback.currentAudio?.id) {
                mediaPlayer?.reset()
                mediaPlayer?.setDataSource(context, Uri.parse(audio.path))
                mediaPlayer?.prepare()
                store.updateInfo {
                    copy(
                        playback = playback.copy(
                            currentAudio = audio,
                            position = 0,
                            isPlay = false,
                            isComplete = false,
                        ),
                        playListType = type,
                    )
                }
            } else {
                Unit
            }
        } catch (e: Exception) {
            store.sendError(PlayerError.DATA_SOURCE)
        }

        override fun play() = store.updateInfo {
            mediaPlayer?.start()
            copy(playback = playback.copy(isPlay = true))
        }

        override fun pause() = store.updateInfo {
            mediaPlayer?.pause()
            copy(playback = playback.copy(isPlay = false))
        }

        override fun stop() = store.updateInfo {
            mediaPlayer?.stop()
            copy(playback = playback.copy(isPlay = false, isComplete = true))
        }

        override fun seekTo(position: Int) = store.updateInfo {
            mediaPlayer?.seekTo(position)
            copy(playback = playback.copy(position = position))
        }

        override fun fetchPosition(): Int {
            return mediaPlayer?.currentPosition ?: -1
        }

        override fun isPlaying(): Boolean {
            return mediaPlayer?.isPlaying ?: false
        }

        override fun release() = store.updateInfo {
            mediaPlayer?.release()
            mediaPlayer = null
            copy(playback = PlaybackInfoUi(volume = volume))
        }

        override fun setVolume(value: Float) = store.updateInfo {
            volume = value
            mediaPlayer?.setVolume(volume, volume)
            copy(playback = playback.copy(volume = value))
        }

        override fun fetchVolume(): Float {
            return volume
        }

        override fun onPrepared(player: MediaPlayer?) = play()

        override fun onCompletion(player: MediaPlayer?) = stop()

        override fun onError(player: MediaPlayer?, error: Int, extra: Int): Boolean {
            store.sendError(PlayerError.OTHER)
            mediaPlayer?.release()
            mediaPlayer = prepareMediaPlayer()
            return true
        }

        private fun prepareMediaPlayer() = MediaPlayer().apply {
            store.updateInfo { copy(playback = PlaybackInfoUi(volume = volume)) }
            setVolume(volume, volume)
            setOnErrorListener(this@Base)
            setOnCompletionListener(this@Base)
            setOnPreparedListener(this@Base)
        }
    }
}

interface MediaPlayerListeners :
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnErrorListener