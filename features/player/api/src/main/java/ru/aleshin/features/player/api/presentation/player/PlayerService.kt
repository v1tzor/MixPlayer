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

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.aleshin.core.common.functional.Constants
import ru.aleshin.core.common.functional.MediaCommand
import ru.aleshin.core.common.functional.audio.PlayerError
import ru.aleshin.core.common.functional.audio.PlayerInfo
import ru.aleshin.core.common.managers.AudioManagerController
import ru.aleshin.core.common.notifications.NotificationCreator

/**
 * @author Stanislav Aleshin on 13.07.2023.
 */
class PlayerService : PlayerServiceAbstract() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(job)

    private lateinit var audioManager: AudioManager
    private lateinit var audioManagerController: AudioManagerController
    private lateinit var playerStore: PlayerInfoStore
    private lateinit var mediaPlayerManager: MediaPlayerManager

    private val binder by lazy { PlayerServiceBinder(this) }

    private val audioAttributes = AudioAttributes.Builder().let {
        it.setUsage(AudioAttributes.USAGE_MEDIA)
        it.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        it.build()
    }

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AudioManager::class.java)
        audioManagerController = AudioManagerController.Base(audioManager, audioAttributes)
        playerStore = PlayerInfoStore.Base()
        mediaPlayerManager = MediaPlayerManager.Base(this, playerStore)
        audioManagerController.captureAudioFocus(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(Constants.Notification.FOREGROUND_NOTIFY_ID, createNotification())
        handleAction(intent?.action)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder {
        updatePosition()
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        mediaPlayerManager.release()
        audioManagerController.freeAudioFocus(this)
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun workMediaCommand(mediaCommand: MediaCommand): Unit = with(mediaPlayerManager) {
        when (mediaCommand) {
            is MediaCommand.SelectAudio -> setAudio(mediaCommand.audio, mediaCommand.type)
            is MediaCommand.PlayOrPause -> if (isPlaying()) pause() else play()
            is MediaCommand.SeekTo -> seekTo(mediaCommand.value)
            is MediaCommand.ChangeVolume -> setVolume(mediaCommand.value)
        }
    }

    override fun onAudioFocusChange(focus: Int) = with(mediaPlayerManager) {
        when (focus) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                if (!isPlaying()) play()
                setVolume(fetchVolume())
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                if (isPlaying()) stop()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                if (isPlaying()) pause()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                if (isPlaying()) setVolume(0.1f)
            }
        }
    }

    private fun handleAction(action: String?) = when (action) {
        Constants.Notification.ACTION_PLAY_PAUSE -> workMediaCommand(MediaCommand.PlayOrPause)
        else -> Unit
    }

    private fun createNotification(): Notification {
        val playIntent = Intent(this, PlayerService::class.java).apply {
            action = Constants.Notification.ACTION_PLAY_PAUSE
        }
        val pendingPlay = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)

        val actions = listOf(
            NotificationCompat.Action.Builder(R.drawable.ic_media_play, "Плей/Пауза", pendingPlay).build(),
        )

        return NotificationCreator.Base(this).createNotify(
            channelId = Constants.Notification.CHANNEL_ID,
            title = "MixPlayer",
            text = "Play audio music",
            smallIcon = R.drawable.ic_media_play,
            ongoing = true,
            actions = actions
        )
    }

    private fun updatePosition() = scope.launch {
        while (isActive) {
            if (mediaPlayerManager.isPlaying()) {
                val position = mediaPlayerManager.fetchPosition()
                playerStore.updateInfo { copy(playback = playback.copy(position = position)) }
            }
            delay(Constants.Delay.SLIDER_POSITION_UPDATE)
        }
    }

    override suspend fun collectErrors(collector: FlowCollector<PlayerError>) = playerStore.collectErrors(collector)

    override suspend fun collectInfo(collector: FlowCollector<PlayerInfo>) = playerStore.collectInfo(collector)
}